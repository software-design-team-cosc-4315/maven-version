create table MEMBERS
(
    MEMBER_ID       NUMBER default "FLORESE"."ISEQ$$_382856".nextval generated as identity
        constraint MEMBER_ID_PK
        primary key,
    USERNAME        VARCHAR2(50)         not null
        constraint MEMBERS_UK1
            unique,
    MEMBER_PASSWORD VARCHAR2(50)         not null,
    MEMBER_ROLE     VARCHAR2(50)         not null
        check (member_role IN ('Manager', 'Team Leader', 'Base User')),
    TEAM_ID         VARCHAR2(50),
    DELETED         CHAR   default ('N') not null
        constraint MEMBS_DELETED_CHECK
            check (DELETED IN ('N', 'Y'))
)
/

create or replace trigger RESTRICT_MOVE_BUSY_BASE_USER
    before update of TEAM_ID
    on MEMBERS
    for each row
DECLARE
    assignments INTEGER;
BEGIN
    SELECT COUNT(*)
    INTO assignments
    FROM SUBTASK
    WHERE ASSIGNED_TO_MEMBER_ID = :OLD.MEMBER_ID;

    IF 0 < assignments THEN
        RAISE_APPLICATION_ERROR(-20000,
                                'ERROR: {' || :NEW.USERNAME || '} has subtask assignments in his current team.');
    END IF;

    SELECT COUNT(*)
    INTO assignments
    FROM TASKS
    WHERE ASSIGNED_TO_MEMBER_ID = :OLD.MEMBER_ID;

    IF 0 < assignments THEN
        RAISE_APPLICATION_ERROR(-20000, 'ERROR: {' || :NEW.USERNAME || '} has task assignments in his current team.');
    END IF;

END;
/

create or replace trigger RESTRICT_DELETE_TEAM_LEADER
    before update of DELETED
    on MEMBERS
    for each row
    when (OLD.MEMBER_ROLE = 'Team Leader' AND NEW.DELETED = 'Y')
BEGIN
    raise_application_error(-20000, 'May not delete a team leader, designate a new leader first');
end;
/

create or replace trigger MEMBER_DELETED
    before update of DELETED
    on MEMBERS
    for each row
    when (NEW.DELETED = 'Y')
DECLARE
    subtask_count int;
    task_count    int;
    teams_count   int;
BEGIN

    SELECT COUNT(*)
    INTO subtask_count
    FROM SUBTASK
    WHERE ASSIGNED_TO_MEMBER_ID = :OLD.MEMBER_ID
      AND DELETED <> 'Y'
      AND STATUS != 'Completed';

    if subtask_count != 0 then
        RAISE_APPLICATION_ERROR(-20000, 'ERROR: {' || subtask_count || '} subtasks are assigned to this user.');
    end if;

    SELECT COUNT(*)
    INTO task_count
    FROM TASKS
    WHERE ASSIGNED_TO_MEMBER_ID = :OLD.MEMBER_ID
      AND DELETED <> 'Y'
      AND STATUS != 'Completed';

    if subtask_count != 0 then
        RAISE_APPLICATION_ERROR(-20000, 'ERROR: {' || task_count || '} tasks are assigned to this user.');
    end if;

    SELECT COUNT(*)
    INTO teams_count
    FROM TEAMS
    WHERE TEAM_LEADER_ID = :OLD.MEMBER_ID;

    if teams_count != 0 then
        RAISE_APPLICATION_ERROR(-20000, 'ERROR: This user is a team leader, designate a new leader first.');
    end if;

end;
/

create table TEAMS
(
    TEAM_ID        VARCHAR2(50)       not null,
    TEAM_LEADER_ID NUMBER             not null
        constraint TEAM_LEADER_ID_FK
            references MEMBERS,
    DELETED        CHAR default ('N') not null
        constraint TEAMS_DELETED_CHECK
            check (DELETED IN ('N', 'Y'))
)
/

create unique index TEAMS_TEAM_ID_UINDEX
    on TEAMS (TEAM_ID)
/

create unique index TEAMS_TEAM_LEADER_ID_UINDEX
    on TEAMS (TEAM_LEADER_ID)
/

alter table TEAMS
    add constraint TEAMS_PK
        primary key (TEAM_ID)
/

alter table MEMBERS
    add constraint TEAM_ID_FK
        foreign key (TEAM_ID) references TEAMS
            on delete set null
/

create table TASKCATEGORIES
(
    NAME                 VARCHAR2(50) not null,
    CATEGORY_DESCRIPTION VARCHAR2(200),
    CREATED_BY_MEMBER_ID NUMBER       not null
        constraint TASKCATCREATED_BY_MEMBER_ID_FK
            references MEMBERS,
    CREATED_ON           DATE   default CURRENT_DATE,
    TASK_CATEGORY_ID     NUMBER default "FLORESE"."ISEQ$$_382860".nextval generated as identity
        constraint TASKCATEGORIES_PK
        primary key,
    TEAM_ID              VARCHAR2(50) not null
        constraint TASKCATTEAM_ID_FK
            references TEAMS
                on delete cascade,
    constraint TASKCATEGORIES_UK1
        unique (NAME, TEAM_ID)
)
/

create or replace trigger TASK_CAT_CREATION_DATE
    before insert
    on TASKCATEGORIES
    for each row
BEGIN
    :new.CREATED_ON := sysdate;
END;
/

create or replace trigger CHECK_DATE_ON_TASKCATEGORIES
    before insert or update
    on TASKCATEGORIES
    for each row
    when (NEW.CREATED_ON > CURRENT_DATE)
BEGIN
    raise_application_error(-20002, 'CREATED_ON may not be in the future');
end;
/

create or replace trigger TASKCATEGORY_CREATED_BY
    before insert or update of CREATED_BY_MEMBER_ID
    on TASKCATEGORIES
    for each row
DECLARE
    name varchar2(50) := '';
    role varchar2(15) := '';
BEGIN

    name := null;
    role := null;

    SELECT USERNAME, MEMBER_ROLE
    into name, role
    FROM MEMBERS
    WHERE :NEW.CREATED_BY_MEMBER_ID = MEMBER_ID;

    IF (role <> 'Manager') THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERROR: {' || name || '} is not a manager.');
    end if;

end;
/

create or replace trigger NO_UNCATEGORIZED_TASKS
    instead of delete
    on TASKCATEGORIES
    for each row
    COMPOUND TRIGGER
    task_ID INTEGER;
    task_cat_ID INTEGER;

    -- records to store tasks that only belong to 1 category:
    TYPE category_table_type IS TABLE OF TASKINCATEGORIES%ROWTYPE INDEX BY PLS_INTEGER;
    only_category_table category_table_type;


BEFORE STATEMENT IS
BEGIN

    -- before statement action, collect all task-in-category records for tasks with only one category:
    SELECT * BULK COLLECT
    INTO only_category_table
    FROM TASKINCATEGORIES
    WHERE TASK_ID IN (
        SELECT TIC.TASK_ID
        FROM TASKINCATEGORIES TIC,
             TASKS TA
        WHERE TIC.TASK_ID = TA.TASK_ID
          AND TA.DELETED <> 'Y'
        GROUP BY TIC.TASK_ID
        HAVING COUNT(TIC.TASK_CATEGORY_ID) = 1
    );

END BEFORE STATEMENT;


    -- process with the affected row (i.e. row to be deleted)
    BEFORE EACH ROW IS
    BEGIN

        -- for each record of task in its only category:
        FOR ind IN 1 .. only_category_table.COUNT
            LOOP -- examine each record

        -- if the category ID is the category to delete, then prevent the deletion to ensure no task is left uncategorised:
                IF (only_category_table(ind).TASK_CATEGORY_ID = :OLD.TASK_CATEGORY_ID) THEN -- If the category to delete has tasks belonging only to this category
                    RAISE_APPLICATION_ERROR(-20005, 'ERROR: Some tasks would have no category.');
                END IF;

            END LOOP;

    END BEFORE EACH ROW;
    END NO_UNCATEGORIZED_TASKS;
/

create table TASKS
(
    NAME                  VARCHAR2(50)               not null,
    TASK_DESCRIPTION      VARCHAR2(200),
    DUE_DATE              DATE,
    CREATED_ON            DATE         default CURRENT_DATE,
    CREATED_BY_MEMBER_ID  NUMBER                     not null
        constraint CREATED_BY_MEMBER_ID_FK
            references MEMBERS,
    STATUS                VARCHAR2(50) default 'Not Started'
        check (status IN ('Completed', 'In Progress', 'Not Started')),
    TASK_PRIORITY         NUMBER                     not null
        check (task_priority BETWEEN 1 AND 4),
    ASSIGNED_TO_MEMBER_ID NUMBER                     not null
        constraint ASSIGNED_TO_MEMBER_ID_FK
            references MEMBERS,
    TEAM_ID               VARCHAR2(50)               not null
        constraint TASKTOTEAM_ID_FK
            references TEAMS
                on delete cascade,
    TASK_ID               NUMBER       default "FLORESE"."ISEQ$$_382862".nextval generated as identity
        constraint TASKS_PK
        primary key,
    RECUR_INTERVAL        NUMBER                     not null
        constraint TASKS_CHK_RECUR
            check ((RECUR_INTERVAL IN (0, 7, 30))),
    DELETED               CHAR         default ('N') not null
        constraint DELETED_CHECK
            check (DELETED IN ('N', 'Y')),
    constraint TASKS_UK1
        unique (NAME, TEAM_ID),
    constraint TASK_DATE
        check (DUE_DATE > CREATED_ON)
)
/

create table TASKINCATEGORIES
(
    TASK_ID          NUMBER not null
        constraint TASKINCATEGORIES_FK1
            references TASKS
                on delete cascade,
    TASK_CATEGORY_ID NUMBER not null
        constraint TASKINCATEGORIES_FK2
            references TASKCATEGORIES
                on delete cascade,
    constraint TASKINCATEGORIES_ID
        primary key (TASK_CATEGORY_ID, TASK_ID)
)
/

create table SUBTASK
(
    NAME                  VARCHAR2(50)                       not null,
    DESCRIPTION           VARCHAR2(500),
    DUE_DATE              DATE,
    CREATED_ON            DATE         default CURRENT_DATE,
    CREATED_BY_MEMBER_ID  NUMBER                             not null
        constraint SUBTASKCREATED_BY_MEMBER_ID_FK
            references MEMBERS,
    STATUS                VARCHAR2(50) default 'Not Started' not null
        check (status IN ('Completed', 'In Progress', 'Not Started')),
    PRIORITY              NUMBER
        check (priority BETWEEN 1 AND 4),
    ASSIGNED_TO_MEMBER_ID NUMBER                             not null
        constraint SUBASSIGNED_TO_MEMBER_ID_FK
            references MEMBERS,
    SUBTASK_TO            NUMBER                             not null
        constraint SUBTASK_FK1
            references TASKS
                on delete cascade,
    SUBTASK_ID            NUMBER       default "FLORESE"."ISEQ$$_382858".nextval generated as identity
        constraint SUBTASK_PK
        primary key,
    DELETED               CHAR         default ('N')         not null
        constraint SB_DELETED_CHECK
            check (DELETED IN ('N', 'Y')),
    constraint TASK_DUE_DATE
        check (DUE_DATE > CREATED_ON)
)
/

create or replace trigger SUBTASK_CREATION_DATE
    before insert
    on SUBTASK
    for each row
BEGIN
    :new.CREATED_ON := sysdate;
END;
/

create or replace trigger UNIQUE_SUBTASK
    before insert
    on SUBTASK
    for each row
DECLARE
    similar_record_count INTEGER;
BEGIN

    SELECT COUNT(*)
    INTO similar_record_count
    FROM SUBTASK S1,
         TASKS T1,
         TASKS T2
    WHERE S1.SUBTASK_TO = T1.TASK_ID
      AND T1.TEAM_ID = T2.TEAM_ID
      AND S1.NAME = :NEW.NAME
      AND T2.TASK_ID = :NEW.SUBTASK_TO
      AND S1.DELETED <> 'Y'
      AND T1.DELETED <> 'Y'
      AND T2.DELETED <> 'Y';

    IF 0 < similar_record_count THEN
        RAISE_APPLICATION_ERROR(-20008, 'ERROR: Subtask name {' || :NEW.NAME ||
                                        '} has been used in the current team. Insert rejected.');
    END IF;

END;
/

create or replace trigger CHECK_CREATED_ON_SUBTASK
    before insert or update of CREATED_ON
    on SUBTASK
    for each row
    when (NEW.CREATED_ON > CURRENT_DATE)
BEGIN
    raise_application_error(-20002, 'CREATED_ON may not be in the future');
end;
/

create or replace trigger SUBTASK_CREATED_BY
    before insert or update of CREATED_BY_MEMBER_ID
    on SUBTASK
    for each row
DECLARE
    name       varchar2(50);
    is_manager INTEGER;
BEGIN

    SELECT count(*)
    into is_manager
    FROM MEMBERS
    WHERE :NEW.CREATED_BY_MEMBER_ID = MEMBER_ID
      AND MEMBER_ROLE = 'Manager';

    IF (is_manager = 0) THEN
        SELECT USERNAME into NAME FROM MEMBERS WHERE MEMBER_ID = :NEW.CREATED_BY_MEMBER_ID;

        RAISE_APPLICATION_ERROR(-20001, 'ERROR: {' || name || '} is not a manager.');
    end if;

end;
/

create or replace trigger SUBTASK_ASSIGNED_TO
    before insert or update of ASSIGNED_TO_MEMBER_ID
    on SUBTASK
    for each row
DECLARE
    name varchar2(50);
    role varchar2(50);
BEGIN

    name := null;
    role := null;

    SELECT USERNAME, MEMBER_ROLE
    INTO name, role
    FROM MEMBERS
    WHERE :NEW.ASSIGNED_TO_MEMBER_ID = MEMBER_ID;

    IF (role = 'Manager') THEN
        RAISE_APPLICATION_ERROR(-20001,
                                'ERROR: {' || name || '} is a manager. Subtasks cannot be assigned to managers.');
    END IF;

end;
/

create or replace trigger SUBTASK_BEFORE_TASK
    before insert or update of DUE_DATE
    on SUBTASK
    for each row
DECLARE
    due DATE;
BEGIN

    SELECT TASKS.DUE_DATE
    into due
    FROM TASKS
    WHERE TASK_ID = :NEW.SUBTASK_TO;

    IF (:NEW.DUE_DATE > due) THEN
        RAISE_APPLICATION_ERROR(-20004, 'ERROR: sub-task due date is after parent task due date.');
    end if;

end;
/

create or replace trigger UPDATE_SUBTASK_RECURRENCE
    instead of update
    on SUBTASK
    for each row
    COMPOUND TRIGGER
    task_start_date DATE;
    task_due_date DATE;
    recurrence_interval INT;

    subtaskID INT;
    old_subtask_assigned_to INT;
    old_subtask_parent_ID INT;
    new_subtask_due_date DATE;
    new_subtask_status VARCHAR(15);
    new_subtask_priority INT;
    new_subtask_assigned_to INT;
    new_subtask_parent_ID INT;


BEFORE EACH ROW IS
BEGIN

    SELECT START_DATE, DUE_DATE
    INTO task_start_date, task_due_date
    FROM TASK_HISTORY
    WHERE TASK_ID = :NEW.SUBTASK_TO
      AND HISTORY_ID IN (
        SELECT MAX(HISTORY_ID)
        FROM TASK_HISTORY
        GROUP BY TASK_ID
    );

    SELECT RECUR_INTERVAL
    INTO recurrence_interval
    FROM TASKS
    WHERE TASK_ID = :NEW.SUBTASK_TO;

    -- Validate due date:
    IF (VALIDATE_TASK_DATES(:NEW.DUE_DATE, task_start_date, recurrence_interval) = 0) THEN
        RAISE_APPLICATION_ERROR(-20003, 'ERROR: Due dates cannot go over the period of a periodic tasks!');
    ELSIF (task_due_date < :NEW.DUE_DATE) THEN
        RAISE_APPLICATION_ERROR(-20004, 'ERROR: Due dates cannot be later than the due date of the parent task!');
    END IF;

    -- Validate assignment transfer:
    IF (:NEW.ASSIGNED_TO_MEMBER_ID <> :OLD.ASSIGNED_TO_MEMBER_ID AND :NEW.STATUS = 'Completed') THEN
        RAISE_APPLICATION_ERROR(-20009, 'ERROR: Assigned member cannot change for completed subtasks!');
    END IF;

    subtaskID := :NEW.SUBTASK_ID;
    old_subtask_assigned_to := :OLD.ASSIGNED_TO_MEMBER_ID;
    old_subtask_parent_ID := :OLD.SUBTASK_TO;
    new_subtask_due_date := :NEW.DUE_DATE;
    new_subtask_status := :NEW.STATUS;
    new_subtask_priority := :NEW.PRIORITY;
    new_subtask_assigned_to := :NEW.ASSIGNED_TO_MEMBER_ID;
    new_subtask_parent_ID := :NEW.SUBTASK_TO;

END BEFORE EACH ROW;


    AFTER STATEMENT IS
    BEGIN

        IF (new_subtask_assigned_to = old_subtask_assigned_to AND new_subtask_parent_ID = old_subtask_parent_ID) THEN
            UPDATE SUBTASK_HISTORY
            SET DUE_DATE        = new_subtask_due_date,
                COMPLETION_DATE = CASE
                                      WHEN new_subtask_status = 'Completed' THEN sysdate
                                      ELSE null
                    END,
                FINAL_STATUS    = new_subtask_status,
                PRIORITY        = new_subtask_priority
            WHERE HISTORY_ID = (
                SELECT MAX(HISTORY_ID)
                FROM SUBTASK_HISTORY
                WHERE SUBTASK_ID = subtaskID
            );
        ELSE -- assignment or parent changed; create new history
            INSERT INTO SUBTASK_HISTORY(SUBTASK_ID, START_DATE, DUE_DATE, ASSIGNED_TO, FINAL_STATUS, PRIORITY,
                                        PARENT_ID)
            VALUES (subtaskID, task_start_date, new_subtask_due_date, new_subtask_assigned_to, new_subtask_status,
                    new_subtask_priority, new_subtask_parent_ID);
        END IF;

    END AFTER STATEMENT;
    END UPDATE_SUBTASK_RECURRENCE;
/

create or replace trigger INITIALISE_SUBTASK_RECURRENCE
    instead of insert
    on SUBTASK
    for each row
    COMPOUND TRIGGER
    task_start_date DATE;
    task_due_date DATE;
    task_recur_interval INT;


BEFORE EACH ROW IS
BEGIN

    SELECT START_DATE, DUE_DATE
    INTO task_start_date, task_due_date
    FROM TASK_HISTORY
    WHERE HISTORY_ID = (
        SELECT MAX(HISTORY_ID)
        FROM TASK_HISTORY
        WHERE TASK_ID = :NEW.SUBTASK_TO
    );

    SELECT RECUR_INTERVAL
    INTO task_recur_interval
    FROM TASKS
    WHERE TASK_ID = :NEW.SUBTASK_TO;

    -- Validate subtask due date:
    IF (VALIDATE_TASK_DATES(:NEW.DUE_DATE, task_start_date, task_recur_interval) = 0) THEN
        RAISE_APPLICATION_ERROR(-20003, 'ERROR: Due dates of subtasks cannot go over the period of a periodic tasks!');
    ELSIF (task_due_date < :NEW.DUE_DATE) THEN
        RAISE_APPLICATION_ERROR(-20004,
                                'ERROR: Due dates of subtasks cannot be later than the due date of the parent task!');
    END IF;
END BEFORE EACH ROW;


    AFTER EACH ROW IS
    BEGIN
        -- Create subtask history
        INSERT INTO SUBTASK_HISTORY(SUBTASK_ID, START_DATE, DUE_DATE, ASSIGNED_TO, PRIORITY, PARENT_ID)
        VALUES (:NEW.SUBTASK_ID, :NEW.CREATED_ON, :NEW.DUE_DATE, :NEW.ASSIGNED_TO_MEMBER_ID, :NEW.PRIORITY,
                :NEW.SUBTASK_TO);

    END AFTER EACH ROW;
    END INITIALISE_SUBTASK_RECURRENCE;
/

create or replace trigger TASK_CREATION_DATE
    before insert
    on TASKS
    for each row
BEGIN
    :new.CREATED_ON := sysdate;
END;
/

create or replace trigger CHECK_CREATED_ON_TASKS
    before insert or update of CREATED_ON
    on TASKS
    for each row
    when (NEW.CREATED_ON > CURRENT_DATE)
BEGIN
    raise_application_error(-20002, 'CREATED_ON may not be in the future');
end;
/

create or replace trigger TASK_CREATED_BY
    before insert or update of CREATED_BY_MEMBER_ID
    on TASKS
    for each row
DECLARE
    name       varchar2(50);
    is_manager INTEGER;
BEGIN

    SELECT count(*)
    into is_manager
    FROM MEMBERS
    WHERE :NEW.CREATED_BY_MEMBER_ID = MEMBER_ID
      AND MEMBER_ROLE = 'Manager';

    IF (is_manager = 0) THEN
        SELECT USERNAME into NAME FROM MEMBERS WHERE MEMBER_ID = :NEW.CREATED_BY_MEMBER_ID;

        RAISE_APPLICATION_ERROR(-20001, 'ERROR: {' || name || '} is not a manager.');
    end if;

end;
/

create or replace trigger TASK_ASSIGNED_TO
    before insert or update of ASSIGNED_TO_MEMBER_ID
    on TASKS
    for each row
DECLARE
    name varchar2(50);
    role varchar2(50);
BEGIN

    name := null;
    role := null;

    SELECT USERNAME, MEMBER_ROLE
    INTO name, role
    FROM MEMBERS
    WHERE :NEW.ASSIGNED_TO_MEMBER_ID = MEMBER_ID;

    IF (role != 'Team Leader') THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERROR: {' || name ||
                                        '} is not a team leader. Tasks must be assigned to team leaders.');
    END IF;

end;
/

create or replace trigger UPDATE_TASK_RECURRENCE
    instead of update
    on TASKS
    for each row
    COMPOUND TRIGGER
    task_ID INT;
    new_task_due_date DATE;
    new_task_status VARCHAR(15);
    new_task_priority INT;
    new_task_assigned_to INT;
    new_task_recur_interval INT;

    TYPE task_hist_table_type IS TABLE OF TASK_HISTORY%ROWTYPE INDEX BY PLS_INTEGER;
    latest_task_hist_table task_hist_table_type;

    hist_ind INT;
    count_late_subtasks INT := 0; -- count of the subtasks with due date after the task due date or after the task period


BEFORE STATEMENT IS
BEGIN
    hist_ind := 0;

    -- Select the current records of the tasks:
    SELECT * BULK COLLECT
    INTO latest_task_hist_table
    FROM TASK_HISTORY
    WHERE HISTORY_ID IN (
        SELECT MAX(H.HISTORY_ID)
        FROM TASK_HISTORY H
        GROUP BY H.TASK_ID
    );

END BEFORE STATEMENT;


    BEFORE EACH ROW IS
    BEGIN

        IF (:NEW.DELETED <> 'Y' AND :NEW.TEAM_ID = :OLD.TEAM_ID AND
            0 < latest_task_hist_table.COUNT) THEN -- if the update is significant enough to affect the history
            hist_ind := 1; -- index to the history record to be updated
            LOOP
                -- filter the history record related to the updated task
                EXIT WHEN (latest_task_hist_table(hist_ind).TASK_ID = :NEW.TASK_ID OR
                           hist_ind = latest_task_hist_table.COUNT);
                hist_ind := hist_ind + 1;
            END LOOP;

            -- Validate the dates:
            IF (VALIDATE_TASK_DATES(:NEW.DUE_DATE, latest_task_hist_table(hist_ind).START_DATE, :NEW.RECUR_INTERVAL) =
                0) THEN
                RAISE_APPLICATION_ERROR(-20003, 'ERROR: Due dates cannot go over the period of a periodic tasks!');
            END IF;

            -- Validate assignment transfer:
            IF (:NEW.ASSIGNED_TO_MEMBER_ID <> :OLD.ASSIGNED_TO_MEMBER_ID AND :NEW.STATUS = 'Completed') THEN
                RAISE_APPLICATION_ERROR(-20009, 'ERROR: Assigned member cannot change for completed tasks!');
            END IF;

            task_ID := :NEW.TASK_ID;
            new_task_due_date := :NEW.DUE_DATE;
            new_task_status := :NEW.STATUS;
            new_task_priority := :NEW.TASK_PRIORITY;
            new_task_assigned_to := :NEW.ASSIGNED_TO_MEMBER_ID;
            new_task_recur_interval := :NEW.RECUR_INTERVAL;
        END IF;

    END BEFORE EACH ROW;


    AFTER STATEMENT IS
    BEGIN

        IF (0 < hist_ind) THEN -- if the update is significant enough to affect the history
            SELECT COUNT(*)
            INTO count_late_subtasks
            FROM SUBTASK
            WHERE (VALIDATE_TASK_DATES(DUE_DATE, latest_task_hist_table(hist_ind).START_DATE, new_task_recur_interval) =
                   0 -- validate subtask dates against the task start date
                OR new_task_due_date < DUE_DATE) -- subtask due date is after the task due date
              AND SUBTASK_TO = task_ID;
            IF (0 < count_late_subtasks) THEN
                RAISE_APPLICATION_ERROR(-20003, 'ERROR: Detected ' || count_late_subtasks ||
                                                ' subtask(s) with due dates after the due date of the updated task or after the task {' ||
                                                task_ID || '} period!');
            END IF;


            IF (new_task_assigned_to = latest_task_hist_table(hist_ind).ASSIGNED_TO) THEN
                -- Same assignment: update the current task history table
                UPDATE TASK_HISTORY
                SET DUE_DATE        = new_task_due_date,
                    COMPLETION_DATE = CASE
                                          WHEN new_task_status = 'Completed' THEN sysdate
                                          ELSE null
                        END,
                    FINAL_STATUS    = new_task_status,
                    PRIORITY        = new_task_priority
                WHERE HISTORY_ID = latest_task_hist_table(hist_ind).HISTORY_ID;
            ELSE
                -- New assignment: insert a new current task history
                INSERT INTO TASK_HISTORY(TASK_ID, START_DATE, DUE_DATE, ASSIGNED_TO, FINAL_STATUS, PRIORITY)
                VALUES (latest_task_hist_table(hist_ind).TASK_ID,
                        latest_task_hist_table(hist_ind).START_DATE,
                        new_task_due_date,
                        new_task_assigned_to,
                        new_task_status,
                        new_task_priority);
            END IF;
        END IF;

    END AFTER STATEMENT;
    END UPDATE_TASK_RECURRENCE;
/

create or replace trigger INITIALISE_TASK_RECURRENCE
    instead of insert
    on TASKS
    for each row
    COMPOUND TRIGGER

BEFORE EACH ROW IS
BEGIN
    IF (VALIDATE_TASK_DATES(:NEW.DUE_DATE, CURRENT_DATE, :NEW.RECUR_INTERVAL) = 0) THEN
        RAISE_APPLICATION_ERROR(-20003, 'ERROR: Due dates cannot go over the period of a periodic tasks!');
    END IF;
END BEFORE EACH ROW;


    AFTER EACH ROW IS
    BEGIN

        INSERT INTO TASK_HISTORY(TASK_ID, START_DATE, DUE_DATE, ASSIGNED_TO, PRIORITY)
        VALUES (:NEW.TASK_ID, :NEW.CREATED_ON, :NEW.DUE_DATE, :NEW.ASSIGNED_TO_MEMBER_ID, :NEW.TASK_PRIORITY);

    END AFTER EACH ROW;
    END INITIALISE_TASK_RECURRENCE;
/

create or replace trigger TASK_DELETED
    before update of DELETED
    on TASKS
    for each row
    when (NEW.DELETED = 'Y')
BEGIN

    UPDATE SUBTASK
    SET DELETED = 'Y'
    WHERE SUBTASK_TO = :OLD.TASK_ID;

    DELETE
    FROM TASKINCATEGORIES
    WHERE TASKINCATEGORIES.TASK_ID = :OLD.TASK_ID;

end;
/

create or replace trigger CASCADE_ON_UPDATE_TEAM_ID
    after update of TEAM_ID
    on TEAMS
    for each row
BEGIN
    UPDATE MEMBERS
    SET MEMBERS.TEAM_ID = :NEW.TEAM_ID
    WHERE MEMBERS.TEAM_ID = :OLD.TEAM_ID;

    UPDATE TASKCATEGORIES
    SET TASKCATEGORIES.TEAM_ID = :NEW.TEAM_ID
    WHERE TASKCATEGORIES.TEAM_ID = :OLD.TEAM_ID;

    UPDATE TASKS
    SET TASKS.TEAM_ID = :NEW.TEAM_ID
    WHERE TASKS.TEAM_ID = :OLD.TEAM_ID;
end;
/

create or replace trigger CHANGING_TEAM_LEADER
    after update of TEAM_LEADER_ID
    on TEAMS
    for each row
BEGIN

    UPDATE MEMBERS
    SET MEMBER_ROLE = 'Base User'
    WHERE MEMBER_ID = :OLD.TEAM_LEADER_ID;

    UPDATE MEMBERS
    SET MEMBER_ROLE = 'Team Leader'
    WHERE MEMBER_ID = :NEW.TEAM_LEADER_ID;

    UPDATE TASKS
    SET ASSIGNED_TO_MEMBER_ID = :NEW.TEAM_LEADER_ID
    WHERE ASSIGNED_TO_MEMBER_ID = :OLD.TEAM_LEADER_ID;

    UPDATE SUBTASK
    SET ASSIGNED_TO_MEMBER_ID = :NEW.TEAM_LEADER_ID
    WHERE ASSIGNED_TO_MEMBER_ID = :OLD.TEAM_LEADER_ID;

end;
/

create or replace trigger NEW_LEADER_IS_BASE_USER
    before update of TEAM_LEADER_ID
    on TEAMS
    for each row
DECLARE
    role varchar2(50);

BEGIN

    SELECT MEMBER_ROLE
    into role
    FROM MEMBERS
    WHERE MEMBER_ID = :NEW.TEAM_LEADER_ID;

    IF (role != 'Base User') THEN
        RAISE_APPLICATION_ERROR(-20001, 'ERROR: ' || role || 's may not become leaders.');
    end if;
end;
/

create or replace trigger TEAM_DELETED
    before update of DELETED
    on TEAMS
    for each row
    when (NEW.DELETED = 'Y')
BEGIN

    UPDATE TASKS
    SET DELETED = 'Y'
    WHERE TEAM_ID = :OLD.TEAM_ID
      AND DELETED <> 'Y';

    DELETE
    FROM TASKCATEGORIES
    WHERE TEAM_ID = :OLD.TEAM_ID;

    UPDATE MEMBERS
    SET TEAM_ID = null
    WHERE TEAM_ID = :OLD.TEAM_ID;

    UPDATE MEMBERS
    SET MEMBER_ROLE = 'Base User'
    WHERE MEMBER_ID = :OLD.TEAM_LEADER_ID;

end;
/

create table TASK_HISTORY
(
    HISTORY_ID      NUMBER       default "FLORESE"."ISEQ$$_395142".nextval generated as identity,
    TASK_ID         NUMBER
        constraint FK_TASK_RECURRENCE_TASK_ID
            references TASKS
                on delete cascade,
    START_DATE      DATE not null,
    DUE_DATE        DATE not null,
    COMPLETION_DATE DATE,
    ASSIGNED_TO     NUMBER
        constraint FK_TASK_RECURRENCE_MEMBER_ID
            references MEMBERS
                on delete cascade,
    FINAL_STATUS    VARCHAR2(15) default 'Not Started',
    PRIORITY        NUMBER
        check (PRIORITY IN (1, 2, 3, 4))
)
/

create table SUBTASK_HISTORY
(
    HISTORY_ID      NUMBER       default "FLORESE"."ISEQ$$_395144".nextval generated as identity,
    SUBTASK_ID      NUMBER
        constraint FK_SUBTSK_RECUR_SUBTASK_ID
            references SUBTASK
                on delete cascade,
    START_DATE      DATE not null,
    DUE_DATE        DATE not null,
    COMPLETION_DATE DATE,
    ASSIGNED_TO     NUMBER
        constraint FK_SUBTSK_RECUR_MEMBER_ID
            references MEMBERS
                on delete cascade,
    FINAL_STATUS    VARCHAR2(15) default 'Not Started',
    PRIORITY        NUMBER
        check (PRIORITY IN (1, 2, 3, 4)),
    PARENT_ID       NUMBER
        constraint FK_SUBTSK_RECUR_PARENT_ID
            references TASKS
                on delete cascade
)
/



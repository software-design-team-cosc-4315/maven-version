package taskmanager;

import static org.junit.Assert.*;

import org.junit.jupiter.api.*;

import java.sql.*;

import static taskmanager.DBConnection.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DBConnectionTest {

    @BeforeAll
    public static void setUp() {
        assertEquals(true, DBConnection.connect());
    }

    @Test
    public void goodPreparedStatement() {
        assertNotEquals(null, prepared_statement("SELECT * FROM TEAMS"));
    }

    @Test
    void badPreparedStatement() {
        assertEquals(null, prepared_statement(""));
    }

    @Test
    public void callableStatement() {
        assertNotEquals(null, callable_statement("RENEW_TEAM_TASKS(?, ?)"));
    }

    @Test
    void set_prepared_statement_int() {
        PreparedStatement ps = prepared_statement("SELECT ? FROM TEAMS");
        assertEquals(true, set_statement_value(ps, 1, 1));
    }

    @Test
    void set_callable_statement_int() {
        CallableStatement cs = callable_statement("RENEW_TEAM_TASKS(?, ?)");
        assertEquals(true, set_statement_value(cs, 1, 1));
    }

    @Test
    void set_prepared_statement_string() {
        PreparedStatement ps = prepared_statement("SELECT ? FROM TEAMS");
        assertEquals(true, set_statement_value(ps, 1, "*"));
    }

    @Test
    void set_callable_statement_string() {
        CallableStatement cs = callable_statement("RENEW_TEAM_TASKS(?, ?)");
        assertEquals(true, set_statement_value(cs, 2, "string"));
    }

    @Test
    void set_prepared_statement_date() {
        PreparedStatement ps = prepared_statement("SELECT ? FROM TEAMS");
        assertEquals(true, set_statement_value(ps, 1, new Date(0)));
    }


    @Test
    void set_callable_statement_date() {
        CallableStatement cs = callable_statement("RENEW_TEAM_TASKS(?, ?)");
        assertEquals(true, set_statement_value(cs, 2, new Date(0)));
    }


    @Test
    void register_out_parameter() {
        boolean loaded;
        CallableStatement cs = DBConnection.callable_statement("RENEW_TEAM_TASKS(?, ?)");
        loaded = (cs != null) &&
                DBConnection.set_statement_value(cs, 1, "SystemController.current_team.team_ID()") &&
                DBConnection.register_out_parameter(cs, 2, Types.VARCHAR);
        assertEquals(true, loaded);
    }

    @Test
    void execute() {
        CallableStatement cs = DBConnection.callable_statement("RENEW_TEAM_TASKS(?, ?)");
        DBConnection.set_statement_value(cs, 1, "SystemController.current_team.team_ID()");
        DBConnection.register_out_parameter(cs, 2, Types.VARCHAR);
        assertEquals(true, DBConnection.execute(cs));
    }

    @Test
    void execute_good_query(){
        PreparedStatement ps = prepared_statement("SELECT ? FROM TEAMS");
        set_statement_value(ps, 1, "*");
        ResultSet rs = DBConnection.execute_query(ps);
        assert rs != null;
    }

    @Test
    void execute_bad_query(){
        PreparedStatement ps = prepared_statement("SELECTING FROM TEAMS");
        set_statement_value(ps, 1, "*");
        ResultSet rs = DBConnection.execute_query(ps);
        assert rs == null;
    }

    @Test
    void update() {
        assert(DBConnection.update("INSERT INTO TASKCATEGORIES(NAME, " +
                "CATEGORY_DESCRIPTION, CREATED_BY_MEMBER_ID, TEAM_ID) " +
                "VALUES('__test_task_category__', '__This is a test task category.__', 141, 'presentationteam')", true));

    }

    @AfterAll
    public static void tearDown() {
        PreparedStatement ps = DBConnection.prepared_statement("DELETE FROM TASKCATEGORIES " +
                "WHERE NAME = '__test_task_category__'");
        DBConnection.execute_update(ps, true);

        assertEquals(true, DBConnection.disconnect());
    }
}
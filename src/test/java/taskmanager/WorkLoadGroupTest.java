package taskmanager;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Erick Flores
 */

class WorkLoadGroupTest {

    private static WorkLoadGroup Work_Load_Group;

    @BeforeEach
    void setUp() {
        Work_Load_Group = new WorkLoadGroup();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void set_member_count() {
        Work_Load_Group.set_member_count(10);
        assertEquals(10, Work_Load_Group.get_member_count());
    }

    @org.junit.jupiter.api.Test
    void set_record() {
        Work_Load_Group.set_record(3, 5, "Completed");

        Work_Load_Group.set_record(1, 4, "In Progress");

        Work_Load_Group.set_record(2, 6, "Not Started");

        assertEquals(6, Work_Load_Group.get_total_workload());
    }

    @org.junit.jupiter.api.Test
    void get_member_count() {
        Work_Load_Group.set_member_count(8);
        assertEquals(8, Work_Load_Group.get_member_count());
    }

    @org.junit.jupiter.api.Test
    void get_total_workload() {
        Work_Load_Group.set_record(8, 5, "Completed");

        Work_Load_Group.set_record(3, 4, "In Progress");

        Work_Load_Group.set_record(7, 6, "Not Started");

        assertEquals(18, Work_Load_Group.get_total_workload());
    }

    @org.junit.jupiter.api.Test
    void get_workload() {
        Work_Load_Group.set_record(10, 5, "Completed");

        assertEquals(10, Work_Load_Group.get_workload(Work_Load_Group.COMPLETED));
    }

    @org.junit.jupiter.api.Test
    void get_workload_portion() {
        Work_Load_Group.set_record(9, 5, "Completed");

        Work_Load_Group.set_record(4, 4, "In Progress");

        Work_Load_Group.set_record(5, 6, "Not Started");

        assertEquals(.5f, Work_Load_Group.get_workload_portion(Work_Load_Group.COMPLETED));
    }

    @org.junit.jupiter.api.Test
    void compute_productivity() {


        Work_Load_Group.set_record(3, 5, "Completed");

        Work_Load_Group.set_record(1, 4, "In Progress");

        Work_Load_Group.set_record(2, 6, "Not Started");

        Work_Load_Group.set_member_count(10);

        float expectProd = 6 * (.5f + ((1/6f)  *.5f));
        if(10 > 1){
            expectProd /= (float)(10 * Math.log(10));
        }

        assertEquals(expectProd, Work_Load_Group.compute_productivity());

    }
}
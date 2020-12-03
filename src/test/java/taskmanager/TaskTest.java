package taskmanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    public Task testTask = new Task();
    public Task testTask2 = new Task();
    public Subtask testSubtask = new Subtask(testTask);
    public Subtask testSubtask2 = new Subtask(testTask2);



    @BeforeEach
    void setUp() {
        testSubtask.set_name("testSubtask");
        testTask.add_subtask(testSubtask);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void get_subtask() {
        assertEquals(testSubtask, testTask.get_subtask("testSubtask"));
    }

    @Test
    void get_subtasks() {
        assertEquals(true, testTask.get_subtasks()[0].name().equals("testSubtask") );
    }

    @Test
    void subtask_collection() {

        Collection testCol = testTask.subtask_collection();

        System.out.println(testTask.subtask_collection());
        System.out.println(testCol);
        assertEquals(testTask.subtask_collection(),testCol);

    }

    @Test
    void add_subtask() {
        //Should print that you cannot add a subtask already part of another task
        testTask.add_subtask(testSubtask2);
    }

    @Test
    void remove_subtask() {
        testTask.remove_subtask("testSubtask");
        assertEquals(null, testTask.get_subtask("testSubtask"));

    }

    @Test
    void recurrence_type() {
        testTask.set_recur_interval(7);
        assertEquals(testTask.recurrence_type(),"weekly");
    }

    @Test
    void set_recur_interval() {
        //also tests recur_interval()
        testTask.set_recur_interval(7);
        assertEquals(testTask.recur_interval(),7);
    }

    @Test
    void to_recur_interval() {
        String recurrance = "weekly";
        assertEquals(Task.to_recur_interval(recurrance),7);
    }
}
package taskmanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    private Task _father;
    private Task _mother;
    private static Subtask sub;

    @BeforeEach
    void setUp() {
        _father = new Task();
        sub = new Subtask(_father);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parent_task_ID() {
        _father.set_ID(23);
        assertEquals(23, sub.parent_task_ID());
    }

    @Test
    void parent_task() {
        assertEquals(_father, sub.parent_task());
    }

    @Test
    void set_parent_task() {
        _mother = new Task();
        sub.set_name("hi");
        sub.set_parent_task(_mother);
        assertEquals(_mother, sub.parent_task());
    }
}
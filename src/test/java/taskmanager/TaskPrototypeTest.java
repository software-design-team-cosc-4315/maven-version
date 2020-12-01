package taskmanager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.Date;
import java.awt.Color;



public class TaskPrototypeTest {
    
    private static TaskPrototype test_task_proto;
    
    
    
    @BeforeAll
    public static void init() {
        SystemController.current_user = new AppUser();
        //SystemController.current_user.setRole(AppUser.UserType.MANAGER);
        test_task_proto = new TaskPrototype();
    }
    
    
    @Test
    public void testDueDate() {
        Date current = new Date();
        test_task_proto.set_due_date(current);
        assertEquals(true, test_task_proto.str_due_date().equals(DBConnection.__date_format__.format(current)));
    }
    
    @Test
    public void testCreationDate() {
        Date current = new Date();
        test_task_proto.set_created_on(current);
        assertEquals(true, test_task_proto.str_created_on().equals(DBConnection.__date_format__.format(current)));
    }
    
    @Test
    public void testColour() {
        test_task_proto.set_priority((short) 3);
        Color colour = test_task_proto.color();
        assertEquals(true, colour.getRed() == 255 && colour.getGreen() == 255 && colour.getBlue() == 0);
    }
    
    @Test
    public void testManagerGetCreatorID() {
        SystemController.current_user.setRole(AppUser.UserType.MANAGER);
        test_task_proto.set_creator_ID(33);
        assertEquals(test_task_proto.creator_ID(), 33);
    }
    
    @Test
    public void testBaseUserGetCreatorID() {
        SystemController.current_user.setRole(AppUser.UserType.BASE_USER);
        assertEquals(test_task_proto.creator_ID(), -1);
    }
    
}
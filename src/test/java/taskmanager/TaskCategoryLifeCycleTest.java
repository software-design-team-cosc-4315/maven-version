/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

import java.sql.*;
import java.util.Calendar;


/**
 *
 * @author Ganondorfjallida
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskCategoryLifeCycleTest {
    
    private static java.util.Date creation_date;
    private static Calendar calendar;
    private static int test_task_category_ID;
    private static int category_task_ID;
    
    @BeforeAll
    public static void init() {
        creation_date = new java.util.Date();
        calendar = Calendar.getInstance();
        test_task_category_ID = -1;
        category_task_ID = -1;
    }
    
    
    @Test
    @Order(1)
    public void testCreateCategory() {
        
        DBConnection.connect();
        PreparedStatement ps = DBConnection.prepared_statement("INSERT INTO TASKCATEGORIES(NAME, CATEGORY_DESCRIPTION, CREATED_BY_MEMBER_ID, TEAM_ID) VALUES(?, ?, ?, ?)");
        assertNotEquals(ps, null);
        assertEquals(true, DBConnection.set_statement_value(ps, 1, "__test_task_category__") );
        assertEquals(true, DBConnection.set_statement_value(ps, 2, "__This is a test task category.__") );
        assertEquals(true, DBConnection.set_statement_value(ps, 3, 141) ); // creator ID
        assertEquals(true, DBConnection.set_statement_value(ps, 4, "presentationteam") ); // team ID
        
        assertEquals(true, DBConnection.execute_update(ps, true) );
        DBConnection.disconnect();
        
    }
    
    
    @Test
    @Order(2)
    public void testSelectCreatedCategory() {
        
        DBConnection.connect();
        PreparedStatement ps = DBConnection.prepared_statement("SELECT TASK_CATEGORY_ID FROM TASKCATEGORIES WHERE NAME = ? AND TEAM_ID = ?");
        assertNotEquals(ps, null);
        assertEquals(true, DBConnection.set_statement_value(ps, 1, "__test_task_category__") );
        assertEquals(true, DBConnection.set_statement_value(ps, 2, "presentationteam") );
        ResultSet rs = DBConnection.execute_query(ps);
        
        try { 
            assertEquals(true, rs.next()); 
            test_task_category_ID = rs.getInt("TASK_CATEGORY_ID"); 
        }  catch (Exception e) { 
            assertEquals(true, false); 
        }
        DBConnection.disconnect();
    }
    
    
    @Test
    @Order(3)
    public void testUpdateCategory() {
        
        DBConnection.connect();
        PreparedStatement ps = DBConnection.prepared_statement("UPDATE TASKCATEGORIES SET NAME = ?, CATEGORY_DESCRIPTION = ? WHERE NAME = ? AND TEAM_ID = ?");
        assertEquals(true, DBConnection.set_statement_value(ps, 1, "__test_updated_task_category__") );
        assertEquals(true, DBConnection.set_statement_value(ps, 2, "__Changed description__") );
        assertEquals(true, DBConnection.set_statement_value(ps, 3, "__test_task_category__") );
        assertEquals(true, DBConnection.set_statement_value(ps, 4, "presentationteam") );
        
        assertEquals(true, DBConnection.execute_update(ps, true) );
        DBConnection.disconnect();
        
    }
    
    @Test
    @Order(4)
    public void testSelectUpdatedCategory() {
        
        DBConnection.connect();
        PreparedStatement ps = DBConnection.prepared_statement("SELECT TASK_CATEGORY_ID, CATEGORY_DESCRIPTION FROM TASKCATEGORIES WHERE NAME = ? AND TEAM_ID = ?");
        assertEquals(true, DBConnection.set_statement_value(ps, 1, "__test_updated_task_category__") );
        assertEquals(true, DBConnection.set_statement_value(ps, 2, "presentationteam") );
        ResultSet rs = DBConnection.execute_query(ps);
        
        try {
            assertEquals(true, rs.next());
            assertEquals(rs.getInt("TASK_CATEGORY_ID"), test_task_category_ID);
            assertEquals(true, rs.getString("CATEGORY_DESCRIPTION").equals("__Changed description__"));
        } catch (Exception e) {
            assertEquals(true, false); 
        }
        DBConnection.disconnect();
        
    }
    
    
    @Test
    @Order(5)
    public void testAddTaskToCategory() {
        
        calendar.setTime(creation_date);
        calendar.add(Calendar.DATE, 15);
        
        DBConnection.connect();
        DBConnection.transaction(DBConnection.Transaction.BEGIN);
        PreparedStatement ps = DBConnection.prepared_statement("INSERT INTO TASKS(NAME, TASK_DESCRIPTION, DUE_DATE, CREATED_BY_MEMBER_ID, TASK_PRIORITY, ASSIGNED_TO_MEMBER_ID, TEAM_ID, RECUR_INTERVAL) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        DBConnection.set_statement_value(ps, 1, "__test_category_task__");
        DBConnection.set_statement_value(ps, 2, "__This is a task for a test category.__");
        DBConnection.set_statement_value(ps, 3, new java.sql.Date(calendar.getTimeInMillis()));
        DBConnection.set_statement_value(ps, 4, 141); // creator ID
        DBConnection.set_statement_value(ps, 5, 2);  // priority
        DBConnection.set_statement_value(ps, 6, 157);  // team leader ID
        DBConnection.set_statement_value(ps, 7, "presentationteam"); // team ID
        DBConnection.set_statement_value(ps, 8, 0); // interval of recurrence
        DBConnection.execute_update(ps, true);
        
        ResultSet rs = DBConnection.query("SELECT TASK_ID FROM TASKS WHERE NAME = '__test_category_task__' AND TEAM_ID = 'presentationteam'");
        try {
            rs.next(); 
            category_task_ID = rs.getInt("TASK_ID");
        } catch(Exception e) {}
        
        
        ps = DBConnection.prepared_statement("INSERT INTO TASKINCATEGORIES(TASK_ID, TASK_CATEGORY_ID) VALUES(?, ?)");
        DBConnection.set_statement_value(ps, 1, category_task_ID);
        DBConnection.set_statement_value(ps, 2, test_task_category_ID);
        assertEquals(true, DBConnection.execute_update(ps, true) );
        
        DBConnection.transaction(DBConnection.Transaction.END);
        DBConnection.disconnect();
        
    }
    
    @Test
    @Order(6)
    public void testRemoveTaskFromCategory() {
        
        DBConnection.connect();
        PreparedStatement ps = DBConnection.prepared_statement("UPDATE TASKS SET DELETED = 'Y' WHERE TASK_ID = ?");
        DBConnection.set_statement_value(ps, 1, category_task_ID);
        assertEquals(true, DBConnection.execute_update(ps, true));
        DBConnection.disconnect();
        
    }
    
    @Test
    @Order(7)
    public void testDeleteCategory() {
        
        DBConnection.connect();
        PreparedStatement ps = DBConnection.prepared_statement("DELETE FROM TASKCATEGORIES WHERE NAME = ? and TEAM_ID = ?");
        assertEquals(true, DBConnection.set_statement_value(ps, 1, "__test_updated_task_category__") );
        assertEquals(true, DBConnection.set_statement_value(ps, 2, "presentationteam") );
        assertEquals(true, DBConnection.execute_update(ps, true) );
        DBConnection.disconnect();
        
    }
    
    
    @AfterAll
    public static void cleanUpTestedDatabaseRecords() {
        
        DBConnection.connect();
        DBConnection.transaction(DBConnection.Transaction.BEGIN);
        
        // clean the task history:
        PreparedStatement ps = DBConnection.prepared_statement("DELETE FROM TASK_HISTORY WHERE TASK_ID = ?");
        boolean cleaned = (ps != null) && DBConnection.set_statement_value(ps, 1, category_task_ID);
        cleaned = cleaned && DBConnection.execute_update(ps, true);
        
        // clean the task:
        ps = DBConnection.prepared_statement("DELETE FROM TASKS WHERE TASK_ID = ?");
        cleaned = cleaned && (ps != null);
        cleaned = cleaned && DBConnection.set_statement_value(ps, 1, category_task_ID);
        cleaned = cleaned && DBConnection.execute_update(ps, true);
        
        if (!cleaned) {
            System.out.println("ERROR: Task category life-cycle test clean-up failed! Please remove the test records from the database manually.");
            DBConnection.transaction(DBConnection.Transaction.ROLLBACK);
        }
        
        DBConnection.transaction(DBConnection.Transaction.END);
        DBConnection.disconnect();
        
    }
    
}

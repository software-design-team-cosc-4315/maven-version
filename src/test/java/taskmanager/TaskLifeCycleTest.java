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
import java.text.SimpleDateFormat;

/**
 *
 * @author Ganondorfjallida
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskLifeCycleTest {
    
    private static java.util.Date creation_date;
    private static Calendar calendar;
    private static int test_task_ID;
    
    
    @BeforeAll
    public static void init() {
        creation_date = new java.util.Date();
        calendar = Calendar.getInstance();
        test_task_ID = -1;
        DBConnection.connect();
    }
    
    
    @Test
    @Order(1)
    public void testCreateTask() {
        
        calendar.setTime(creation_date);
        calendar.add(Calendar.DATE, 2);

        PreparedStatement ps = DBConnection.prepared_statement("INSERT INTO TASKS(NAME, TASK_DESCRIPTION, DUE_DATE, CREATED_BY_MEMBER_ID, TASK_PRIORITY, ASSIGNED_TO_MEMBER_ID, TEAM_ID, RECUR_INTERVAL) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
        assertEquals(true, ps != null);
        assertEquals(true,  DBConnection.set_statement_value(ps, 1, "__test_non_recurrent_task__") );
        assertEquals(true,  DBConnection.set_statement_value(ps, 2, "__This is a test task.__") );
        assertEquals(true,  DBConnection.set_statement_value(ps, 3, new java.sql.Date(calendar.getTimeInMillis())) );
        assertEquals(true,  DBConnection.set_statement_value(ps, 4, 141) ); // creator ID
        assertEquals(true,  DBConnection.set_statement_value(ps, 5, 2) );  // priority
        assertEquals(true,  DBConnection.set_statement_value(ps, 6, 157) );  // team leader ID
        assertEquals(true,  DBConnection.set_statement_value(ps, 7, "presentationteam") ); // team ID
        assertEquals(true,  DBConnection.set_statement_value(ps, 8, 0) ); // interval of recurrence
        
        assertEquals(true,  DBConnection.execute_update(ps, true) );
    }
    
    
    @Test
    @Order(2)
    public void testSelectCreatedTask() {
        PreparedStatement ps = DBConnection.prepared_statement("SELECT TASK_ID FROM TASKS WHERE NAME = ? AND TEAM_ID = ? AND DELETED = 'N'");
        assertEquals(true, ps != null);
        assertEquals(true,  DBConnection.set_statement_value(ps, 1, "__test_non_recurrent_task__") );
        assertEquals(true,  DBConnection.set_statement_value(ps, 2, "presentationteam") );
        ResultSet rs = DBConnection.execute_query(ps);
        
        try { 
            assertEquals(true, rs.next()); 
            test_task_ID = rs.getInt("TASK_ID"); 
        }  catch (Exception e) { 
            assertEquals(true, false); 
        }
    }
    
    
    @Test
    @Order(3)
    public void testUpdateTask() {
        
        calendar.setTime(creation_date);
        calendar.add(Calendar.DATE, 15);

        PreparedStatement ps = DBConnection.prepared_statement("UPDATE TASKS SET NAME = ?, TASK_DESCRIPTION = ?, DUE_DATE = ?, TASK_PRIORITY = ?, RECUR_INTERVAL = ?, STATUS = ? WHERE NAME = ? AND TEAM_ID = ? AND DELETED <> 'Y'");
        assertEquals(true,  DBConnection.set_statement_value(ps, 1, "__test_monthly_task__") );
        assertEquals(true,  DBConnection.set_statement_value(ps, 2, "__Changed description__") );
        assertEquals(true,  DBConnection.set_statement_value(ps, 3, new java.sql.Date(calendar.getTimeInMillis())) );
        assertEquals(true,  DBConnection.set_statement_value(ps, 4, 1) );  // priority
        assertEquals(true,  DBConnection.set_statement_value(ps, 5, 30) ); // interval of recurrence
        assertEquals(true,  DBConnection.set_statement_value(ps, 6, "In Progress") );
        assertEquals(true,  DBConnection.set_statement_value(ps, 7, "__test_non_recurrent_task__") );
        assertEquals(true,  DBConnection.set_statement_value(ps, 8, "presentationteam") );
        
        assertEquals(true,  DBConnection.execute_update(ps, true) );
    }
    
    @Test
    @Order(4)
    public void testSelectUpdatedTask() {

        PreparedStatement ps = DBConnection.prepared_statement("SELECT TASK_ID, TASK_DESCRIPTION, DUE_DATE, TASK_PRIORITY, RECUR_INTERVAL, STATUS FROM TASKS WHERE NAME = ? AND TEAM_ID = ? AND DELETED <> 'Y'");
        assertEquals(true,  DBConnection.set_statement_value(ps, 1, "__test_monthly_task__") );
        assertEquals(true,  DBConnection.set_statement_value(ps, 2, "presentationteam") );
        ResultSet rs = DBConnection.execute_query(ps);
        
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            assertEquals(true, rs.next());
            assertEquals(rs.getInt("TASK_ID"), test_task_ID);
            assertEquals(true, rs.getString("TASK_DESCRIPTION").equals("__Changed description__"));
            assertEquals(true, rs.getDate("DUE_DATE").toString().equals(date_format.format(calendar.getTime())));
            assertEquals(rs.getInt("TASK_PRIORITY"), 1);
            assertEquals(rs.getInt("RECUR_INTERVAL"), 30);
            assertEquals(true, rs.getString("STATUS").equals("In Progress"));
        } catch (Exception e) {
            assertEquals(true, false);
        }
    }
    
    
    @Test
    @Order(5)
    public void testDeleteTask() {

        PreparedStatement ps = DBConnection.prepared_statement("UPDATE TASKS SET DELETED = 'Y' WHERE NAME = ? and TEAM_ID = ? AND DELETED != 'Y'");
        assertEquals(true,  DBConnection.set_statement_value(ps, 1, "__test_monthly_task__") );
        assertEquals(true,  DBConnection.set_statement_value(ps, 2, "presentationteam") );
        assertEquals(true,  DBConnection.execute_update(ps, true) );
    }
    
    
    
    
    @AfterAll
    public static void cleanUpTestedDatabaseRecords() {
        DBConnection.transaction(DBConnection.Transaction.BEGIN);
        
        // clean the task history:
        PreparedStatement ps = DBConnection.prepared_statement("DELETE FROM TASK_HISTORY WHERE TASK_ID = ?");
        boolean cleaned = (ps != null)
            && DBConnection.set_statement_value(ps, 1, test_task_ID)
            && DBConnection.execute_update(ps, true);
        
        // clean the task:
        ps = DBConnection.prepared_statement("DELETE FROM TASKS WHERE TASK_ID = ?");
        cleaned = cleaned && (ps != null)
            && DBConnection.set_statement_value(ps, 1, test_task_ID)
            && DBConnection.execute_update(ps, true);
        
        if (!cleaned) {
            System.out.println("ERROR: Task life-cycle test clean-up failed! Please remove the test records from the database manually.");
            DBConnection.transaction(DBConnection.Transaction.ROLLBACK);
        }
        
        DBConnection.transaction(DBConnection.Transaction.END);
        DBConnection.disconnect();
    }
}

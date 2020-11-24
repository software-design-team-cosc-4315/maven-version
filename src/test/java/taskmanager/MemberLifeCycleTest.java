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
import org.junit.jupiter.api.AfterAll;

import java.sql.*;

/**
 *
 * @author Ganondorfjallida
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MemberLifeCycleTest {
    
    
    
    @Test
    @Order(1)
    public void testCreateUser() {
        
        DBConnection.connect();
        PreparedStatement ps = DBConnection.prepared_statement("INSERT INTO MEMBERS(USERNAME, MEMBER_PASSWORD, MEMBER_ROLE) VALUES(?, ?, ?)");
        DBConnection.set_statement_value(ps, 1, "__test_username__");
        DBConnection.set_statement_value(ps, 2, "__test_password__");
        DBConnection.set_statement_value(ps, 3, "Base User");
        assertEquals(true, DBConnection.execute_update(ps, true));
        DBConnection.disconnect();
        
    }
    
    @Test
    @Order(2)
    public void testUpdateUser() {
        
        DBConnection.connect();
        PreparedStatement ps = DBConnection.prepared_statement("UPDATE MEMBERS SET USERNAME = ?, MEMBER_PASSWORD = ?, MEMBER_ROLE = ? WHERE USERNAME = ?");
        DBConnection.set_statement_value(ps, 1, "__test_new_username__");
        DBConnection.set_statement_value(ps, 2, "__test_new_password__");
        DBConnection.set_statement_value(ps, 3, "Manager");
        DBConnection.set_statement_value(ps, 4, "__test_username__");
        assertEquals(true, DBConnection.execute_update(ps, true));
        DBConnection.disconnect();
        
    }
    
    @Test
    @Order(3)
    public void testDeleteUser() {
        
        DBConnection.connect();
        PreparedStatement ps = DBConnection.prepared_statement("UPDATE MEMBERS SET DELETED = 'Y' WHERE USERNAME = ?");
        DBConnection.set_statement_value(ps, 1, "__test_new_username__");
        assertEquals(true, DBConnection.execute_update(ps, true) );
        DBConnection.disconnect();
        
    }
    
    @AfterAll
    public static void cleanUpTestedDatabaseRecords() {
        
        DBConnection.connect();
        PreparedStatement ps = DBConnection.prepared_statement("DELETE FROM MEMBERS WHERE USERNAME = ?");
        boolean cleaned = (ps != null) && DBConnection.set_statement_value(ps, 1, "__test_new_username__");
        cleaned = cleaned && DBConnection.execute_update(ps, true);
        if (!cleaned)
            System.out.println("ERROR: Member life-cycle test clean-up failed! Please remove the test records from the database manually.");
        DBConnection.disconnect();
        
    }
    
}

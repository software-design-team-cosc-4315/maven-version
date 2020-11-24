/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author Ganondorfjallida
 */
public class DBConnection {
    
    
    public enum Transaction { BEGIN, ROLLBACK, END }
    public enum Action { QUERY, UPDATE }
    
    
    
    // Connection pointer:
    public static Connection connection = null;
    
    
    

    // Auxiliary variables:
    public static final SimpleDateFormat __date_format__ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
    private static Savepoint __save_point__ = null;
    private static boolean __in_transaction__ = false;
    
    
    
    
    public static boolean connect() {
        // Connect to Database:
        try {
            // Load the JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // Create a connection
            DBConnection.connection = DriverManager.getConnection("jdbc:oracle:thin:@129.7.240.3:1521:ORCL", "FLORESE", "FLORESE#");
            return true;
        } catch (Exception e) {
            System.out.println("--- ERROR CONNECTING ---");
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean disconnect() {
        // Close the connection object  
        try { 
            DBConnection.connection.close(); 
            DBConnection.connection = null;
            return true;
        }
        catch (Exception e) {
            System.out.println("--- ERROR DISCONNECTING ---");
            System.out.println(e);
            return false;
        }
    }
    
    
    @Nullable
    public static PreparedStatement prepared_statement(String statement) {
        try { return DBConnection.connection.prepareStatement(statement); }
        catch (Exception e) { 
            System.out.println("--- STATEMENT PREPARATION ERROR ---");
            System.out.println(e);
        }
        return null;
    }
    
    @Nullable
    public static CallableStatement callable_statement(String statement) {
        try { return DBConnection.connection.prepareCall("{call " + statement + "}"); }
        catch(Exception e) {
            System.out.println("--- CALLABLE STATEMENT ERROR ---");
            System.out.println(e);
        }
        return null;
    }
    
    
    public static boolean set_statement_value(PreparedStatement ps, int index, int value) {
        try { 
            ps.setInt(index, value);
            return true;
        }
        catch (Exception e) { 
            System.out.println("--- STATEMENT VALUE ERROR ---"); 
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean set_statement_value(CallableStatement cs, int index, int value) {
        try {
            cs.setInt(index, value);
            return true;
        }
        catch (Exception e) {
            System.out.println("--- STATEMENT VALUE ERROR ---");
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean set_statement_value(PreparedStatement ps, int index, String value) {
        try { 
            ps.setString(index, value); 
            return true;
        }
        catch (Exception e) { 
            System.out.println("--- STATEMENT VALUE ERROR ---"); 
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean set_statement_value(CallableStatement cs, int index, String value) {
        try {
            cs.setString(index, value);
            return true;
        }
        catch (Exception e) {
            System.out.println("--- STATEMENT VALUE ERROR ---");
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean set_statement_value(PreparedStatement ps, int index, Date value) {
        try { 
            ps.setDate(index, value);
            return true;
        }
        catch (Exception e) { 
            System.out.println("--- STATEMENT VALUE ERROR ---"); 
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean set_statement_value(CallableStatement cs, int index, Date value) {
        try {
            cs.setDate(index, value);
            return true;
        }
        catch (Exception e) {
            System.out.println("--- STATEMENT VALUE ERROR ---");
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean register_out_parameter(CallableStatement cs, int index, int sqlType) {
        try {
            cs.registerOutParameter(index, sqlType);
            return true;
        } catch(Exception e) {
            System.out.println("--- OUT PARAMETER REGISTRATION ERROR ---");
            System.out.println(e);
            return false;
        }
    }
    
    public static boolean execute(CallableStatement cs) {
        try {
            cs.execute();
            return true;
        } catch (Exception e) {
            System.out.println("--- STATEMENT EXECUTION ERROR ---");
            System.out.println(e);
            return false;
        }
    }
    
    @Nullable
    public static ResultSet execute_query(PreparedStatement ps) {
        try { return ps.executeQuery(); } 
        catch (Exception e) {
            System.out.println("--- QUERY ERROR ---");
            System.out.println(e);
        }
        return null;
    }
    
    public static boolean execute_update(PreparedStatement ps) {
        return DBConnection.execute_update(ps, false);
    }
    
    public static boolean execute_update(PreparedStatement ps, boolean mandatory_update) {
        try { 
            int row_count = ps.executeUpdate(); 
            if (row_count == 0 && mandatory_update) {
                System.out.println("--- UPDATE ERROR ---");
                System.out.println("-> No rows were updated.");
                return false;
            }
            return true; 
        }
        catch (Exception e) {
            System.out.println("--- UPDATE ERROR ---");
            System.out.println(e);
            return false;
        }
    }
    
    
    public static ResultSet query(String statement) {
        ResultSet rs;
        try {
            Statement stmt = DBConnection.connection.createStatement();
            rs = stmt.executeQuery(statement);
        } catch (Exception e) {
            System.out.println("--- QUERY ERROR ---");
            System.out.println(e);
            rs = null;
        }
        return rs;
    }
    
    public static boolean update(String statement) {
        return DBConnection.update(statement, false);
    }
    
    public static boolean update(String statement, boolean mandatory_update) {
        try {
            Statement stmt = DBConnection.connection.createStatement();
            int row_count = stmt.executeUpdate(statement);
            if (row_count == 0 && mandatory_update) {
                System.out.println("--- UPDATE ERROR ---");
                System.out.println("-> No rows were updated.");
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("--- UPDATE ERROR ---");
            System.out.println(e);
            return false;
        }
    }
    
    
    
    // Transaction command:
    public static void transaction(@NotNull Transaction command) {
        
        switch (command) {
            case BEGIN:
                DBConnection.__begin_transaction__();
                break;
            case ROLLBACK:
                DBConnection.__rollback_transaction__();
                break;
            case END:
                DBConnection.__end_transaction__();
                break;
            default:
                // Invalid transaction command:
                System.out.println("TRANSACTION COMMAND ERROR: Invalid transaction command {" + command + "}");
        }
        
    }
    
    
    private static void __begin_transaction__() {
        if (DBConnection.__in_transaction__) {
            System.out.println("TRANSACTION ERROR: System is processing another transaction and cannot start a new transaction yet!");
            return;
        }
                
        try {
            DBConnection.connection.setAutoCommit(false);
            DBConnection.__save_point__ = DBConnection.connection.setSavepoint(); 
            DBConnection.__in_transaction__ = true;
        } catch(SQLException e) { 
            System.out.println("TRANSACTION ERROR: There was an error when starting the transaction. Transaction aborted!");
            System.out.println(e);
            DBConnection.__save_point__ = null;
            DBConnection.__in_transaction__ = false;
        }
    }
    
    private static void __rollback_transaction__() {
        if (!DBConnection.__in_transaction__) {
            System.out.println("TRANSACTION ERROR: System is not processing a transaction and cannot make a rollback!");
            return;
        }

        try {
            DBConnection.connection.rollback(DBConnection.__save_point__);
            DBConnection.connection.commit();
        } catch (SQLException e) {
            System.out.println("TRANSACTION ERROR: There was an error when rolling back the transaction. Rollback aborted!");
            System.out.println(e);
        }
    }
    
    private static void __end_transaction__() {
        if (!DBConnection.__in_transaction__) {
            System.out.println("TRANSACTION ERROR: System is not processing a transaction and cannot commit anything!");
            return;
        }

        try {
            DBConnection.connection.commit();
            DBConnection.__save_point__ = null;
            DBConnection.__in_transaction__ = false;
        } catch (SQLException e) {
            System.out.println("TRANSACTION ERROR: There was an error when finishing up the transaction. Transaction not ended!");
            System.out.println(e);
        }

        // Restore auto-commit:
        try {
            DBConnection.connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("WARNING: Database auto-commit may be turned off. Restore the auto-commit immediately!");
            System.out.println("POST-TRANSACTION ERROR: " + e);
        }
    }
    
}

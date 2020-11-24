/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;


import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;


/**
 *
 * @author Ganondorfjallida, Kevin Do
 */
public class SystemController {
    
    public enum State { 
        UNAUTHENTICATED,  
        RECENTLY_AUTHENTICATED,
        LOADED_TASK_PAGE,
        LOADED_TEAM_LEADS_PAGE,
        LOADED_MANAGERS_PAGE
    }
    
    private static LoginPage login_page = null;
    private static TaskPage task_page = null;
    private static TeamLeadersPage team_leads_page = null;
    private static ManagersPage managers_page = null;
    
    public static State current_state = State.UNAUTHENTICATED;
    public static JFrame current_page = null;
    public static AppUser current_user = null;
    public static Team current_team = null;


    public static void create_pages() {
        SystemController.login_page = new LoginPage();
        SystemController.task_page = new TaskPage();
        SystemController.team_leads_page = new TeamLeadersPage();
        SystemController.managers_page = new ManagersPage();
        
        SystemController.login_page.setTitle("Task Manager - Login");
        SystemController.task_page.setTitle("Task Manager - Tasks");
        SystemController.team_leads_page.setTitle("Task Manager - Team Leader");
        SystemController.managers_page.setTitle("Task Manager - Management");
    }
    
    
    
    
    @Nullable
    public static String authenticate(String username, char[] password) {
        
        // Use DBConnection to call statement in order to retrieve user information.
        boolean authenticated;
        DBConnection.connect();
        
        PreparedStatement ps = DBConnection.prepared_statement("SELECT MEMBER_ID, MEMBER_ROLE, TEAM_ID FROM MEMBERS WHERE USERNAME = ? AND MEMBER_PASSWORD = ? AND DELETED != 'Y'");
        authenticated = (ps != null) && DBConnection.set_statement_value(ps, 1, username);
        authenticated = authenticated && DBConnection.set_statement_value(ps, 2, new String(password));
        ResultSet rs = DBConnection.execute_query(ps);
        try {
            authenticated = authenticated && Objects.requireNonNull(rs).next();
            // store user information in SystemController.current_user
            if (authenticated) {  // ***NOTE: state change to recently authenticated
                SystemController.current_state = State.RECENTLY_AUTHENTICATED;
                SystemController.current_user = new AppUser();
                SystemController.current_user.set_ID(rs.getInt("MEMBER_ID"));
                SystemController.current_user.setUsername(username);
                SystemController.current_user.setRole( AppUser.toUserType(rs.getString("MEMBER_ROLE")) );
                SystemController.current_user.setTeamID(rs.getString("TEAM_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            authenticated = false;
        }
        
        DBConnection.disconnect();
        
        
        // If result is not authenticated, restore unauthenticated state:
        if (!authenticated) {
            SystemController.current_user = null;
            SystemController.current_state = State.UNAUTHENTICATED;
            return "Invalid user credentials!";
        }
        
        
        // Determine the user type and proceed to the next state:
        if (current_user.role() == AppUser.UserType.MANAGER)        SystemController.to_managers_page();
        else if (current_user.role() == AppUser.UserType.TEAM_LEAD) SystemController.to_team_leaders_page(null, null);
        else if (current_user.role() == AppUser.UserType.BASE_USER) SystemController.to_task_page();
        else {
            SystemController.current_user = null;
            SystemController.current_state = State.UNAUTHENTICATED;
            return "User role is not valid. Contact the managers for help.";
        }
        
        return null;
        
    }
    
    public static void log_out() {     
        // Clear user and team data:
        SystemController.current_user = null;
        SystemController.current_team = null;
        
        // Update current state:
        SystemController.current_state = State.UNAUTHENTICATED;
        
        // Switch to login page:
        if (SystemController.current_page != null) SystemController.current_page.setVisible(false);
        SystemController.current_page = SystemController.login_page;
        SystemController.current_page.setVisible(true);
    }
    
    public static void to_managers_page() {
        SystemController.current_team = null;
        SystemController.managers_page.reload();
        SystemController.current_state = State.LOADED_MANAGERS_PAGE;
        
        SystemController.current_page.setVisible(false);
        SystemController.current_page = SystemController.managers_page;
        SystemController.current_page.setVisible(true);
    }
    
    public static void to_team_leaders_page(TeamLeadersPage.Focus focus, String search_name) {
        if (SystemController.current_team == null) {
            if (!SystemController.load_current_team(SystemController.current_user.team_ID())) {
                System.out.println("ERROR: Team information was not properly loaded (Team Leader's Page).");
                return;
            }
        }
        
        SystemController.team_leads_page.reload(focus, search_name);
        SystemController.current_state = State.LOADED_TEAM_LEADS_PAGE;
        
        SystemController.current_page.setVisible(false);
        SystemController.current_page = SystemController.team_leads_page;
        SystemController.current_page.setVisible(true);
    }
    
    public static void to_task_page() {
        if (SystemController.current_team == null) {
            if (!SystemController.load_current_team(SystemController.current_user.team_ID())) {
                System.out.println("ERROR: Team information was not properly loaded (Task Page).");
                return;
            }
        }
        
        SystemController.task_page.reload();
        SystemController.current_state = State.LOADED_TASK_PAGE;
        
        SystemController.current_page.setVisible(false);
        SystemController.current_page = SystemController.task_page;
        SystemController.current_page.setVisible(true);
    }
    
    
    public static boolean load_current_team(String team_ID) {
        // Query to retrieve the information of the current team:
        boolean team_info_retrieved;
        DBConnection.connect();
            
        PreparedStatement ps = DBConnection.prepared_statement("SELECT L.USERNAME FROM TEAMS T, MEMBERS L WHERE T.TEAM_ID = ? AND L.MEMBER_ID = T.TEAM_LEADER_ID AND T.DELETED != 'Y' AND L.DELETED != 'Y'");
        team_info_retrieved = (ps != null) && DBConnection.set_statement_value(ps, 1, team_ID);
        ResultSet rs = team_info_retrieved? DBConnection.execute_query(ps) : null;
        try {
            team_info_retrieved = team_info_retrieved && Objects.requireNonNull(rs).next();
            if (team_info_retrieved) {
                SystemController.current_team = new Team();
                SystemController.current_team.set_team_ID(team_ID);
                SystemController.current_team.set_leader_username(rs.getString("USERNAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            team_info_retrieved = false;
        }
            
        DBConnection.disconnect();
        return team_info_retrieved;
    }
    
    
    /*
        Function to validate the current user's information when attempting to transition to the team leader's page:
    */
    public static boolean validate_user_for_team_leaders_page() {
        AppUser current_user = SystemController.current_user;
        if ( current_user.role() == AppUser.UserType.TEAM_LEAD && !current_user.team_ID().equals(SystemController.current_team.team_ID()) ) {
            System.out.println("ERROR: Current team leader is not from this team: '" 
                               + SystemController.current_team.team_ID() 
                               + "'. Attempt to navigate to the team leader's page is illegal.");
            return true;
        }
        if (current_user.role() == AppUser.UserType.BASE_USER) {
            System.out.println("ERROR: Current user is a base user. Attempt to navigate to the team leader's page is illegal.");
            return true;
        }
        
        return false;
    }
    
    
}

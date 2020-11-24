/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author Ganondorfjallida
 */
public class AppUser {
    
    public enum UserType { MANAGER, TEAM_LEAD, BASE_USER }
    
    private static final String __BASE_USER__ = "Base User";
    private static final String __TEAM_LEAD__ = "Team Leader";
    private static final String __MANAGER__ = "Manager";
    
    
    // Member data:
    private int _ID;
    private String _username;
    private UserType _role;
    private String _team_ID;
    
    
    
    
    public AppUser() {}
    
    
    public void clear() {
        this._ID = -1;
        this._username = null;
        this._role = null;
        this._team_ID = null;
    }
    
    
    
    
    
    
    
    // Accessors and Mutators:
    public int ID() {
        if (SystemController.current_user._role != UserType.MANAGER && SystemController.current_user._ID != this._ID) {
            System.out.println("INVALID USER ID: Only managers have the priviledge to access ID's.");
            return -1;
        }
        return this._ID;
    }
    
    public String username() { return this._username; }
    public UserType role() { return this._role; }
    public String team_ID() { return this._team_ID; }
    
    
    public void set_ID(int ID) {
        if (SystemController.current_state != SystemController.State.RECENTLY_AUTHENTICATED && SystemController.current_user._role != UserType.MANAGER) System.out.println("INVALID USER ID: Only managers have the priviledge to access ID's.");
        else this._ID = ID;
    }
    
    public void set_username(String username) { this._username = username; } 
    public void set_role(UserType role) { this._role = role; }
    public void set_team_ID(String ID) { this._team_ID = ID; }
    
    
    
    
    
    @org.jetbrains.annotations.Nullable
    public static UserType to_user_type(@NotNull String type) {
        if (type.equals(__BASE_USER__))   return UserType.BASE_USER;
        if (type.equals(__TEAM_LEAD__))   return UserType.TEAM_LEAD;
        if (type.equals(__MANAGER__))     return UserType.MANAGER;
        return null;
    }
    
    @Nullable
    @Contract(pure = true)
    public static String user_type_to_string(UserType type) {
        if (type == UserType.BASE_USER) return __BASE_USER__;
        if (type == UserType.TEAM_LEAD) return __TEAM_LEAD__;
        if (type == UserType.MANAGER)   return __MANAGER__;
        return null;
    }
    
    @Nullable
    public static String valid_user_role(@NotNull String role) {
        if (role.equals(__BASE_USER__) || role.equals(__TEAM_LEAD__) || role.equals(__MANAGER__))   return role;
        return null;
    }
}

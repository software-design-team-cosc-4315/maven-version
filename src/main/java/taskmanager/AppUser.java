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
    private int id;
    private String username;
    private UserType role;
    private String teamID;

    public AppUser() {}

    public void clear() {
        this.id = -1;
        this.username = null;
        this.role = null;
        this.teamID = null;
    }

    // Accessors and Mutators:
    public int ID() {
        if (SystemController.current_user.role != UserType.MANAGER && SystemController.current_user.id != this.id) {
            System.out.println("INVALID USER ID: Only managers have the priviledge to access ID's.");
            return -1;
        }
        return this.id;
    }
    
    public String getUsername() {
        return this.username;
    }
    public UserType role() {
        return this.role;
    }
    public String team_ID() {
        return this.teamID;
    }
    
    
    public void setId(int id) {
        if (SystemController.current_state != SystemController.State.RECENTLY_AUTHENTICATED && SystemController.current_user.role != UserType.MANAGER) System.out.println("INVALID USER ID: Only managers have the priviledge to access ID's.");
        else this.id = id;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public void setRole(UserType role) {
        this.role = role;
    }
    public void setTeamID(String ID) {
        this.teamID = ID;
    }

    @org.jetbrains.annotations.Nullable
    public static UserType toUserType(@NotNull String type) {
        if (type.equals(__BASE_USER__))   return UserType.BASE_USER;
        if (type.equals(__TEAM_LEAD__))   return UserType.TEAM_LEAD;
        if (type.equals(__MANAGER__))     return UserType.MANAGER;
        return null;
    }
    
    @Nullable
    @Contract(pure = true)
    public static String userTypeToString(UserType type) {
        if (type == UserType.BASE_USER) return __BASE_USER__;
        if (type == UserType.TEAM_LEAD) return __TEAM_LEAD__;
        if (type == UserType.MANAGER)   return __MANAGER__;
        return null;
    }
}

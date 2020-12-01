/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

/**
 *
 * @author Ganondorfjallida
 */
public class Team {
    
    // Member data:
    private String _team_ID;
    private int _leader_ID;
    private String _leader_username;
    
    
    public Team() {}
    
    
    
    // Accessors and Mutators:
    public String team_ID() { return this._team_ID; }
    public int leader_ID() {
        if (SystemController.current_user.role() != AppUser.UserType.MANAGER && 
            !(SystemController.current_user.role() == AppUser.UserType.TEAM_LEAD
                && SystemController.current_user.team_ID().equals(this._team_ID)))
        {
            System.out.println("INVALID LEADER ID: Only managers have the priviledge to access ID's.");
            return -1;
        }
        return this._leader_ID;
    }
    public String leader_username() { return this._leader_username; }
    
    
    
    public void set_team_ID(String name) { this._team_ID = name; }
    public void set_leader_ID(int ID) {
        if (SystemController.current_user.role() != AppUser.UserType.MANAGER && 
            !(SystemController.current_user.role() == AppUser.UserType.TEAM_LEAD
                && SystemController.current_user.team_ID().equals(this._team_ID)))
        {    System.out.println("INVALID LEADER ID: Only managers have the priviledge to access ID's.");   }
        else this._leader_ID = ID;
    }
    public void set_leader_username(String username) { this._leader_username = username; }
    
}

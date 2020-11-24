/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 *
 * @author Kevin Do
 */
public class TaskPrototype {
    
    // Member data:
    protected int _ID;
    protected String _name;
    protected String _description;
    protected java.util.Date _due_date;
    protected java.util.Date _created_on;
    protected int _creator_ID;
    protected String _creator_username;
    protected String _status;
    protected short _priority;
    protected Color _color;
    protected String _assigned_to_member_ID;
    protected String _assigned_to_member_username;
    protected String _team_ID;

    
    public TaskPrototype() {}
    
    
    // Accessors and Mutators:

    //Access
    public int ID() { return this._ID; }
    public String name() { return this._name; }
    public String description() { return this._description; }
    public java.util.Date due_date() { return this._due_date; }
    public String str_due_date() { return DBConnection.__date_format__.format(this._due_date); }
    public java.util.Date created_on() { return this._created_on; }
    public String str_created_on() { return DBConnection.__date_format__.format(this._created_on); }
    public int creator_ID() { 
        if (SystemController.current_user.role() != AppUser.UserType.MANAGER) {
            System.out.println("INVALID CREATOR ID: Only managers have the priviledge to access ID's.");
            return -1;
        }
        return this._creator_ID; 
    }
    public String creator_username() { return this._creator_username; }
    public String status() { return this._status; }
    public Color color() { return this._color; }
    public short priority() { return this._priority; }
    public String assigned_to_member_ID() { return this._assigned_to_member_ID; }
    public String assigned_to_member_username() { return this._assigned_to_member_username; }
    public String team_ID() { return this._team_ID; }
    


    // Set changes
    public void set_ID(int ID) { this._ID = ID; }
    public void set_name(String name) { this._name = name; }
    public void set_description(String description) { this._description = description; }
    public void set_due_date(java.util.Date due_date) { this._due_date = due_date; }
    public void set_due_date(String due_date) {
        try { this._due_date = DBConnection.__date_format__.parse(due_date); } 
        catch (Exception e) { System.out.println("DATE PARSING ERROR: Entered date [" + due_date + "] is in the wrong format!"); }
    }
    public void set_due_date(@NotNull java.sql.Date due_date) { this._due_date = new java.util.Date(due_date.getTime()); }
    public void set_created_on(java.util.Date created_on) { this._created_on = created_on; }
    public void set_created_on(String created_on) {
        try { this._created_on = DBConnection.__date_format__.parse(created_on); } 
        catch (Exception e) { System.out.println("DATE PARSING ERROR: Entered date [" + created_on + "] is in the wrong format!"); }
    }
    public void set_created_on(@NotNull java.sql.Date creation_date) {
        this._created_on = new java.util.Date(creation_date.getTime());
    }
    public void set_creator_ID(int creator_ID) { 
        if (SystemController.current_user.role() != AppUser.UserType.MANAGER) System.out.println("INVALID CREATOR ID: Only managers have the priviledge to access ID's.");
        else this._creator_ID = creator_ID;
    }
    public void set_creator_username(String creator_username) { this._creator_username = creator_username; }
    public void set_status(String status) { this._status = status; }
    public void set_priority(short priority) {
        this._color = TaskPrototype.color(priority);
        this._priority = priority; 
    }
    public void set_assigned_to_member_ID(String assigned_to_member_id)
    { this._assigned_to_member_ID = assigned_to_member_id; }
    public void set_assigned_to_member_username(String assigned_to_member_username)
    { this._assigned_to_member_username = assigned_to_member_username; }
    public void set_team_ID(String team_ID) { this._team_ID = team_ID; }
    
    
    

    @Nullable
    public static Color color(short priority) {
        if (priority == 1) return new Color(255, 0, 102);
        if (priority == 2) return new Color(255, 170, 0);
        if (priority == 3) return new Color(255, 255, 0);
        if (priority == 4) return new Color(0, 0, 255);
        System.out.println("INVALID PRIORITY: Priority value range=[1, 4].");
        return null;
    }
    
    

}
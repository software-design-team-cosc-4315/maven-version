/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*** COMMENT OUT LINE 8 TO TEST YOUR CODE!!! ***/
package taskmanager;


import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;



/**
 *
 * @author Kevin Do
 */
public class TaskCategory {
    
    // Member data:
    private int _ID;
    private String _name;
    private String _description;
    private int _creator_ID;
    private String _creator_username;
    private java.util.Date _created_on;
    private String _team_ID;
    
    
    // Child data structures:
    private final TreeMap<String, Task> _task_map = new TreeMap<String, Task>();
    
    
    
    
    
    public TaskCategory() {}
    
    
    
    
    // Member functions:
    public void add_task(@NotNull Task task) {
        if (this._task_map.containsKey(task.name())) System.out.println("INVALID TASK INSERT: Task Name: {" + task.name() + "} is already in the task map for Category Name: {" + this._name + "}.");
        else this._task_map.put(task.name(), task);
    }
    
    public void remove_task(@NotNull Task task) {
        this._task_map.remove(task.name());
    }
    
    public void remove_task(String name) {
        this._task_map.remove(name);
    }
    
    public void remove_all_tasks() {
        this._task_map.clear();
    }
    
    public Task get_task(String name) {
        return this._task_map.get(name);
    }
    
    public Task[] tasks_in_category() {
        Task[] array = new Task[this._task_map.size()];
        int i = 0;
        for (Map.Entry<String, Task> entry : this._task_map.entrySet()) {
            array[i] = entry.getValue();
            ++i;
        }
        return array;
    }
    
    public Collection<Task> task_collection() {
        return this._task_map.values();
    }
    
    public boolean find_task(String name) {
        return this._task_map.containsKey(name);
    }
    
    
    
    // Accessors and Mutators:
    public int ID() { return this._ID; }
    public String name() { return this._name; }
    public String description() { return this._description; }
    public int creator_ID() { 
        if (SystemController.current_user.role() != AppUser.UserType.MANAGER) {
            System.out.println("INVALID CREATOR ID: Only managers have the priviledge to access ID's.");
            return -1;
        }
        return this._creator_ID; 
    }
    public String creator_username() { return this._creator_username; }
    public java.util.Date created_on() { return this._created_on; }
    public String str_created_on() { return DBConnection.__date_format__.format(this._created_on); }
    public String team_ID() { return this._team_ID; }
    
    
    public void set_ID(int ID) { this._ID = ID; }
    public void set_name(String name) { this._name = name; }
    public void set_description(String description) { this._description = description; }
    public void set_creator_ID(int ID) {
        if (SystemController.current_user.role() != AppUser.UserType.MANAGER) System.out.println("INVALID CREATOR ID: Only managers have the priviledge to access ID's.");
        else this._creator_ID = ID;
    }
    public void set_creator_username(String username) { this._creator_username = username; }
    public void set_created_on(String creation_date) { 
        try {
            this._created_on = DBConnection.__date_format__.parse(creation_date); 
        } catch (Exception e) {
            System.out.println("DATE PARSING ERROR: Entered date [" + creation_date + "] is in the wrong format!");
        }
    }
    public void set_created_on(@NotNull java.sql.Date creation_date) {
        this._created_on = new java.util.Date(creation_date.getTime());
    }
    //public void set_created_on(java.sql.Date creation_date) { this._created_on = new java.util.Date(creation_date.getTime()); }
    public void set_team_ID(String name) { this._team_ID = name; }
    
    
    
}

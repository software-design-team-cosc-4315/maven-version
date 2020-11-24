/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;


import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Kevin Do
 */
public class Task extends TaskPrototype {
    
    private static final String __NONRECURRENT__ = "non-recurrent";
    private static final String __WEEKLY__ = "weekly";
    private static final String __MONTHLY__ = "monthly";
    private static final int __NONRECUR__ = 0;
    private static final int __WEEK__ = 7;
    private static final int __MONTH__ = 30;
    
    protected int _recur_interval;
    
    // Subtasks data structure: reference by <name, subtask>
    private final TreeMap<String, Subtask> _subtasks = new TreeMap<String, Subtask>();
    
    
    
    
    
    public Task() {}
    
    
    public Subtask get_subtask(String name) {
        if (this._subtasks.containsKey(name)) return _subtasks.get(name);
        else return null; 
    }
    
    public Subtask[] get_subtasks() {
        Subtask[] array = new Subtask[this._subtasks.size()];

        int i = 0;
        for (Map.Entry<String, Subtask> entry : this._subtasks.entrySet())
        {
            array[i] = entry.getValue();
            ++i;
        }
        return array;
    }
    
    public Collection subtask_collection() {
        return this._subtasks.values();
    }
    
    public void add_subtask(Subtask subtask) {
        if (subtask.parent_task() == this) this._subtasks.put(subtask.name(), subtask);
        else System.out.println("You cannot add a subtask that already is part of another task.");  //Other task is the parent
    }
    
    public void remove_subtask(String name) {
        Subtask removed = this._subtasks.remove(name);
        System.out.println(removed);
    }
    
    
    
    
    // Mutators and accessors:
    public int recur_interval() { return this._recur_interval; }
    public String recurrence_type() { 
        switch (this._recur_interval) {
            case Task.__NONRECUR__: return Task.__NONRECURRENT__;
            case Task.__WEEK__: return Task.__WEEKLY__;
            case Task.__MONTH__: return Task.__MONTHLY__;
            default:
                System.out.println("ERROR: There is no such recurrent type with interval " + this._recur_interval + ".");
                return null;
        }
    }
    
    
    
    public void set_recur_interval(int interval) { 
        if (interval != Task.__NONRECUR__ && interval != Task.__WEEK__ && interval != Task.__MONTH__) {
            System.out.println("ERROR: There is no such recurrent type with interval " + interval + ".");
            this._recur_interval = -1;
        } else
            this._recur_interval = interval; 
    }
    
    public static int to_recur_interval(String recurrence_type) {
        if (recurrence_type.equals(Task.__NONRECURRENT__)) return Task.__NONRECUR__;
        if (recurrence_type.equals(Task.__WEEKLY__)) return Task.__WEEK__;
        if (recurrence_type.equals(Task.__MONTHLY__)) return Task.__MONTH__;
        System.out.println("ERROR: Recurrence type {" + recurrence_type + "} not recognised!");
        return -1;
    }
    
}
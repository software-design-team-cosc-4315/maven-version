/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;




/**
 *
 * @author Kevin Do
 */
public class Subtask extends TaskPrototype {
    
    // Member data:
    private Task _parent_task;
    

    public Subtask(Task parent) {
        super();
        this._parent_task = parent;
    }

    public int parent_task_ID() {
        return this._parent_task._ID;
    }
    
    public Task parent_task() {
        return this._parent_task;
    }
    
    public void set_parent_task(Task parent) {
        this._parent_task.remove_subtask(this._name);
        this._parent_task = parent;
    }
    
    
    
}
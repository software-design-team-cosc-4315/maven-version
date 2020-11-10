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
public class WorkLoadRecord {
    public float task_weights;
    public int task_count;
    public String status;
    
    
    public WorkLoadRecord() {
        this.task_weights = 0.0f;
        this.task_count = 0;
        this.status = null;
    }
    
    public WorkLoadRecord(float weights, int count, String status_type) {
        this.task_weights = weights;
        this.task_count = count;
        this.status = status_type;
    }
}

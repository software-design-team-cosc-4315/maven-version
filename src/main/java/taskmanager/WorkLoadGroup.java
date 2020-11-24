/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;


import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Ganondorfjallida
 */
public class WorkLoadGroup {
    
    public static final short COMPLETED = 0;
    public static final short IN_PROGRESS = 1;
    public static final short NOT_STARTED = 2;
    
    
    private int member_count = 1;
    
    private final WorkLoadRecord[] workload_records = new WorkLoadRecord[]
    { new WorkLoadRecord(), new WorkLoadRecord(), new WorkLoadRecord() };
   
    
    public WorkLoadGroup() {
        this.workload_records[0].status = "Completed";
        this.workload_records[1].status = "In Progress";
        this.workload_records[2].status = "Not Started";
    }
    
    public void set_member_count(int count) { this.member_count = count; }
    
    public void set_record(float task_weights, int task_count, @NotNull String status) {
        if (status.equals("Completed")) {
            this.workload_records[0].task_weights = task_weights;
            this.workload_records[0].task_count = task_count;
        } else if (status.equals("In Progress")) {
            this.workload_records[1].task_weights = task_weights;
            this.workload_records[1].task_count = task_count;
        } else if (status.equals("Not Started")) {
            this.workload_records[2].task_weights = task_weights;
            this.workload_records[2].task_count = task_count;
        } else 
            System.out.println("ERROR: Record status must be one of the three: Completed, In Progress, Not Started.");
    }
    
    public int get_member_count() {
        return this.member_count;
    }
    
    public float get_total_workload() {
        return this.workload_records[0].task_weights 
             + this.workload_records[1].task_weights 
             + this.workload_records[2].task_weights;
    }
    
    public float get_workload(short status_index) {
        return this.workload_records[status_index].task_weights;
    }
    
    public float get_workload_portion(short status_index) {
        return this.workload_records[status_index].task_weights / this.get_total_workload();
    }
    
    public float compute_productivity() {
        float productivity = this.get_total_workload() * (
                                this.get_workload_portion(WorkLoadGroup.COMPLETED) + 
                                this.get_workload_portion(WorkLoadGroup.IN_PROGRESS) * 0.5f
                             );
        if (1 < this.member_count) 
            productivity /= (float)(this.member_count * Math.log(this.member_count));
        return productivity;
    }
    
    
}

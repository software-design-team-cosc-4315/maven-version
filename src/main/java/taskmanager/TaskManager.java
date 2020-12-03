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
public class TaskManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /* Create and display the form: */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {        
                SystemController.create_pages();
                SystemController.log_out();
            }
        });
        
    }
    
    
    
    
    
    
    
}

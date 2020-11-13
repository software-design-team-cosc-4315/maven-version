/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;



import javax.swing.*;

/**
 *
 * @author Ganondorfjallida
 */
public class TaskPanel extends TaskPrototypePanel {
    
    // Data source:
    private Task _data_source;
    private TaskPage _parent_page;
    
    // UI Components:
    protected JLabel _recurrence_label;
    
    private JPanel _subtasks_panel;
    private JLabel _subtasks_label;
    private JPanel _subtasks_container;
    
    
    
    
    
    
    
    public TaskPanel(Task data_source, TaskPage parent_page) {
        super();
        this._data_source = data_source;
        this._parent_page = parent_page;
        
        this.initComponents();
        
        this.refresh();
    }
    
    @Override
    public void initComponents() {
        
        final TaskPanel self = this;
        this._recurrence_label = new JLabel();
        this._subtasks_panel = new JPanel();
        this._subtasks_label = new JLabel();
        this._subtasks_container = new JPanel();
        
        super.initComponents();
        
        this._recurrence_label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        this._recurrence_label.setText("  Recurrence: loading...");
        this._due_date_panel.add(this._recurrence_label, java.awt.BorderLayout.CENTER);
        

        // Subtasks Panel:
        this._subtasks_panel = new JPanel();
        this._subtasks_panel.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        BoxLayout subtasks_panel_layout = new BoxLayout(this._subtasks_panel, BoxLayout.LINE_AXIS);
        this._subtasks_panel.setLayout(subtasks_panel_layout);

        // 1. subtasks label
        JPanel subtasks_label_panel = new JPanel( new java.awt.BorderLayout() );
        this._subtasks_label.setText("Subtasks:");
        subtasks_label_panel.add(this._subtasks_label, java.awt.BorderLayout.PAGE_START);
        subtasks_label_panel.setMaximumSize(new java.awt.Dimension(subtasks_label_panel.getPreferredSize().width, Short.MAX_VALUE));
        
        // 2. subtasks container
        this._subtasks_container.setLayout(new BoxLayout(this._subtasks_container, BoxLayout.PAGE_AXIS));
        
        
        this._subtasks_panel.add(subtasks_label_panel);
        this._subtasks_panel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        this._subtasks_panel.add(this._subtasks_container);
        
        this._subtasks_panel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, this._subtasks_container.getPreferredSize().height));
        
        
        // Set up button response:
        this._delete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                self._parent_page.reload_task_delete_pop_up_panel(self._data_source.name());
                self._parent_page.show_delete_pop_up_window();
            }
        });

        this._edit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                self.edit_task_button_action();
            }
        });
        
        
        
        // Add Task Components into the Task Panel
        // Add information panel into the container:
        this.add(this._subtasks_panel);
        
        this.add(this.__UI_gaps__[5] = Box.createRigidArea(new java.awt.Dimension(0, 5)));
        this.__UI_gaps__[5].setVisible(false);
        
    }
    
    /*
        Function to navigate to the team leader's page to edit the task when the edit-task-button is clicked (and released):
    */
    public void edit_task_button_action() {
        // Validate user data: only the team leader and managers can go to the team leader's page:
        if (!SystemController.validate_user_for_team_leaders_page()) return;
        SystemController.to_team_leaders_page(TeamLeadersPage.Focus.TASK, this._data_source.name()); // transition to team leader's page
    }
    
    
    
    @Override
    public void title_label_clicked(java.awt.event.MouseEvent evt) {
        if (this.__expanded__) {
            this.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
            this._description_panel.setVisible(false);
            this._assignment_panel.setVisible(false);
            this._due_date_panel.setVisible(false);
            this._creation_info_panel.setVisible(false);
            this._status_panel.setVisible(false);
            this._subtasks_panel.setVisible(false);
            for (short i=0; i < 5; ++i) this.__UI_gaps__[i].setVisible(false);
            this.__UI_gaps__[5].setVisible(true);
            this.__expanded__ = false;
        } else {
            this.setBorder(BorderFactory.createTitledBorder("Task: " + this._data_source.name()));
            this._description_panel.setVisible(true);
            this._assignment_panel.setVisible(true);
            this._due_date_panel.setVisible(true);
            this._creation_info_panel.setVisible(true);
            this._status_panel.setVisible(true);
            this._subtasks_panel.setVisible(true);
            for (short i=0; i < 5; ++i) this.__UI_gaps__[i].setVisible(true);
            this.__UI_gaps__[5].setVisible(false);
            this.__expanded__ = true;
        }
    }
    
    
    public void refresh() {
        this.refresh_labels();
        this.refresh_subtasks();
    }
    
    public void refresh_labels() {
        this.setBorder(BorderFactory.createTitledBorder("Task: " + this._data_source.name()));
        this._colour_panel.setBackground(this._data_source.color());
        this._title_label.setText(this._data_source.name());
        this._description_label.setText(this._data_source.description());
        this._assignment_label.setText("Assigned to: " + this._data_source.assigned_to_member_username());
        this._due_date_label.setText("Due date: " + this._data_source.str_due_date());
        this._recurrence_label.setText("  " + this._data_source.recurrence_type() + " task");
        this._creator_label.setText("Created by: " + this._data_source.creator_username());
        this._creation_date_label.setText("on: " + this._data_source.str_created_on());
        this._status_label.setText("Status: " + this._data_source.status());
        
        if (SystemController.current_user.role() == AppUser.UserType.BASE_USER) { this._edit_button.setEnabled(false); this._delete_button.setEnabled(false); }
        else if (SystemController.current_user.role() == AppUser.UserType.TEAM_LEAD) this._delete_button.setEnabled(false);
    }
    
    public void refresh_subtasks() {
        this._subtasks_container.removeAll();
        
        Subtask[] subtasks = this._data_source.get_subtasks();
        for (Subtask subtask: subtasks) this._subtasks_container.add(new SubtaskPanel(subtask, this._parent_page));
        
        int preferred_height = this._subtasks_container.getPreferredSize().height;
        this._subtasks_panel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, preferred_height < 20? 20 : preferred_height));
        
        this.revalidate();
        this.repaint();
    }
    
}

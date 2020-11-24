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
public class SubtaskPanel extends TaskPrototypePanel {
    
    // Data source:
    private final Subtask _data_source;
    private final TaskPage _parent_page;
    
    
    
    public SubtaskPanel(Subtask data_source, TaskPage parent_page) {
        super();
        this._data_source = data_source;
        this._parent_page = parent_page;
        
        this.initComponents();
        this.refresh();
    }
    
    
    @Override
    public void initComponents() {
        
        super.initComponents();
        final SubtaskPanel self = this;
        
        // Set up button response:
        this._delete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                self._parent_page.reload_subtask_delete_pop_up_panel(self._data_source.name());
                self._parent_page.show_delete_pop_up_window();
            }
        });

        this._edit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                self.edit_subtask_button_action();
            }
        });
        
    }
    
    /*
        Function to navigate to the team leader's page to edit the task when the edit-subtask-button is clicked (and released):
    */
    public void edit_subtask_button_action() {
        // Validate user data: only the team leader and managers can go to the team leader's page:
        if (!SystemController.validate_user_for_team_leaders_page()) return;
        SystemController.to_team_leaders_page(TeamLeadersPage.Focus.SUBTASK, this._data_source.name()); // transition to team leader's page
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
            for (short i=0; i < 5; ++i) this.__UI_gaps__[i].setVisible(false);
            this.__expanded__ = false;
        } else {
            this.setBorder(BorderFactory.createTitledBorder("SubTask: " + this._data_source.name()));
            this._description_panel.setVisible(true);
            this._assignment_panel.setVisible(true);
            this._due_date_panel.setVisible(true);
            this._creation_info_panel.setVisible(true);
            this._status_panel.setVisible(true);
            for (short i=0; i < 5; ++i) this.__UI_gaps__[i].setVisible(true);
            this.__expanded__ = true;
        }
    }
    
    
    @Override
    public void refresh() {
        this.setBorder(BorderFactory.createTitledBorder("SubTask: " + this._data_source.name()));
        this._colour_panel.setBackground(this._data_source.color());
        this._title_label.setText(this._data_source.name());
        this._description_label.setText(this._data_source.description());
        this._assignment_label.setText("Assigned to: " + this._data_source.assigned_to_member_username());
        this._due_date_label.setText("Due date: " + this._data_source.str_due_date());
        this._creator_label.setText("Created by: " + this._data_source.creator_username());
        this._creation_date_label.setText("on: " + this._data_source.str_created_on());
        this._status_label.setText("Status: " + this._data_source.status());
        
        if (SystemController.current_user.role() == AppUser.UserType.BASE_USER) { this._edit_button.setEnabled(false); this._delete_button.setEnabled(false); }
        else if (SystemController.current_user.role() == AppUser.UserType.TEAM_LEAD) this._delete_button.setEnabled(false);
    }
    
}

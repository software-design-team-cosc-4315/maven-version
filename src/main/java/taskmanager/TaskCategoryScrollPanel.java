/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;



import javax.swing.*;
import java.util.*;
import java.sql.*;


/**
 *
 * @author Ganondorfjallida, Kevin Do
 */
public class TaskCategoryScrollPanel extends JScrollPane {
    
    // Member data:
    private final static short __inner_panel_width__ = 870;
    
    
    // Reference to other classes:
    private TaskCategory _data_source;
    private TaskPage _parent_page;
    
    
    // UI components:
    private JPanel _container;
    private JPanel _tasks_panel;
    private JPanel _info_panel;
    private JPanel _description_panel;
    private JLabel _description_label;
    private JPanel _creation_info_panel;
    private JLabel _creator_label;
    private JLabel _creation_date_label;
    private JPanel _alter_panel;
    private JButton _edit_button;
    private JButton _delete_button;
    
    
    
    public TaskCategoryScrollPanel(TaskCategory data_source, TaskPage parent_page) {
        super();
        this._data_source = data_source;
        this._parent_page = parent_page;
        
        this.init_components();
    }
    
    private void init_components() {
        this._container = new JPanel();
        this._tasks_panel = new JPanel();
        this._info_panel = new JPanel();
        this._description_panel = new JPanel();
        this._description_label = new JLabel();
        this._creation_info_panel = new JPanel();
        this._creator_label = new JLabel();
        this._creation_date_label = new JLabel();
        this._alter_panel = new JPanel();
        this._edit_button = new JButton();
        this._delete_button = new JButton();
        
        final TaskCategoryScrollPanel self = this;
        
        
        // Disable horizontal scrolling:
        this.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Category Information Panel:
        this._info_panel.setBorder(BorderFactory.createTitledBorder("Task Category Description"));
        
        // 1. description
        this._description_label.setText(this._data_source.description());
        
        GroupLayout description_panel_layout = new GroupLayout(this._description_panel);
        this._description_panel.setLayout(description_panel_layout);
        description_panel_layout.setHorizontalGroup(
            description_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(description_panel_layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this._description_label)
                .addContainerGap())
        );
        description_panel_layout.setVerticalGroup(
            description_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(description_panel_layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this._description_label, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        
        // 2. creation information
        this._creator_label.setText("Created by: " + this._data_source.creator_username());  
        
        this._creation_date_label.setText("on: " + this._data_source.str_created_on()); 
 
        GroupLayout creation_info_panel_layout = new GroupLayout(this._creation_info_panel);
        this._creation_info_panel.setLayout(creation_info_panel_layout);
        creation_info_panel_layout.setHorizontalGroup(
            creation_info_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(creation_info_panel_layout.createSequentialGroup()
                .addComponent(this._creator_label, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._creation_date_label, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        creation_info_panel_layout.setVerticalGroup(
            creation_info_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(creation_info_panel_layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(this._creator_label)
                .addComponent(this._creation_date_label))
        );

        // 3. alteration
        this._delete_button.setIcon(new ImageIcon(getClass().getResource("/taskmanager/assets/images/delete-bin-2-fill.png"))); // NOI18N
        this._delete_button.setToolTipText("delete cateogry");
        this._delete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                self._parent_page.reload_task_category_delete_pop_up_panel(self._data_source.name());
                self._parent_page.show_delete_pop_up_window();
            }
        });

        this._edit_button.setIcon(new ImageIcon(getClass().getResource("/taskmanager/assets/images/edit-2-fill.png"))); // NOI18N
        this._edit_button.setToolTipText("modify category");
        this._edit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                self.edit_category_button_action();
            }
        });

        GroupLayout alter_panel_layout = new GroupLayout(this._alter_panel);
        this._alter_panel.setLayout(alter_panel_layout);
        alter_panel_layout.setHorizontalGroup(
            alter_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, alter_panel_layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(this._edit_button, 48, 48, 48)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._delete_button, 48, 48, 48))
        );
        alter_panel_layout.setVerticalGroup(
            alter_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this._delete_button, 48, 48, 48)
            .addComponent(this._edit_button, 48, 48, 48)
        );
        
        // 4. add components into the information panel
        GroupLayout info_panel_layout = new GroupLayout(this._info_panel);
        this._info_panel.setLayout(info_panel_layout);
        info_panel_layout.setHorizontalGroup(
            info_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(info_panel_layout.createSequentialGroup()
                .addGroup(info_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(info_panel_layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(info_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(this._creation_info_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(info_panel_layout.createSequentialGroup()
                                .addComponent(this._alter_panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(this._description_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        info_panel_layout.setVerticalGroup(
            info_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(info_panel_layout.createSequentialGroup()
                .addComponent(this._description_panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._creation_info_panel, javax.swing.GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._alter_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        
        
        this._info_panel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, this._info_panel.getPreferredSize().height));
  
        
        // Add information panel into the container:
        this._tasks_panel.setMaximumSize(new java.awt.Dimension(TaskCategoryScrollPanel.__inner_panel_width__, Short.MAX_VALUE));
        this._tasks_panel.setLayout(new BoxLayout(this._tasks_panel, BoxLayout.PAGE_AXIS));
        this._tasks_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        this._tasks_panel.add(this._info_panel);
        this._tasks_panel.add(Box.createRigidArea(new java.awt.Dimension(0, 24)));
        
        
        GroupLayout container_layout = new GroupLayout(this._container);
        this._container.setLayout(container_layout);
        container_layout.setHorizontalGroup(
                container_layout.createSequentialGroup()
                .addComponent(this._tasks_panel)
        );
        container_layout.setVerticalGroup(
                container_layout.createSequentialGroup()
                .addComponent(this._tasks_panel)
        );
        
        
        this.setViewportView(this._container); // add container into the scroll panel
        
    }
    
    /*
        Function to navigate to the team leader's page to edit the task category when the edit-category-button is clicked (and released):
    */
    public void edit_category_button_action() {
        // Validate user data: only the team leader and managers can go to the team leader's page:
        if (!SystemController.validate_user_for_team_leaders_page()) return;
        SystemController.to_team_leaders_page(TeamLeadersPage.Focus.TASK_CATEGORY, this._data_source.name()); // transition to team leader's page
    }
    
    
    
    public void reload() {
        
        DBConnection.connect();
        
        List<Task> task_list = new ArrayList<Task>();
        // Leaders and Managers see all tasks in the current team:
        if (SystemController.current_user.role() == AppUser.UserType.TEAM_LEAD || SystemController.current_user.role() == AppUser.UserType.MANAGER) {
            
            // Query tasks in the current task category:
            List<Integer> task_IDs = new ArrayList<>();
            PreparedStatement ps = DBConnection.prepared_statement(
                    "SELECT TASK_ID, TASK_CATEGORY_ID"
                    + " FROM TASKINCATEGORIES"
                    + " WHERE TASK_CATEGORY_ID = ?"
            );
            if (ps == null || !DBConnection.set_statement_value(ps, 1, this._data_source.ID())) { 
                System.out.println("ERROR: Task ID query statement failed.");
                DBConnection.disconnect(); return; 
            }  // do not proceed if there is an error
            
            ResultSet rs = DBConnection.execute_query(ps);
            try {
                while (rs.next()) { task_IDs.add(rs.getInt("TASK_ID")); }
            } catch (SQLException e) {
                System.out.println(e);
                DBConnection.disconnect(); return;      // do not proceed if there is an error
            }
            
            
            // Query tasks data by the ID's:
            ps = DBConnection.prepared_statement(
                    "SELECT T.NAME, T.TASK_DESCRIPTION, T.DUE_DATE, T.CREATED_ON, CREATOR.USERNAME AS CREATOR_USERNAME, U.USERNAME AS ASSIGNED_USERNAME, T.STATUS, T.TASK_PRIORITY"
                    + " FROM TASKS T, MEMBERS CREATOR, MEMBERS U"
                    + " WHERE T.TASK_ID = ? AND T.CREATED_BY_MEMBER_ID = CREATOR.MEMBER_ID AND T.ASSIGNED_TO_MEMBER_ID = U.MEMBER_ID" 
                    + " AND T.DELETED != 'Y' AND U.DELETED != 'Y'"
            );

            for (int task_ID: task_IDs) {
                
                if (!DBConnection.set_statement_value(ps, 1, task_ID)) { 
                    System.out.println("ERROR: Task query statement failed.");
                    DBConnection.disconnect(); return; 
                }     // do not proceed if there is an error
                rs = DBConnection.execute_query(ps);
                try {
                    rs.next();
                    // Add task to task list:
                    Task task = new Task();
                    task.set_ID(task_ID);
                    task.set_priority(rs.getShort("TASK_PRIORITY"));
                    task.set_name(rs.getString("NAME"));
                    task.set_description(rs.getString("TASK_DESCRIPTION"));
                    task.set_assigned_to_member_username(rs.getString("ASSIGNED_USERNAME"));
                    task.set_due_date(rs.getString("DUE_DATE"));
                    task.set_creator_username(rs.getString("CREATOR_USERNAME"));
                    task.set_created_on(rs.getString("CREATED_ON"));
                    task.set_status(rs.getString("STATUS"));

                    task_list.add(task);
                    
                } catch (SQLException e) {
                    System.out.println(e);
                    DBConnection.disconnect(); return;      // do not proceed if there is an error
                }
                
            }
            
            
            // Query subtasks for each task:
            for (Task task: task_list) {
                // Query tasks in the current team:
                ps = DBConnection.prepared_statement(
                        "SELECT S.SUBTASK_ID, S.NAME, S.DESCRIPTION, S.DUE_DATE, S.CREATED_ON, CREATOR.USERNAME AS CREATOR_USERNAME, U.USERNAME AS ASSIGNED_USERNAME, S.STATUS, S.PRIORITY, S.SUBTASK_TO"
                        + " FROM SUBTASK S, MEMBERS CREATOR, MEMBERS U"
                        + " WHERE S.SUBTASK_TO = ? AND S.CREATED_BY_MEMBER_ID = CREATOR.MEMBER_ID AND S.ASSIGNED_TO_MEMBER_ID = U.MEMBER_ID" 
                        + " AND S.DELETED != 'Y' AND U.DELETED != 'Y'"
                );
                if (ps == null || !DBConnection.set_statement_value(ps, 1, task.ID())) { 
                    System.out.println("ERROR: Subtask query statement failed.");
                    DBConnection.disconnect(); return; 
                }     // do not proceed if there is an error

                rs = DBConnection.execute_query(ps);
                try {
                    while (rs.next()) {
                        // Add subtask as child to its parent task:
                        Subtask subtask = new Subtask(task);
                        subtask.set_ID(rs.getInt("SUBTASK_ID"));
                        subtask.set_priority(rs.getShort("PRIORITY"));
                        subtask.set_name(rs.getString("NAME"));
                        subtask.set_description(rs.getString("DESCRIPTION"));
                        subtask.set_assigned_to_member_username(rs.getString("ASSIGNED_USERNAME"));
                        subtask.set_due_date(rs.getString("DUE_DATE"));
                        subtask.set_creator_username(rs.getString("CREATOR_USERNAME"));
                        subtask.set_created_on(rs.getString("CREATED_ON"));
                        subtask.set_status(rs.getString("STATUS"));

                        task.add_subtask(subtask);
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                    DBConnection.disconnect(); return;      // do not proceed if there is an error
                }
            }
            
            
        // Base users only see their own subtasks:
        } else if (SystemController.current_user.role() == AppUser.UserType.BASE_USER) {
            
            // Query tasks in which the current user is assigned a subtask:
            PreparedStatement ps = DBConnection.prepared_statement(
                    "SELECT DISTINCT T.TASK_ID, T.NAME, T.TASK_DESCRIPTION, T.DUE_DATE, T.CREATED_ON, CREATOR.USERNAME AS CREATOR_USERNAME, T.STATUS, T.TASK_PRIORITY"
                    + " FROM TASKS T, MEMBERS CREATOR, SUBTASK S, TASKINCATEGORIES TIC"
                    + " WHERE T.CREATED_BY_MEMBER_ID = CREATOR.MEMBER_ID AND S.SUBTASK_TO = T.TASK_ID AND S.ASSIGNED_TO_MEMBER_ID = ? AND TIC.TASK_CATEGORY_ID = ? AND TIC.TASK_ID = T.TASK_ID" 
                    + " AND T.DELETED != 'Y' AND S.DELETED != 'Y'"
            );
            DBConnection.set_statement_value(ps, 1, SystemController.current_user.ID());
            DBConnection.set_statement_value(ps, 2, this._data_source.ID());
            
            ResultSet rs = DBConnection.execute_query(ps);
            try {
                while (rs.next()) {
                    // Add task to the task list:
                    Task task = new Task();
                    task.set_ID(rs.getInt("TASK_ID"));
                    task.set_priority(rs.getShort("TASK_PRIORITY"));
                    task.set_name(rs.getString("NAME"));
                    task.set_description(rs.getString("TASK_DESCRIPTION"));
                    task.set_assigned_to_member_username(SystemController.current_user.username());
                    task.set_due_date(rs.getString("DUE_DATE"));
                    task.set_creator_username(rs.getString("CREATOR_USERNAME"));
                    task.set_created_on(rs.getString("CREATED_ON"));
                    task.set_status(rs.getString("STATUS"));
                    
                    task_list.add(task);
                }
            } catch (SQLException e) {
                System.out.println(e);
                DBConnection.disconnect(); return;      // do not proceed if there is an error
            }
            
            
            // Query subtasks for each task:
            for (Task task: task_list) {
                // Query tasks in the current team:
                ps = DBConnection.prepared_statement(
                        "SELECT S.SUBTASK_ID, S.NAME, S.DESCRIPTION, S.DUE_DATE, S.CREATED_ON, CREATOR.USERNAME AS CREATOR_USERNAME, S.STATUS, S.PRIORITY, S.SUBTASK_TO"
                        + " FROM SUBTASK S, MEMBERS CREATOR"
                        + " WHERE S.SUBTASK_TO = ? AND S.CREATED_BY_MEMBER_ID = CREATOR.MEMBER_ID AND S.ASSIGNED_TO_MEMBER_ID = ?" 
                        + " AND S.DELETED != 'Y'"
                );
                boolean healthy_statement = (ps != null);
                healthy_statement = healthy_statement? DBConnection.set_statement_value(ps, 1, task.ID()) : false;
                healthy_statement = healthy_statement? DBConnection.set_statement_value(ps, 2, SystemController.current_user.ID()) : false;
                if (!healthy_statement) { DBConnection.disconnect(); return; }     // do not proceed if there is an error
                
                rs = DBConnection.execute_query(ps);
                try {
                    while (rs.next()) {
                        // Add subtask as child to its parent task:
                        Subtask subtask = new Subtask(task);
                        subtask.set_ID(rs.getInt("SUBTASK_ID"));
                        subtask.set_priority(rs.getShort("PRIORITY"));
                        subtask.set_name(rs.getString("NAME"));
                        subtask.set_description(rs.getString("DESCRIPTION"));
                        subtask.set_assigned_to_member_username(SystemController.current_user.username());
                        subtask.set_due_date(rs.getString("DUE_DATE"));
                        subtask.set_creator_username(rs.getString("CREATOR_USERNAME"));
                        subtask.set_created_on(rs.getString("CREATED_ON"));
                        subtask.set_status(rs.getString("STATUS"));

                        task.add_subtask(subtask);
                    }
                } catch (SQLException e) {
                    System.out.println(e);
                    DBConnection.disconnect(); return;      // do not proceed if there is an error
                }
            }
            
            
        }
        
        
        DBConnection.disconnect();
        
        // Refactor data source (update local task category content):
        this._data_source.remove_all_tasks();
        for (Task task : task_list) this._data_source.add_task(task);
        
        // Refresh UI:
        this.refresh();
        
    }
    
    
    public void refresh() {
        this.refresh_labels();
        this.refresh_tasks();
    }
    
    public void refresh_labels() {
        this._description_label.setText(this._data_source.description());
        this._creator_label.setText("Created by: " + this._data_source.creator_username());
        this._creation_date_label.setText("on: " + this._data_source.str_created_on());
        
        if (SystemController.current_user.role() == AppUser.UserType.BASE_USER) { this._edit_button.setEnabled(false); this._delete_button.setEnabled(false); }
        else if (SystemController.current_user.role() == AppUser.UserType.TEAM_LEAD) this._delete_button.setEnabled(false);
    }
    
    public void refresh_tasks() {
        this._tasks_panel.removeAll();             // clear old local task panels
        
        this._tasks_panel.add(this._info_panel);   // add information panel back
        this._tasks_panel.add(Box.createRigidArea(new java.awt.Dimension(0, 24)));
        
        for (Task task: this._data_source.tasks_in_category()) {
            TaskPanel task_panel = new TaskPanel(task, this._parent_page);
            task_panel.setVisible(true);
            this._tasks_panel.add(task_panel);
        }
        
        this._tasks_panel.revalidate();
        this._tasks_panel.repaint();
    }
    
    
    
    
    
    public TaskCategory data_source() { return this._data_source; };
    
}

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
public abstract class TaskPrototypePanel extends JPanel {
    
    // Member data:
    protected boolean __expanded__ = true;
    protected short __gaps_size__ = 6;
    
    // UI Components:
    protected JPanel _title_panel;
    protected JPanel _colour_panel;
    protected JLabel _title_label;
    protected JPanel _alter_panel;
    protected JButton _edit_button;
    protected JButton _delete_button;
            
    protected JPanel _description_panel;
    protected JLabel _description_label;
    protected JPanel _assignment_panel;
    protected JLabel _assignment_label;
    protected JPanel _due_date_panel;
    protected JLabel _due_date_label;
    
    protected JPanel _creation_info_panel;
    protected JLabel _creator_label;
    protected JLabel _creation_date_label;
    
    protected JPanel _status_panel;
    protected JLabel _status_label;
    
    
    protected java.awt.Component[] __UI_gaps__ = new java.awt.Component[6];
    
    
    
    
    public void initComponents() {
        
        final TaskPrototypePanel self = this;
        
        this._title_panel = new JPanel();
        this._colour_panel = new JPanel();
        this._title_label = new JLabel();
        this._alter_panel = new JPanel();
        this._edit_button = new JButton();
        this._delete_button = new JButton();
            
        this._description_panel = new JPanel();
        this._description_label = new JLabel();
        this._assignment_panel = new JPanel();
        this._assignment_label = new JLabel();
        this._due_date_panel = new JPanel();
        this._due_date_label = new JLabel();
    
        this._creation_info_panel = new JPanel();
        this._creator_label = new JLabel();
        this._creation_date_label = new JLabel();
    
        this._status_panel = new JPanel();
        this._status_label = new JLabel();
        
        
        
        // Set the border of the task panel:
        this.setBorder(BorderFactory.createTitledBorder("Task: loading..."));   // TODO: get info from data source
        
        
        // Title Panel:
        // 1. colour
        this._colour_panel.setBackground(new java.awt.Color(204, 0, 204));      // TODO: get info from data source

        GroupLayout colour_panel_layout = new GroupLayout(this._colour_panel);
        this._colour_panel.setLayout(colour_panel_layout);
        colour_panel_layout.setHorizontalGroup(
            colour_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );
        colour_panel_layout.setVerticalGroup(
            colour_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        // 2. task name
        this._title_label.setText("loading...");    // TODO: get info from data source
        this._title_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                self.title_label_clicked(evt);
            }
        });
        
        
        // 3. alteration buttons
        this._delete_button.setIcon(new ImageIcon(getClass().getResource("/taskmanager/assets/images/delete-bin-2-fill.png"))); // NOI18N
        this._delete_button.setToolTipText("delete task");
        this._delete_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        this._edit_button.setIcon(new ImageIcon(getClass().getResource("/taskmanager/assets/images/edit-2-fill.png"))); // NOI18N
        this._edit_button.setToolTipText("modify task");
        this._edit_button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        // 4. add title components into the title panel
        GroupLayout title_panel_layout = new GroupLayout(this._title_panel);
        this._title_panel.setLayout(title_panel_layout);
        title_panel_layout.setHorizontalGroup(
            title_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, title_panel_layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(this._alter_panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(title_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(title_panel_layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(this._colour_panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(this._title_label)
                    .addContainerGap(568, Short.MAX_VALUE)))
        );
        title_panel_layout.setVerticalGroup(
            title_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this._alter_panel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(title_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(title_panel_layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(title_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(this._colour_panel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(this._title_label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );

        
        // Description Label:
        this._description_panel = new JPanel( new java.awt.BorderLayout() );
        this._description_panel.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        this._description_label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        this._description_label.setText("<html>loading...</html>");             // TODO: get info from the data source
        this._description_panel.add(this._description_label);
        
        
        // Assignment Label:
        this._assignment_panel = new JPanel( new java.awt.BorderLayout() );
        this._assignment_panel.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        this._assignment_label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        this._assignment_label.setText("Assigned to: loading...");              // TODO: get info from the data source
        this._assignment_panel.add(this._assignment_label);
        
        
        // Due Date Label:
        this._due_date_panel = new JPanel( new java.awt.BorderLayout() );
        this._due_date_panel.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        this._due_date_label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        this._due_date_label.setText("Due date: loading...");                   // TODO: get info from the data source
        this._due_date_panel.add(this._due_date_label, java.awt.BorderLayout.WEST);
        
        
        
        
        // Creation Information Panel:
        // 1. creator 
        this._creator_label.setText("Created by: loading...");                  // TODO: get info from the data source
        
        // 2. creation date 
        this._creation_date_label.setText("on: loading...");                    // TODO: get info from the data source
        
        // 3. add creation information components into creation information panel
        GroupLayout creation_info_panel_layout = new GroupLayout(this._creation_info_panel);
        this._creation_info_panel.setLayout(creation_info_panel_layout);
        creation_info_panel_layout.setHorizontalGroup(
            creation_info_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(creation_info_panel_layout.createSequentialGroup()
                .addComponent(this._creator_label)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._creation_date_label)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        creation_info_panel_layout.setVerticalGroup(
            creation_info_panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(creation_info_panel_layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(this._creator_label)
                .addComponent(this._creation_date_label))
        );
        this._creation_info_panel.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));

        
        
        // Status Label:
        this._status_panel = new JPanel( new java.awt.BorderLayout() );
        this._status_panel.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 0));
        this._status_label.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        this._status_label.setText("Status: loading...");                       // TODO: get info from the data source
        this._status_panel.add(this._status_label);

        
        
        this._title_panel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, this._title_panel.getPreferredSize().height));
        this._description_panel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, this._description_panel.getPreferredSize().height));
        this._assignment_panel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, this._assignment_panel.getPreferredSize().height));
        this._due_date_panel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, this._due_date_panel.getPreferredSize().height));
        this._creation_info_panel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, this._creation_info_panel.getPreferredSize().height));
        this._status_panel.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, this._status_panel.getPreferredSize().height));
        
        
        
        
        // Add Task Components into the Task Panel
        // Add information panel into the container:
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(this._title_panel);
        this.add(this._description_panel);
        this.add(this.__UI_gaps__[0] = Box.createRigidArea(new java.awt.Dimension(0, 18)));
        this.add(this._assignment_panel);
        this.add(this.__UI_gaps__[1] = Box.createRigidArea(new java.awt.Dimension(0, 2)));
        this.add(this._due_date_panel);
        this.add(this.__UI_gaps__[2] = Box.createRigidArea(new java.awt.Dimension(0, 2)));
        this.add(this._creation_info_panel);
        this.add(this.__UI_gaps__[3] = Box.createRigidArea(new java.awt.Dimension(0, 2)));
        this.add(this._status_panel);
        this.add(this.__UI_gaps__[4] = Box.createRigidArea(new java.awt.Dimension(0, 2)));
        
    }
    
    
    
    public abstract void title_label_clicked(java.awt.event.MouseEvent evt);
    
    
    public abstract void refresh();
    
}

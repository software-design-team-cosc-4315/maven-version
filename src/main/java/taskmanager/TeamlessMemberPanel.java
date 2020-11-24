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
public class TeamlessMemberPanel extends JPanel {
    
    // Data source:
    private final AppUser _data_source;
    
    
    // UI components:
    private JLabel _username_label;
    private JSeparator _username_separator; 
    private JCheckBox _add_checkbox;
    private JSeparator _add_separator;
    private JRadioButton _leader_button;
    private JSeparator _leader_separator;
    private JLabel _role_label;
    
    
            
    public TeamlessMemberPanel(AppUser data_source) {
        super();
        this._data_source = data_source;
        
        this.initComponents();
    }
    
    
    public void initComponents() {
        
        this._username_label = new JLabel();
        this._username_separator = new JSeparator();
        this._add_checkbox = new JCheckBox();
        this._add_separator = new JSeparator();
        this._leader_button = new JRadioButton();
        this._leader_separator = new JSeparator();
        this._role_label = new JLabel();
        
        
        // Panel size and border:
        this.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        this.setPreferredSize(new java.awt.Dimension(565, 24));
        this.setMaximumSize(this.getPreferredSize());

        // 1. username 
        this._username_label.setText(this._data_source.username());        
        this._username_separator.setOrientation(SwingConstants.VERTICAL);
        
        // 2. add-to-team feature
        this._add_checkbox.setText("add");
        this._add_separator.setOrientation(SwingConstants.VERTICAL);

        // 3. make leader button
        this._leader_button.setText("appoint");
        this._leader_button.setSize(new java.awt.Dimension(106, 23));
        this._leader_separator.setOrientation(SwingConstants.VERTICAL);

        // 4. role
        this._role_label.setText(AppUser.user_type_to_string(this._data_source.role()));
        
        // 5. add components to the panel
        GroupLayout panel_layout = new GroupLayout(this);
        this.setLayout(panel_layout);
        panel_layout.setHorizontalGroup(
            panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(panel_layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(this._username_label, 159, 159, 159)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._username_separator, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._add_checkbox, 134, 134, 134)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._add_separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._leader_button, 106, 106, 106)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._leader_separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this._role_label, 78, 78, 78)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            )
        );
        panel_layout.setVerticalGroup(
            panel_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(this._username_label, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
            .addComponent(this._username_separator)
            .addComponent(this._add_checkbox, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(this._add_separator, GroupLayout.Alignment.TRAILING)
            .addComponent(this._leader_button, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(this._leader_separator, GroupLayout.Alignment.TRAILING)
            .addComponent(this._role_label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
    }
    
    
    
    
    
    public JCheckBox add_member_checkbox() { return this._add_checkbox; }
    public JRadioButton appoint_leader_button() { return this._leader_button; }
    
}

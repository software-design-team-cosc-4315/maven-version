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
public class LoginPage extends javax.swing.JFrame {

    /**
     * Creates new form LoginPage
     */
    public LoginPage() {
        initComponents();
        this.refresh();
        
        /*
        DBConnection.connect();
        
        boolean loaded;
        CallableStatement cs = DBConnection.callable_statement("COMPUTE_PRODUCTIVITY(?, ?, ?)");
        loaded = (cs != null)? DBConnection.set_statement_value(cs, 1, "presentationteam") : false;
        loaded = loaded? DBConnection.register_out_parameter(cs, 2, OracleTypes.CURSOR) : false;
        loaded = loaded? DBConnection.register_out_parameter(cs, 3, OracleTypes.CURSOR) : false;
        DBConnection.execute(cs);
        
        try {
            ResultSet rs = (ResultSet) cs.getObject(2);
            while (rs.next())
                System.out.println(rs.getDouble("SUBTASK_WEIGHTS") + " " + rs.getInt("SUBTASK_COUNT") + " " + rs.getString("STATUS") + " " + rs.getString("USERNAME"));
            System.out.println("\n");
            
            rs = (ResultSet) cs.getObject(3);
            while (rs.next())
                System.out.println(rs.getDouble("TASK_WEIGHT") + " " + rs.getInt("TASK_COUNT") + " " + rs.getString("STATUS"));
        
        } catch (Exception e) {
            System.out.println(e);
        }
        
        DBConnection.disconnect();
        */
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login_page_description_pane = new javax.swing.JPanel();
        login_pager_inner_title_pane = new javax.swing.JPanel();
        login_page_title_label = new java.awt.Label();
        login_page_credentials_entry_pane = new javax.swing.JPanel();
        login_page_username_pane = new javax.swing.JPanel();
        login_page_user_name_label = new javax.swing.JLabel();
        login_page_username_text_field = new javax.swing.JTextField();
        login_page_password_pane = new javax.swing.JPanel();
        login_page_password_label = new javax.swing.JLabel();
        login_page_password_field = new javax.swing.JPasswordField();
        login_page_button_submit_login = new javax.swing.JButton();
        login_page_forgot_password_hint_label = new javax.swing.JLabel();
        login_message = new javax.swing.JLabel();
        login_page_password_reset_pane = new javax.swing.JPanel();
        login_page_button_submit_password_reset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Login Frame"); // NOI18N

        login_page_title_label.setFont(new java.awt.Font("Lucida Grande", 1, 20)); // NOI18N
        login_page_title_label.setText("Login Page");

        javax.swing.GroupLayout login_pager_inner_title_paneLayout = new javax.swing.GroupLayout(login_pager_inner_title_pane);
        login_pager_inner_title_pane.setLayout(login_pager_inner_title_paneLayout);
        login_pager_inner_title_paneLayout.setHorizontalGroup(
            login_pager_inner_title_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_pager_inner_title_paneLayout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addComponent(login_page_title_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        login_pager_inner_title_paneLayout.setVerticalGroup(
            login_pager_inner_title_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_pager_inner_title_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(login_page_title_label, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout login_page_description_paneLayout = new javax.swing.GroupLayout(login_page_description_pane);
        login_page_description_pane.setLayout(login_page_description_paneLayout);
        login_page_description_paneLayout.setHorizontalGroup(
            login_page_description_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(login_pager_inner_title_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        login_page_description_paneLayout.setVerticalGroup(
            login_page_description_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, login_page_description_paneLayout.createSequentialGroup()
                .addComponent(login_pager_inner_title_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        login_page_credentials_entry_pane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enter Your Credentials", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 12))); // NOI18N

        login_page_user_name_label.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        login_page_user_name_label.setText("Username:");

        login_page_username_text_field.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        login_page_username_text_field.setName("Username Input Field"); // NOI18N

        javax.swing.GroupLayout login_page_username_paneLayout = new javax.swing.GroupLayout(login_page_username_pane);
        login_page_username_pane.setLayout(login_page_username_paneLayout);
        login_page_username_paneLayout.setHorizontalGroup(
            login_page_username_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_page_username_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(login_page_user_name_label, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(login_page_username_text_field, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );
        login_page_username_paneLayout.setVerticalGroup(
            login_page_username_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_page_username_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(login_page_username_text_field, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addComponent(login_page_user_name_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        login_page_password_label.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        login_page_password_label.setText("Password:");

        login_page_password_field.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        login_page_password_field.setName("Password Input Field"); // NOI18N

        javax.swing.GroupLayout login_page_password_paneLayout = new javax.swing.GroupLayout(login_page_password_pane);
        login_page_password_pane.setLayout(login_page_password_paneLayout);
        login_page_password_paneLayout.setHorizontalGroup(
            login_page_password_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_page_password_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(login_page_password_label, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(login_page_password_field, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        login_page_password_paneLayout.setVerticalGroup(
            login_page_password_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_page_password_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(login_page_password_field, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addComponent(login_page_password_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        login_page_button_submit_login.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        login_page_button_submit_login.setText("Log In");
        login_page_button_submit_login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        login_page_button_submit_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_page_button_submit_loginActionPerformed(evt);
            }
        });

        login_page_forgot_password_hint_label.setForeground(new java.awt.Color(0, 0, 255));
        login_page_forgot_password_hint_label.setText("forgot password?");
        login_page_forgot_password_hint_label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout login_page_credentials_entry_paneLayout = new javax.swing.GroupLayout(login_page_credentials_entry_pane);
        login_page_credentials_entry_pane.setLayout(login_page_credentials_entry_paneLayout);
        login_page_credentials_entry_paneLayout.setHorizontalGroup(
            login_page_credentials_entry_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_page_credentials_entry_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(login_page_credentials_entry_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(login_page_username_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(login_page_password_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(login_page_credentials_entry_paneLayout.createSequentialGroup()
                        .addComponent(login_page_button_submit_login, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(login_page_forgot_password_hint_label)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(login_message, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        login_page_credentials_entry_paneLayout.setVerticalGroup(
            login_page_credentials_entry_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_page_credentials_entry_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(login_page_username_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(login_page_password_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(login_page_credentials_entry_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(login_page_button_submit_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(login_page_forgot_password_hint_label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(login_message)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        login_page_password_reset_pane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Password Reset Request Form", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 12))); // NOI18N

        login_page_button_submit_password_reset.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        login_page_button_submit_password_reset.setText("Submit");
        login_page_button_submit_password_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout login_page_password_reset_paneLayout = new javax.swing.GroupLayout(login_page_password_reset_pane);
        login_page_password_reset_pane.setLayout(login_page_password_reset_paneLayout);
        login_page_password_reset_paneLayout.setHorizontalGroup(
            login_page_password_reset_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(login_page_password_reset_paneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(login_page_button_submit_password_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        login_page_password_reset_paneLayout.setVerticalGroup(
            login_page_password_reset_paneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, login_page_password_reset_paneLayout.createSequentialGroup()
                .addGap(0, 124, Short.MAX_VALUE)
                .addComponent(login_page_button_submit_password_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(login_page_description_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(login_page_credentials_entry_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(login_page_password_reset_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(login_page_description_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(login_page_credentials_entry_pane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(login_page_password_reset_pane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /*
        Function to start authenticating the user when the submit-login button is clicked (and released)
    */
    private void login_page_button_submit_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_page_button_submit_loginActionPerformed
        
        String username = this.login_page_username_text_field.getText();
        if (username.length() < 6 || this.login_page_password_field.getPassword().length < 6) {
            this.login_message.setText("Invalid username or password!");
            return;
        }
        
        // Use the system controller to manage authentication and system state change:
        String auth_message = SystemController.authenticate(username, this.login_page_password_field.getPassword());
        if (auth_message == null) {
            this.login_page_username_text_field.setText("");
            this.login_page_password_field.setText("");
            this.login_message.setText("");
        } else
            this.login_message.setText(auth_message);
    }//GEN-LAST:event_login_page_button_submit_loginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel login_message;
    private javax.swing.JButton login_page_button_submit_login;
    private javax.swing.JButton login_page_button_submit_password_reset;
    private javax.swing.JPanel login_page_credentials_entry_pane;
    private javax.swing.JPanel login_page_description_pane;
    private javax.swing.JLabel login_page_forgot_password_hint_label;
    private javax.swing.JPasswordField login_page_password_field;
    private javax.swing.JLabel login_page_password_label;
    private javax.swing.JPanel login_page_password_pane;
    private javax.swing.JPanel login_page_password_reset_pane;
    private java.awt.Label login_page_title_label;
    private javax.swing.JLabel login_page_user_name_label;
    private javax.swing.JPanel login_page_username_pane;
    private javax.swing.JTextField login_page_username_text_field;
    private javax.swing.JPanel login_pager_inner_title_pane;
    // End of variables declaration//GEN-END:variables


    private void refresh() {
        
        this.login_page_username_text_field.setText("");
        this.login_page_password_field.setText("");
        this.login_message.setText("");
        
    }

    



}

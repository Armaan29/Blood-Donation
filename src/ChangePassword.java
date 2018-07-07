
import com.mysql.jdbc.Statement;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;


public class ChangePassword extends javax.swing.JFrame 
{

    
    public ChangePassword()
    {
        initComponents();
        this.getContentPane().setBackground(new Color(204, 204, 255));
        changePasswordOldNotMatchLable.setVisible(false);
        changePasswordNotMatchLable.setVisible(false);
        changePasswordSucessLable.setVisible(false);
//        changePasswordOldText.setFocusable(true);
        try 
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select email from login");
            rs.next();
            String email = rs.getString("email");
            if(email == null)
            {
                changePasswordEmailText.setText("");
            }
            else
            {
                changePasswordEmailText.setText(email);
            }
            DataBaseConnection.close(con);
        }   
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
    }

    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        changePasswordEmailLable = new javax.swing.JLabel();
        changePasswordNewLable = new javax.swing.JLabel();
        changePasswordConfirmLable = new javax.swing.JLabel();
        changePasswordNewText = new javax.swing.JPasswordField();
        changePasswordConfirmText = new javax.swing.JPasswordField();
        changePasswordChangeButton = new javax.swing.JButton();
        changePasswordCancleButton = new javax.swing.JButton();
        changePasswordOldNotMatchLable = new javax.swing.JLabel();
        changePasswordNotMatchLable = new javax.swing.JLabel();
        changePasswordOldText = new javax.swing.JPasswordField();
        changePasswordOldLable = new javax.swing.JLabel();
        changePasswordEmailRecoveryLable = new javax.swing.JLabel();
        changePasswordEmailText = new javax.swing.JTextField();
        changePasswordSucessLable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Change Password");
        setPreferredSize(new java.awt.Dimension(300, 350));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(null);

        changePasswordEmailLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        changePasswordEmailLable.setText("E-mail");
        getContentPane().add(changePasswordEmailLable);
        changePasswordEmailLable.setBounds(90, 30, 35, 18);

        changePasswordNewLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        changePasswordNewLable.setText("New Password");
        getContentPane().add(changePasswordNewLable);
        changePasswordNewLable.setBounds(20, 160, 89, 18);

        changePasswordConfirmLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        changePasswordConfirmLable.setText("Confirm Password");
        getContentPane().add(changePasswordConfirmLable);
        changePasswordConfirmLable.setBounds(20, 220, 110, 18);
        getContentPane().add(changePasswordNewText);
        changePasswordNewText.setBounds(140, 150, 124, 29);
        getContentPane().add(changePasswordConfirmText);
        changePasswordConfirmText.setBounds(140, 210, 124, 29);

        changePasswordChangeButton.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        changePasswordChangeButton.setText("Change");
        changePasswordChangeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasswordChangeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(changePasswordChangeButton);
        changePasswordChangeButton.setBounds(20, 280, 111, 33);

        changePasswordCancleButton.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        changePasswordCancleButton.setText("Cancel");
        changePasswordCancleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasswordCancleButtonActionPerformed(evt);
            }
        });
        getContentPane().add(changePasswordCancleButton);
        changePasswordCancleButton.setBounds(140, 280, 85, 33);

        changePasswordOldNotMatchLable.setForeground(new java.awt.Color(255, 0, 0));
        changePasswordOldNotMatchLable.setText("Password not matched");
        getContentPane().add(changePasswordOldNotMatchLable);
        changePasswordOldNotMatchLable.setBounds(140, 120, 140, 14);

        changePasswordNotMatchLable.setForeground(new java.awt.Color(255, 0, 0));
        changePasswordNotMatchLable.setText("Passwords not equal");
        getContentPane().add(changePasswordNotMatchLable);
        changePasswordNotMatchLable.setBounds(140, 250, 140, 14);

        changePasswordOldText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasswordOldTextActionPerformed(evt);
            }
        });
        getContentPane().add(changePasswordOldText);
        changePasswordOldText.setBounds(140, 90, 124, 29);

        changePasswordOldLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        changePasswordOldLable.setText("Old Password");
        getContentPane().add(changePasswordOldLable);
        changePasswordOldLable.setBounds(20, 100, 83, 18);

        changePasswordEmailRecoveryLable.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        changePasswordEmailRecoveryLable.setText("(for recovery)");
        getContentPane().add(changePasswordEmailRecoveryLable);
        changePasswordEmailRecoveryLable.setBounds(130, 30, 65, 13);
        getContentPane().add(changePasswordEmailText);
        changePasswordEmailText.setBounds(50, 50, 194, 29);

        changePasswordSucessLable.setForeground(new java.awt.Color(0, 153, 0));
        changePasswordSucessLable.setText("Password changed secessfully !!");
        getContentPane().add(changePasswordSucessLable);
        changePasswordSucessLable.setBounds(60, 10, 190, 14);

        setSize(new java.awt.Dimension(300, 369));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void changePasswordCancleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordCancleButtonActionPerformed
       this.dispose();
    }//GEN-LAST:event_changePasswordCancleButtonActionPerformed

    private void changePasswordChangeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordChangeButtonActionPerformed
        String oldPassword = changePasswordOldText.getText();
        String newPassword = changePasswordNewText.getText();
        String confirmPassword = changePasswordConfirmText.getText();
        String email = changePasswordEmailText.getText();
        if(!(oldPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")))
        {
            if(newPassword.equals(confirmPassword))
            {
                changePasswordNotMatchLable.setVisible(false);
                try
                {
                    Connection con = DataBaseConnection.open();
                    Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("select * from login");
                    rs.next();
                    String old = rs.getString("password");
                    if(old.equals(oldPassword))
                    {
                        rs.updateString("password", newPassword);
                        rs.updateString("email", email);
                        rs.updateRow();
                        changePasswordNotMatchLable.setVisible(false);
                        changePasswordOldNotMatchLable.setVisible(false);
                        changePasswordSucessLable.setVisible(true);
                        changePasswordOldText.setText("");
                        changePasswordNewText.setText("");
                        changePasswordConfirmText.setText("");
                        
                    }
                    else
                    {
                        changePasswordOldNotMatchLable.setVisible(true);
                    }
                    DataBaseConnection.close(con);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                changePasswordNotMatchLable.setVisible(true);
                changePasswordOldNotMatchLable.setVisible(false);
            }
        }
    }//GEN-LAST:event_changePasswordChangeButtonActionPerformed

    private void changePasswordOldTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordOldTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_changePasswordOldTextActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Global.mainForm.setEnabled(true);
        Global.mainForm.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChangePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChangePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChangePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChangePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ChangePassword().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changePasswordCancleButton;
    private javax.swing.JButton changePasswordChangeButton;
    private javax.swing.JLabel changePasswordConfirmLable;
    private javax.swing.JPasswordField changePasswordConfirmText;
    private javax.swing.JLabel changePasswordEmailLable;
    private javax.swing.JLabel changePasswordEmailRecoveryLable;
    private javax.swing.JTextField changePasswordEmailText;
    private javax.swing.JLabel changePasswordNewLable;
    private javax.swing.JPasswordField changePasswordNewText;
    private javax.swing.JLabel changePasswordNotMatchLable;
    private javax.swing.JLabel changePasswordOldLable;
    private javax.swing.JLabel changePasswordOldNotMatchLable;
    private javax.swing.JPasswordField changePasswordOldText;
    private javax.swing.JLabel changePasswordSucessLable;
    // End of variables declaration//GEN-END:variables
}

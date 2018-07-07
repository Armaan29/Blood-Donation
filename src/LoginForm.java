
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class LoginForm extends javax.swing.JFrame 
{
    
    
    public LoginForm() 
    {
        initComponents();
        loginNotCorrectLable.setVisible(false);
        this.getContentPane().setBackground(Color.WHITE);
    }

   
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        loginUserLable = new javax.swing.JLabel();
        loginPasswordLable = new javax.swing.JLabel();
        loginUserText = new javax.swing.JTextField();
        loginPasswordText = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        loginForgotLable = new javax.swing.JLabel();
        loginNotCorrectLable = new javax.swing.JLabel();
        loginCoverPhotoLable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("loginForm"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(null);

        loginUserLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        loginUserLable.setText("User Name");
        getContentPane().add(loginUserLable);
        loginUserLable.setBounds(70, 130, 83, 29);

        loginPasswordLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        loginPasswordLable.setText("Password");
        getContentPane().add(loginPasswordLable);
        loginPasswordLable.setBounds(70, 190, 75, 29);
        getContentPane().add(loginUserText);
        loginUserText.setBounds(180, 130, 131, 29);
        getContentPane().add(loginPasswordText);
        loginPasswordText.setBounds(180, 190, 131, 29);

        loginButton.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        getContentPane().add(loginButton);
        loginButton.setBounds(120, 250, 100, 34);

        loginForgotLable.setForeground(new java.awt.Color(0, 51, 255));
        loginForgotLable.setText("Forgot Password");
        loginForgotLable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        loginForgotLable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginForgotLableMouseClicked(evt);
            }
        });
        getContentPane().add(loginForgotLable);
        loginForgotLable.setBounds(240, 270, 100, 14);

        loginNotCorrectLable.setForeground(new java.awt.Color(253, 0, 0));
        loginNotCorrectLable.setText("Username or Password not correct");
        getContentPane().add(loginNotCorrectLable);
        loginNotCorrectLable.setBounds(80, 110, 210, 14);

        loginCoverPhotoLable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/login_cover.jpg"))); // NOI18N
        getContentPane().add(loginCoverPhotoLable);
        loginCoverPhotoLable.setBounds(0, 0, 400, 100);

        setSize(new java.awt.Dimension(416, 339));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        loginNotCorrectLable.setVisible(false);
        String userName = loginUserText.getText();
        String password = loginPasswordText.getText();
        if(!(userName.equals("") || password.equals("")))
        {
            
            try
            {
                Connection con = DataBaseConnection.open();
                Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("select * from login");
                rs.next();
                if(userName.equals(rs.getString("user_name")) && password.equals(rs.getString("password")))
                {
                    new MainForm().setVisible(true);
                    this.dispose();
                }
                else
                {
                    loginNotCorrectLable.setText("username or password not match");
                    loginNotCorrectLable.setVisible(true);
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
             loginNotCorrectLable.setVisible(true);
             loginNotCorrectLable.setText("Fill up all details");
        }
    }//GEN-LAST:event_loginButtonActionPerformed

    private void loginForgotLableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginForgotLableMouseClicked
        new Thread(new Runnable() 
        {

            @Override
            public void run() 
            {
                Connection con = null;
                try
                {
                    con = DataBaseConnection.open();
                    Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("select * from login");
                    rs.next();
                    String s = rs.getString("email");
                    if(s == null | s.trim().equals(""))
                    {
                        JOptionPane.showInternalMessageDialog(getContentPane(), "Recovery Email not set. Contact Service!");
                    }
                    else
                    {
                        new SendMail("Your password is : "+rs.getString("password"), rs.getString("email"));
                    }

                }
                catch (Exception ex) 
                {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(getContentPane(), "OOPS !! Internet is not working.");
                }
                finally
                {
                    DataBaseConnection.close(con);
                }
            }
        }).start();
        JOptionPane.showInternalMessageDialog(getContentPane(), "Password sent to your recovery Email ID.");
        
    }//GEN-LAST:event_loginForgotLableMouseClicked

   
    public static void main(String args[]) 
    {
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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() 
        {

            public void run() 
            {
                new LoginForm().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton loginButton;
    private javax.swing.JLabel loginCoverPhotoLable;
    private javax.swing.JLabel loginForgotLable;
    private javax.swing.JLabel loginNotCorrectLable;
    private javax.swing.JLabel loginPasswordLable;
    private javax.swing.JPasswordField loginPasswordText;
    private javax.swing.JLabel loginUserLable;
    private javax.swing.JTextField loginUserText;
    // End of variables declaration//GEN-END:variables
}

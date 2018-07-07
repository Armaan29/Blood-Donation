
import java.awt.*;
import java.util.Properties;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pavilion
 */
public class MainForm extends javax.swing.JFrame {

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        Rectangle r = this.getBounds();
        r.y = 0;
        this.setBounds(r);
        this.getContentPane().setBackground(Color.WHITE);
        
    }

    boolean logOut()
    {
        int i = JOptionPane.showConfirmDialog(this, "Do you want to Log out ?", "Log out", JOptionPane.YES_NO_OPTION);
        if(i == 0)
            return true;
        else
            return false;
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        menuBarMain = new javax.swing.JMenuBar();
        mainStartCampMenu = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        mainExistingCamp = new javax.swing.JMenuItem();
        menuMainViewEdit = new javax.swing.JMenu();
        mainMenuViewEditBloodBankMenuItem = new javax.swing.JMenuItem();
        mainMenuViewEditCampsMenuItem = new javax.swing.JMenuItem();
        mainMenuViewEditDonorsMenuItem = new javax.swing.JMenuItem();
        mainMenuViewEditDonorsCampWiseMenuItem = new javax.swing.JMenuItem();
        mainMenuViewEditHospitalsMenuItem = new javax.swing.JMenuItem();
        mainMenuViewSupplyMenuItem = new javax.swing.JMenuItem();
        mainNewHospitalMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        menuMainSettings = new javax.swing.JMenu();
        menuItemChangePassword = new javax.swing.JMenuItem();
        menuItemLogout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Blood Donation");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setExtendedState(6);
        setName("mainForm");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/main_top_left.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(80, 50, 280, 360);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/main_bottom_left.jpg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(80, 450, 370, 170);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/main_top_right.jpg"))); // NOI18N
        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(520, 30, 410, 150);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/main_bottom_right.jpg"))); // NOI18N
        jLabel4.setText("jLabel4");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(600, 370, 330, 260);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/main_center1.jpg"))); // NOI18N
        jLabel5.setText("jLabel5");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(410, 220, 170, 170);

        menuBarMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        menuBarMain.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        menuBarMain.setPreferredSize(new java.awt.Dimension(98, 40));

        mainStartCampMenu.setText("Start Camp");
        mainStartCampMenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mainStartCampMenu.setPreferredSize(new java.awt.Dimension(100, 19));

        jMenuItem1.setText("New Camp");
        jMenuItem1.setPreferredSize(new java.awt.Dimension(130, 30));
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mainStartCampMenu.add(jMenuItem1);

        mainExistingCamp.setText("Existing Camp");
        mainExistingCamp.setPreferredSize(new java.awt.Dimension(107, 30));
        mainExistingCamp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainExistingCampActionPerformed(evt);
            }
        });
        mainStartCampMenu.add(mainExistingCamp);

        menuBarMain.add(mainStartCampMenu);

        menuMainViewEdit.setBorder(null);
        menuMainViewEdit.setText("View/Edit");
        menuMainViewEdit.setName("menuI");
        menuMainViewEdit.setPreferredSize(new java.awt.Dimension(100, 19));

        mainMenuViewEditBloodBankMenuItem.setText("Blood Bank");
        mainMenuViewEditBloodBankMenuItem.setPreferredSize(new java.awt.Dimension(137, 30));
        mainMenuViewEditBloodBankMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuViewEditBloodBankMenuItemActionPerformed(evt);
            }
        });
        menuMainViewEdit.add(mainMenuViewEditBloodBankMenuItem);

        mainMenuViewEditCampsMenuItem.setText("Camps");
        mainMenuViewEditCampsMenuItem.setPreferredSize(new java.awt.Dimension(137, 30));
        mainMenuViewEditCampsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuViewEditCampsMenuItemActionPerformed(evt);
            }
        });
        menuMainViewEdit.add(mainMenuViewEditCampsMenuItem);

        mainMenuViewEditDonorsMenuItem.setText("Donors");
        mainMenuViewEditDonorsMenuItem.setPreferredSize(new java.awt.Dimension(137, 30));
        mainMenuViewEditDonorsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuViewEditDonorsMenuItemActionPerformed(evt);
            }
        });
        menuMainViewEdit.add(mainMenuViewEditDonorsMenuItem);

        mainMenuViewEditDonorsCampWiseMenuItem.setText("Donors Camp Wise");
        mainMenuViewEditDonorsCampWiseMenuItem.setPreferredSize(new java.awt.Dimension(145, 30));
        mainMenuViewEditDonorsCampWiseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuViewEditDonorsCampWiseMenuItemActionPerformed(evt);
            }
        });
        menuMainViewEdit.add(mainMenuViewEditDonorsCampWiseMenuItem);

        mainMenuViewEditHospitalsMenuItem.setText("Hospitals");
        mainMenuViewEditHospitalsMenuItem.setPreferredSize(new java.awt.Dimension(146, 30));
        mainMenuViewEditHospitalsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuViewEditHospitalsMenuItemActionPerformed(evt);
            }
        });
        menuMainViewEdit.add(mainMenuViewEditHospitalsMenuItem);

        mainMenuViewSupplyMenuItem.setText("Supply");
        mainMenuViewSupplyMenuItem.setPreferredSize(new java.awt.Dimension(146, 30));
        mainMenuViewSupplyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuViewSupplyMenuItemActionPerformed(evt);
            }
        });
        menuMainViewEdit.add(mainMenuViewSupplyMenuItem);

        menuBarMain.add(menuMainViewEdit);

        mainNewHospitalMenu.setText("New Hospital");
        mainNewHospitalMenu.setPreferredSize(new java.awt.Dimension(100, 19));
        mainNewHospitalMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainNewHospitalMenuMouseClicked(evt);
            }
        });
        menuBarMain.add(mainNewHospitalMenu);

        jMenu1.setText("Supply");
        jMenu1.setPreferredSize(new java.awt.Dimension(100, 19));
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        menuBarMain.add(jMenu1);

        menuMainSettings.setText("Settings");
        menuMainSettings.setPreferredSize(new java.awt.Dimension(100, 19));

        menuItemChangePassword.setText("Change Password");
        menuItemChangePassword.setPreferredSize(new java.awt.Dimension(137, 30));
        menuItemChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemChangePasswordActionPerformed(evt);
            }
        });
        menuMainSettings.add(menuItemChangePassword);

        menuItemLogout.setText("Logout");
        menuItemLogout.setPreferredSize(new java.awt.Dimension(137, 30));
        menuItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLogoutActionPerformed(evt);
            }
        });
        menuMainSettings.add(menuItemLogout);

        menuBarMain.add(menuMainSettings);

        setJMenuBar(menuBarMain);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1000)/2, (screenSize.height-739)/2, 1000, 739);
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLogoutActionPerformed
      if(logOut())
      {
        new LoginForm().setVisible(true);
        this.dispose();
      }
    }//GEN-LAST:event_menuItemLogoutActionPerformed

    private void menuItemChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemChangePasswordActionPerformed
        new ChangePassword().setVisible(true);
        Global.mainForm = this;
        this.setEnabled(false);
    }//GEN-LAST:event_menuItemChangePasswordActionPerformed

    private void mainMenuViewEditBloodBankMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuViewEditBloodBankMenuItemActionPerformed
        new BloodBankAvailablity().setVisible(true);
        Global.mainForm = this;
        this.setEnabled(false);
    }//GEN-LAST:event_mainMenuViewEditBloodBankMenuItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(logOut())
        {
           new LoginForm().setVisible(true);
           this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Global.mainForm = this;
        this.setEnabled(false);
        new NewCamp().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void mainExistingCampActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainExistingCampActionPerformed
        Global.mainForm = this;
        this.setEnabled(false);
        new ExistingCamp().setVisible(true);
    }//GEN-LAST:event_mainExistingCampActionPerformed

    private void mainMenuViewEditCampsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuViewEditCampsMenuItemActionPerformed
        Global.mainForm = this;
        this.setEnabled(false);
        new ViewCamps().setVisible(true);
    }//GEN-LAST:event_mainMenuViewEditCampsMenuItemActionPerformed

    private void mainMenuViewEditDonorsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuViewEditDonorsMenuItemActionPerformed
        Global.mainForm = this;
        this.setEnabled(false);
        new ViewDonors().setVisible(true);
    }//GEN-LAST:event_mainMenuViewEditDonorsMenuItemActionPerformed

    private void mainMenuViewEditDonorsCampWiseMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuViewEditDonorsCampWiseMenuItemActionPerformed
        Global.mainForm = this;
        this.setEnabled(false);
        new ViewDonorsCampWise().setVisible(true);
    }//GEN-LAST:event_mainMenuViewEditDonorsCampWiseMenuItemActionPerformed

    private void mainNewHospitalMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainNewHospitalMenuMouseClicked
        Global.mainForm = this;
        this.setEnabled(false);
        new NewHospital().setVisible(true);
    }//GEN-LAST:event_mainNewHospitalMenuMouseClicked

    private void mainMenuViewEditHospitalsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuViewEditHospitalsMenuItemActionPerformed
        Global.mainForm = this;
        this.setEnabled(false);
        new ViewHospitals().setVisible(true);
    }//GEN-LAST:event_mainMenuViewEditHospitalsMenuItemActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        Global.mainForm = this;
        this.setEnabled(false);
        new NewSupply().setVisible(true);
    }//GEN-LAST:event_jMenu1MouseClicked

    private void mainMenuViewSupplyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuViewSupplyMenuItemActionPerformed
        Global.mainForm = this;
        this.setEnabled(false);
        new ViewSupply().setVisible(true);
    }//GEN-LAST:event_mainMenuViewSupplyMenuItemActionPerformed

    public static void main(String[] args) 
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
        try
        {
//            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
//            Properties props = new Properties();
//            props.put("logoString", "my company");

            // If the decorationStyle is not "default" we have to switch off the windowDecoration property
//            if (!DEFAULT_DECORATION.equals(decorationStyle))
//            {
//                props.put("windowDecoration", "off");
//            }
//            com.jtattoo.plaf.hifi.HiFiLookAndFeel.setCurrentTheme(props);
//            UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//            UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
//            UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        new MainForm().setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem mainExistingCamp;
    private javax.swing.JMenuItem mainMenuViewEditBloodBankMenuItem;
    private javax.swing.JMenuItem mainMenuViewEditCampsMenuItem;
    private javax.swing.JMenuItem mainMenuViewEditDonorsCampWiseMenuItem;
    private javax.swing.JMenuItem mainMenuViewEditDonorsMenuItem;
    private javax.swing.JMenuItem mainMenuViewEditHospitalsMenuItem;
    private javax.swing.JMenuItem mainMenuViewSupplyMenuItem;
    private javax.swing.JMenu mainNewHospitalMenu;
    private javax.swing.JMenu mainStartCampMenu;
    private javax.swing.JMenuBar menuBarMain;
    private javax.swing.JMenuItem menuItemChangePassword;
    private javax.swing.JMenuItem menuItemLogout;
    private javax.swing.JMenu menuMainSettings;
    private javax.swing.JMenu menuMainViewEdit;
    // End of variables declaration//GEN-END:variables
}

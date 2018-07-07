
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class NewCamp extends javax.swing.JFrame 
{
    DateTextField newCampDateText;
    
    public NewCamp() 
    {
        initComponents();
        newCampDateText = new DateTextField();
        newCampDateText.setBounds(180, 350, 180, 33);
        this.add(newCampDateText);
        
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        newCampNameLabel = new javax.swing.JLabel();
        newCampLocationLabel = new javax.swing.JLabel();
        newCampDateLabel = new javax.swing.JLabel();
        newCampNameText = new javax.swing.JTextField();
        newCampStartButton = new javax.swing.JButton();
        newCampCityLabel = new javax.swing.JLabel();
        newCampStateLabel = new javax.swing.JLabel();
        newCampCityText = new javax.swing.JTextField();
        newCampStateText = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        newCampLocationText = new javax.swing.JTextArea();

        jTextField3.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        newCampNameLabel.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newCampNameLabel.setText("Camp Name");
        getContentPane().add(newCampNameLabel);
        newCampNameLabel.setBounds(70, 80, 82, 33);

        newCampLocationLabel.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newCampLocationLabel.setText("Location");
        getContentPane().add(newCampLocationLabel);
        newCampLocationLabel.setBounds(70, 160, 82, 33);

        newCampDateLabel.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newCampDateLabel.setText("Date");
        getContentPane().add(newCampDateLabel);
        newCampDateLabel.setBounds(70, 350, 82, 33);
        getContentPane().add(newCampNameText);
        newCampNameText.setBounds(180, 80, 180, 33);

        newCampStartButton.setText("Start");
        newCampStartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newCampStartButtonActionPerformed(evt);
            }
        });
        getContentPane().add(newCampStartButton);
        newCampStartButton.setBounds(140, 400, 120, 40);

        newCampCityLabel.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newCampCityLabel.setText("City");
        getContentPane().add(newCampCityLabel);
        newCampCityLabel.setBounds(70, 250, 82, 33);

        newCampStateLabel.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newCampStateLabel.setText("State");
        getContentPane().add(newCampStateLabel);
        newCampStateLabel.setBounds(70, 300, 82, 33);
        getContentPane().add(newCampCityText);
        newCampCityText.setBounds(180, 250, 180, 33);
        getContentPane().add(newCampStateText);
        newCampStateText.setBounds(180, 300, 180, 33);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 43, 52));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("New Camp");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(70, 0, 290, 70);

        newCampLocationText.setColumns(20);
        newCampLocationText.setLineWrap(true);
        newCampLocationText.setRows(5);
        jScrollPane1.setViewportView(newCampLocationText);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(180, 130, 180, 100);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-466)/2, (screenSize.height-489)/2, 466, 489);
    }// </editor-fold>//GEN-END:initComponents

    private void newCampStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newCampStartButtonActionPerformed
        String name = newCampNameText.getText();
        String location = newCampLocationText.getText();
        String city = newCampCityText.getText();
        String state = newCampStateText.getText();
        String date = newCampDateText.getText();
        
        if(name.isEmpty() || location.isEmpty() || city.isEmpty() || state.isEmpty() || date.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "All fields mandatory !!");
        }
        else
        {
            try
            {
                boolean isNotExist = true; 
                Connection testCon = DataBaseConnection.open();
                Statement testStmt = (Statement)testCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet testRs = testStmt.executeQuery("select camp_id from camp where camp_date='"+date+"' and camp_name='"+name+"'");
                if(testRs.next())
                {
                    JOptionPane.showMessageDialog(this, "This entry already exists !!");
                    isNotExist = false;
                }
                DataBaseConnection.close(testCon);
                if(isNotExist)
                {
                    Connection con = DataBaseConnection.open();
                    Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("select * from camp");
                    rs.moveToInsertRow();
                    rs.updateString("camp_name", name);
                    rs.updateString("camp_location",location);
                    rs.updateString("camp_city", city);
                    rs.updateString("camp_state", state);
                    rs.updateString("camp_date", date);
                    rs.insertRow();
                    rs.last();

                    int id = rs.getInt("camp_id");
                    DataBaseConnection.close(con);
                    this.dispose();
                    new WorkingCamp(id).setVisible(true);
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_newCampStartButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Global.mainForm.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

    
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
            java.util.logging.Logger.getLogger(NewCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new NewCamp().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel newCampCityLabel;
    private javax.swing.JTextField newCampCityText;
    private javax.swing.JLabel newCampDateLabel;
    private javax.swing.JLabel newCampLocationLabel;
    private javax.swing.JTextArea newCampLocationText;
    private javax.swing.JLabel newCampNameLabel;
    private javax.swing.JTextField newCampNameText;
    private javax.swing.JButton newCampStartButton;
    private javax.swing.JLabel newCampStateLabel;
    private javax.swing.JTextField newCampStateText;
    // End of variables declaration//GEN-END:variables
}


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class ExistingCamp extends javax.swing.JFrame {

    
    public ExistingCamp() 
    {
        initComponents();
            
        
        try
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select camp_date from camp group by camp_date");
            while(rs.next())
            {
                existingCampDateCombo.addItem(rs.getString("camp_date"));
            }
            DataBaseConnection.close(con);
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        existingCampDateCombo = new javax.swing.JComboBox();
        existingCampNameCombo = new javax.swing.JComboBox();
        existingCampStartCampButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 34, 52));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Select Camp");

        jLabel2.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLabel2.setText("Date");

        jLabel3.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLabel3.setText("Name");

        existingCampDateCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Select--" }));
        existingCampDateCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                existingCampDateComboItemStateChanged(evt);
            }
        });
        existingCampDateCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingCampDateComboActionPerformed(evt);
            }
        });

        existingCampNameCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Select--" }));
        existingCampNameCombo.setEnabled(false);

        existingCampStartCampButton.setText("Start Camp");
        existingCampStartCampButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                existingCampStartCampButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(existingCampDateCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(existingCampNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(existingCampStartCampButton, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(existingCampDateCombo, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(existingCampNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(existingCampStartCampButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        setSize(new java.awt.Dimension(316, 339));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void existingCampDateComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_existingCampDateComboItemStateChanged
        existingCampNameCombo.removeAllItems();
        String date = existingCampDateCombo.getSelectedItem().toString();
        if(date.equals("--Select--"))
        {
            existingCampNameCombo.addItem("--Select--");
            existingCampNameCombo.setEnabled(false);
        }
        else
        {
            try
            {
                Connection con = DataBaseConnection.open();
                Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("select camp_name from camp where camp_date='"+date+"'");
                while(rs.next())
                {
                    existingCampNameCombo.addItem(rs.getString("camp_name"));
                    
                }
                DataBaseConnection.close(con);
                existingCampNameCombo.setEnabled(true);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_existingCampDateComboItemStateChanged

    private void existingCampStartCampButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingCampStartCampButtonActionPerformed
        String date = existingCampDateCombo.getSelectedItem().toString();
        String name = existingCampNameCombo.getSelectedItem().toString();
        if(!(date.equals("--Start--") || date.equals("--Select--")))
        {
            try
            {
                Connection con = DataBaseConnection.open();
                Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("select camp_id from camp where camp_date='"+date+"' and camp_name='"+name+"'");
                rs.next();
                int id = rs.getInt("camp_id");
                DataBaseConnection.close(con);
                this.dispose();
                new WorkingCamp(id).setVisible(true);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_existingCampStartCampButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Global.mainForm.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

    private void existingCampDateComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_existingCampDateComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_existingCampDateComboActionPerformed

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
            java.util.logging.Logger.getLogger(ExistingCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExistingCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExistingCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExistingCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ExistingCamp().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox existingCampDateCombo;
    private javax.swing.JComboBox existingCampNameCombo;
    private javax.swing.JButton existingCampStartCampButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}

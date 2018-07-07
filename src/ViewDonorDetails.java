
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;


public class ViewDonorDetails extends javax.swing.JFrame
{
    DateTextField viewDonorDetailsDateOfBirthText;
    int id;
    public ViewDonorDetails(int id)
    {
        initComponents();
        
        this.id = id;
        viewDonorDetailsDateOfBirthText = new DateTextField();
        viewDonorDetailsDateOfBirthText.setBounds(670, 335, 185, 35);
        this.add(viewDonorDetailsDateOfBirthText);
        try
        {
            Connection con = DataBaseConnection.open();
            com.mysql.jdbc.Statement stmt = (com.mysql.jdbc.Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from donor where donor_id="+id);
            rs.next();
            String name = rs.getString("donor_name");
            String gender = rs.getString("donor_gender");
            String phone = rs.getString("donor_phone");
            String email = rs.getString("donor_email");
            String address = rs.getString("donor_address");
            String bloodGroup = rs.getString("donor_blood_group");
            String dateOfBirth = rs.getString("donor_dob");
            Timestamp lastDonationTimeStamp = rs.getTimestamp("donor_last_donation_date");
            int numberOfDonations = rs.getInt("donor_total_donations");
            
            viewDonorDetailsNameText.setText(name);
            viewDonorDetailsPhoneText.setText(phone);
            viewDonorDetailsEmailText.setText(email);
            viewDonorDetailsAddressText.setText(address);
            viewDonorDetailsBloodGroupText.setText(bloodGroup);
            viewDonorDetailsNumberOfDonationsText.setText(""+numberOfDonations);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String dateInString = dateOfBirth;
            Date date = formatter.parse(dateInString);
            viewDonorDetailsDateOfBirthText.setDate(date);
            
            int genderId = 0;
            switch(gender)
            {
                case "Male":
                    genderId = 0;
                    break;
                case "Female":
                    genderId = 1;
                    break;
            }
            viewDonorDetailsGenderCombo.setSelectedIndex(genderId);
            
            Date d = new Date(lastDonationTimeStamp.getTime());
            String lastDate = new SimpleDateFormat("dd/MM/yyyy").format(d);
            viewDonorDetailsLastDonationText.setText(lastDate);
            
            DataBaseConnection.close(con);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
        
        
        viewDonorDetailsPhoneText.addKeyListener(new KeyAdapter() 
        {
            public void keyTyped(KeyEvent e) 
            {
                char ch = e.getKeyChar();
                if(!isNumeric(ch) || viewDonorDetailsPhoneText.getText().length()>9)
                {
                    e.consume();
                }
            }
        });   
    }

    boolean isNumeric(char c)
    {
        try 
        {
            Integer.parseInt(""+c);
            return true;
        }
        catch (Exception e) 
        {
           return false; 
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        newEmployeeNameLable = new javax.swing.JLabel();
        viewDonorDetailsNameText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        newEmployeeDesignationLable = new javax.swing.JLabel();
        viewDonorDetailsPhoneText = new javax.swing.JTextField();
        newEmployeePhoneLable = new javax.swing.JLabel();
        newEmployeeAgeLable = new javax.swing.JLabel();
        viewDonorDetailsLastDonationText = new javax.swing.JTextField();
        newEmployeeEmailLable = new javax.swing.JLabel();
        viewDonorDetailsEmailText = new javax.swing.JTextField();
        newEmployeeDateOfJoiningLable = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewDonorDetailsAddressText = new javax.swing.JTextArea();
        newEmployeeAddresslLable = new javax.swing.JLabel();
        viewDonorDetailsUpdateButton = new javax.swing.JButton();
        viewDonorDetailsCancelButton = new javax.swing.JButton();
        viewDonorDetailsBloodGroupText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        viewDonorDetailsNumberOfDonationsText = new javax.swing.JTextField();
        viewDonorDetailsGenderCombo = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        newEmployeeNameLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newEmployeeNameLable.setText("Name");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Donor Details");

        newEmployeeDesignationLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newEmployeeDesignationLable.setText("Blood Group");

        newEmployeePhoneLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newEmployeePhoneLable.setText("Phone");

        newEmployeeAgeLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newEmployeeAgeLable.setText("Last Donation");

        viewDonorDetailsLastDonationText.setEditable(false);

        newEmployeeEmailLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newEmployeeEmailLable.setText("Email");

        newEmployeeDateOfJoiningLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newEmployeeDateOfJoiningLable.setText("Date Of Birth");

        viewDonorDetailsAddressText.setColumns(16);
        viewDonorDetailsAddressText.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        viewDonorDetailsAddressText.setRows(5);
        jScrollPane1.setViewportView(viewDonorDetailsAddressText);

        newEmployeeAddresslLable.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        newEmployeeAddresslLable.setText("Address");

        viewDonorDetailsUpdateButton.setText("Update");
        viewDonorDetailsUpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDonorDetailsUpdateButtonActionPerformed(evt);
            }
        });

        viewDonorDetailsCancelButton.setText("Cancel");
        viewDonorDetailsCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDonorDetailsCancelButtonActionPerformed(evt);
            }
        });

        viewDonorDetailsBloodGroupText.setEditable(false);

        jLabel2.setText("No. of Donations");

        viewDonorDetailsNumberOfDonationsText.setEditable(false);

        viewDonorDetailsGenderCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(294, 294, 294)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(newEmployeeNameLable, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewDonorDetailsNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(newEmployeeAddresslLable, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(newEmployeeEmailLable, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewDonorDetailsEmailText))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(newEmployeePhoneLable, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewDonorDetailsPhoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(viewDonorDetailsGenderCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(newEmployeeDesignationLable, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewDonorDetailsBloodGroupText, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(newEmployeeAgeLable, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewDonorDetailsLastDonationText, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewDonorDetailsNumberOfDonationsText, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(newEmployeeDateOfJoiningLable, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(viewDonorDetailsUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(viewDonorDetailsCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(125, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewDonorDetailsGenderCombo, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(newEmployeeNameLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(newEmployeeDesignationLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewDonorDetailsNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewDonorDetailsBloodGroupText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewDonorDetailsNumberOfDonationsText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(newEmployeeAgeLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(newEmployeePhoneLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewDonorDetailsPhoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(viewDonorDetailsLastDonationText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(86, 86, 86)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newEmployeeDateOfJoiningLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newEmployeeEmailLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewDonorDetailsEmailText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(newEmployeeAddresslLable, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewDonorDetailsUpdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewDonorDetailsCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(95, 95, 95))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1016)/2, (screenSize.height-639)/2, 1016, 639);
    }// </editor-fold>//GEN-END:initComponents

    private void viewDonorDetailsUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDonorDetailsUpdateButtonActionPerformed
        try {
            String name = viewDonorDetailsNameText.getText();
            String gender = viewDonorDetailsGenderCombo.getSelectedItem().toString();
            String phone = viewDonorDetailsPhoneText.getText();
            String email = viewDonorDetailsEmailText.getText();
            String dateOfBirth = viewDonorDetailsDateOfBirthText.getText();
            String address = viewDonorDetailsAddressText.getText();
            if (name.equals("") || phone.equals("") || email.equals("") || address.equals("") || dateOfBirth.equals("")) 
            {
                JOptionPane.showMessageDialog(this, "All feilds are mandotory !!");
            } 
            else 
            {
                if (phone.length() < 10) 
                {
                    JOptionPane.showMessageDialog(this, "Phone number not valid");
                } 
                else 
                {
                    Connection con = DataBaseConnection.open();
                    Statement stmt = (Statement) con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("select * from donor where donor_id="+id);
                    rs.next();
                    rs.updateString("donor_name", name);
                    rs.updateString("donor_phone", phone);
                    rs.updateString("donor_email", email);
                    rs.updateString("donor_address", address);
                    rs.updateString("donor_dob", dateOfBirth);
                    rs.updateString("donor_gender", gender);
                    rs.updateRow();
                    DataBaseConnection.close(con);

                    JOptionPane.showMessageDialog(this, "Donor Data Updated successfully !!");

                }
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_viewDonorDetailsUpdateButtonActionPerformed

    private void viewDonorDetailsCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDonorDetailsCancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_viewDonorDetailsCancelButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Global.mainForm.setVisible(true);
        Global.currentForm.setVisible(true);
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
            java.util.logging.Logger.getLogger(ViewDonorDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewDonorDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewDonorDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewDonorDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ViewDonorDetails(1).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel newEmployeeAddresslLable;
    private javax.swing.JLabel newEmployeeAgeLable;
    private javax.swing.JLabel newEmployeeDateOfJoiningLable;
    private javax.swing.JLabel newEmployeeDesignationLable;
    private javax.swing.JLabel newEmployeeEmailLable;
    private javax.swing.JLabel newEmployeeNameLable;
    private javax.swing.JLabel newEmployeePhoneLable;
    private javax.swing.JTextArea viewDonorDetailsAddressText;
    private javax.swing.JTextField viewDonorDetailsBloodGroupText;
    private javax.swing.JButton viewDonorDetailsCancelButton;
    private javax.swing.JTextField viewDonorDetailsEmailText;
    private javax.swing.JComboBox viewDonorDetailsGenderCombo;
    private javax.swing.JTextField viewDonorDetailsLastDonationText;
    private javax.swing.JTextField viewDonorDetailsNameText;
    private javax.swing.JTextField viewDonorDetailsNumberOfDonationsText;
    private javax.swing.JTextField viewDonorDetailsPhoneText;
    private javax.swing.JButton viewDonorDetailsUpdateButton;
    // End of variables declaration//GEN-END:variables
}

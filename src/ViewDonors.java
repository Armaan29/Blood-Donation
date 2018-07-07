
import com.mysql.jdbc.Statement;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.table.AbstractTableModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pavilion
 */
public class ViewDonors extends javax.swing.JFrame {

    ArrayList<Donor> donorList;
    TableModle viewDonorsTableModle;
    ArrayList<Integer> listOfIds;
    boolean isFirstTime = false;
    public ViewDonors() {
        initComponents();
        viewDonorsFilterTypeCombo.addItem("");
        this.getContentPane().setBackground(new Color(240, 240, 240));
        Global.currentForm = this;
        donorList = new ArrayList<>();
        viewDonorsTableModle = new TableModle();
        generateList("All","");
        viewDonorsTable.setModel(viewDonorsTableModle);
        viewDonorsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        viewDonorsTable.getColumnModel().getColumn(1).setPreferredWidth(175);
        viewDonorsTable.getColumnModel().getColumn(2).setPreferredWidth(75);
        viewDonorsTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        viewDonorsTable.getColumnModel().getColumn(4).setPreferredWidth(75);
        viewDonorsTable.getColumnModel().getColumn(5).setPreferredWidth(125);
        viewDonorsTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        viewDonorsTable.getColumnModel().getColumn(7).setPreferredWidth(100);
        
        viewDonorsTable.setRowSelectionAllowed(false);
        viewDonorsTable.getColumn("Details").setCellRenderer(new ButtonRenderer());
        viewDonorsTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(),listOfIds,"donors"));
        
        viewDonorsTableScroll.getViewport().setOpaque(false);
        viewDonorsTableScroll.setViewportBorder(null);
        isFirstTime = true;
    }
    
    
    void generateList(String action,String type)
    {
        listOfIds = new ArrayList<>();
        donorList.clear();
        listOfIds.clear();
        try
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = null;
            switch(action)
            {
                case "All":
                    rs = stmt.executeQuery("select * from donor");
                    break;
                case "Blood Group":
                    rs = stmt.executeQuery("select * from donor where donor_blood_group='"+type+"'");
                    break;
                case "Gender":
                    rs = stmt.executeQuery("select * from donor where donor_gender='"+type+"'");
                    break;
                case "Last Donation":
                    if(type.equals("Old"))
                    {
                        rs = stmt.executeQuery("select * from donor order by donor_last_donation_date");
                    }
                    else
                    {
                        rs = stmt.executeQuery("select * from donor order by donor_last_donation_date desc");
                    }
                    break;
                    
            }
            
            int count = 1;
            while(rs.next())
            {
                listOfIds.add(rs.getInt("donor_id"));
                Donor d = new Donor(count,rs.getString("donor_name"),rs.getString("donor_gender"),rs.getString("donor_blood_group"),rs.getString("donor_phone"),getDate(rs.getTimestamp("donor_last_donation_date")),getAge(rs.getString("donor_dob")));  
                donorList.add(d);
                count++;
            }
            DataBaseConnection.close(con);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    String getDate(Timestamp stamp)
    {
        
        Date d = new Date(stamp.getTime());
        String date = new SimpleDateFormat("dd/MM/yyyy").format(d);
        return date;
    }
    
    int getAge(String dateOfBirth)
    {
        Calendar dob = Calendar.getInstance(); 
        Date d = null;
        try
        {
            d = new SimpleDateFormat("dd/MM/yyyy").parse(dateOfBirth);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        dob.setTime(d);  
        Calendar today = Calendar.getInstance();  
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
        if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) 
        {
            age--;  
        } 
        else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) 
        {
            age--;  
        }
        return age;
    }
    
    private class TableModle extends AbstractTableModel
    {
        String columnName[] = {"S.No.","Name","Gender","Age","Blood","Phone","Last Donation","Details"};
        @Override
        public int getRowCount() 
        {
            return donorList.size();
        }

        @Override
        public int getColumnCount() 
        {
            return 8;
        }
        
        public String getColumnName(int columnIndex)
        {
            
            return columnName[columnIndex];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) 
        {
            switch(columnIndex)
            {
                case 0:
                    return donorList.get(rowIndex).number;
                case 1:
                    return donorList.get(rowIndex).name;
                case 2:
                    return donorList.get(rowIndex).gender;
                case 3:
                    return donorList.get(rowIndex).age;
                case 4:
                    return donorList.get(rowIndex).bloodGroup;
                case 5:
                    return donorList.get(rowIndex).phone;
                case 6:
                    return donorList.get(rowIndex).lastDonationDate;
                case 7:
                        return "View";
            }
            return 0;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return columnIndex == 7;
        }
        
    }

   class Donor
   {
       String name,gender,bloodGroup,phone,lastDonationDate;
       int number,age;

        public Donor(int number, String name, String gender, String bloodGroup, String phone, String lastDonationDate, int age) 
        {
            this.name = name;
            this.gender = gender;
            this.bloodGroup = bloodGroup;
            this.phone = phone;
            this.lastDonationDate = lastDonationDate;
            this.number = number;
            this.age = age;
        }
        

   }
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        viewDonorsTableScroll = new javax.swing.JScrollPane();
        viewDonorsTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        viewDonorsFilterTypeCombo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        viewDonorsFilterCombo = new javax.swing.JComboBox();
        viewDonorsFilterGoButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Donors");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(400, 0, 200, 86);

        viewDonorsTableScroll.setBorder(null);

        viewDonorsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        viewDonorsTable.setRowHeight(30);
        viewDonorsTableScroll.setViewportView(viewDonorsTable);

        getContentPane().add(viewDonorsTableScroll);
        viewDonorsTableScroll.setBounds(102, 104, 800, 410);

        jLabel2.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel2.setText("Type");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(780, 30, 40, 30);

        viewDonorsFilterTypeCombo.setEnabled(false);
        getContentPane().add(viewDonorsFilterTypeCombo);
        viewDonorsFilterTypeCombo.setBounds(820, 30, 100, 30);

        jLabel3.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel3.setText("Filters");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(610, 30, 50, 30);

        viewDonorsFilterCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Blood Group", "Gender", "Last Donation" }));
        viewDonorsFilterCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                viewDonorsFilterComboItemStateChanged(evt);
            }
        });
        getContentPane().add(viewDonorsFilterCombo);
        viewDonorsFilterCombo.setBounds(660, 30, 115, 30);

        viewDonorsFilterGoButton.setText("Go");
        viewDonorsFilterGoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewDonorsFilterGoButtonActionPerformed(evt);
            }
        });
        getContentPane().add(viewDonorsFilterGoButton);
        viewDonorsFilterGoButton.setBounds(930, 30, 45, 30);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1016)/2, (screenSize.height-639)/2, 1016, 639);
    }// </editor-fold>//GEN-END:initComponents

    private void viewDonorsFilterComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_viewDonorsFilterComboItemStateChanged
        String action = viewDonorsFilterCombo.getSelectedItem().toString();
        viewDonorsFilterTypeCombo.removeAllItems();
        viewDonorsFilterTypeCombo.setEnabled(true);
        switch(action)
        {
            case "All":
                viewDonorsFilterTypeCombo.setEnabled(false);
                viewDonorsFilterTypeCombo.addItem("");
                break;
            case "Blood Group":
                viewDonorsFilterTypeCombo.addItem("A+");
                viewDonorsFilterTypeCombo.addItem("A-");
                viewDonorsFilterTypeCombo.addItem("B+");
                viewDonorsFilterTypeCombo.addItem("B-");
                viewDonorsFilterTypeCombo.addItem("O+");
                viewDonorsFilterTypeCombo.addItem("O-");
                viewDonorsFilterTypeCombo.addItem("AB+");
                viewDonorsFilterTypeCombo.addItem("AB-");
                break;
            case "Gender":
                viewDonorsFilterTypeCombo.addItem("Male");
                viewDonorsFilterTypeCombo.addItem("Female");
                break;
            case "Last Donation":
                viewDonorsFilterTypeCombo.addItem("Recent");
                viewDonorsFilterTypeCombo.addItem("Old");
                break;
        }
    }//GEN-LAST:event_viewDonorsFilterComboItemStateChanged

    private void viewDonorsFilterGoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDonorsFilterGoButtonActionPerformed
        String action = viewDonorsFilterCombo.getSelectedItem().toString();
        String type = viewDonorsFilterTypeCombo.getSelectedItem().toString();
        generateList(action, type);
        viewDonorsTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(),listOfIds,"donors"));
        viewDonorsTableModle.fireTableDataChanged();
    }//GEN-LAST:event_viewDonorsFilterGoButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Global.mainForm.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        if(!isFirstTime)
        {
            donorList.clear();
            generateList(viewDonorsFilterCombo.getSelectedItem().toString(),viewDonorsFilterTypeCombo.getSelectedItem().toString());
            viewDonorsTableModle.fireTableDataChanged();
        }
    }//GEN-LAST:event_formWindowGainedFocus

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        isFirstTime = false;
    }//GEN-LAST:event_formComponentHidden

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
            java.util.logging.Logger.getLogger(ViewDonors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewDonors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewDonors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewDonors.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ViewDonors().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox viewDonorsFilterCombo;
    private javax.swing.JButton viewDonorsFilterGoButton;
    private javax.swing.JComboBox viewDonorsFilterTypeCombo;
    private javax.swing.JTable viewDonorsTable;
    private javax.swing.JScrollPane viewDonorsTableScroll;
    // End of variables declaration//GEN-END:variables
}

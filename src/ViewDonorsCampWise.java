
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
public class ViewDonorsCampWise extends javax.swing.JFrame {

    ArrayList<DonorInCamp> donorList;
    TableModle viewDonorsTableModle;
    ArrayList<Integer> listOfIds;
    boolean isFirstTime = false;
    
    public ViewDonorsCampWise() {
        initComponents();
        
        this.getContentPane().setBackground(new Color(240, 240, 240));
        Global.currentForm = this;
        donorList = new ArrayList<>();
        viewDonorsNameCombo.addItem("");
        viewDonorsTableModle = new TableModle();
        generateList("","");
        viewDonorsTable.setModel(viewDonorsTableModle);
        viewDonorsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        viewDonorsTable.getColumnModel().getColumn(1).setPreferredWidth(175);
        viewDonorsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        viewDonorsTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        viewDonorsTable.getColumnModel().getColumn(4).setPreferredWidth(75);
        viewDonorsTable.getColumnModel().getColumn(5).setPreferredWidth(125);
        viewDonorsTable.getColumnModel().getColumn(6).setPreferredWidth(50);
        viewDonorsTable.getColumnModel().getColumn(7).setPreferredWidth(75);
        
        viewDonorsTable.setRowSelectionAllowed(false);
        viewDonorsTable.getColumn("Details").setCellRenderer(new ButtonRenderer());
        viewDonorsTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(),listOfIds,"donors"));
        
        viewDonorsTableScroll.getViewport().setOpaque(false);
        viewDonorsTableScroll.setViewportBorder(null);
        isFirstTime = true;
        
        try
            {
                Connection con = DataBaseConnection.open();
                java.sql.Statement stmt = (java.sql.Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("select camp_date from camp group by camp_date");
                while(rs.next())
                {
                    viewDonorsDateCombo.addItem(rs.getString("camp_date"));
                }
                DataBaseConnection.close(con);
                this.dispose();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        
    }
    
    
    
    void generateList(String date,String name)
    {
        listOfIds = new ArrayList<>();
        donorList.clear();
        listOfIds.clear();
        try
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = null;
            if(date.isEmpty())
            {
                rs = stmt.executeQuery("select donor_id from donors_in_camp");
            }
            else
            {
                Connection con1 = DataBaseConnection.open();
                Statement stmt1 = (Statement)con1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs1 = stmt1.executeQuery("select camp_id from camp where camp_date='"+date+"' and camp_name='"+name+"'");
                rs1.next();
                rs = stmt.executeQuery("select donor_id from donors_in_camp where camp_id="+rs1.getInt("camp_id"));
                DataBaseConnection.close(con1);
            }
            
            int count = 1;
            while(rs.next())
            {
                Connection con1 = DataBaseConnection.open();
                Statement stmt1 = (Statement)con1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs1 = stmt1.executeQuery("select * from donor where donor_id="+rs.getInt("donor_id"));
                rs1.next();
                listOfIds.add(rs.getInt("donor_id"));
                if(date.isEmpty())
                {
                    name = "Not Selected";
                    date = "--------";
                }
                DonorInCamp d = new DonorInCamp(count,name,date,rs1.getString("donor_name"),rs1.getString("donor_blood_group"),rs1.getString("donor_phone"),getAge(rs1.getString("donor_dob")));  
                donorList.add(d);
                count++;
                DataBaseConnection.close(con1);
            }
            DataBaseConnection.close(con);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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
        String columnName[] = {"S.No.","Camp Name","Date","Donor Name","Blood","Phone","Age","Details"};
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
                    return donorList.get(rowIndex).campName;
                case 2:
                    return donorList.get(rowIndex).campDate;
                case 3:
                    return donorList.get(rowIndex).donorName;
                case 4:
                    return donorList.get(rowIndex).bloodGroup;
                case 5:
                    return donorList.get(rowIndex).phone;
                case 6:
                    return donorList.get(rowIndex).age;
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

   class DonorInCamp
   {
       String campName,donorName,bloodGroup,phone,campDate;
       int number,age;

        public DonorInCamp(int number, String campName, String campDate, String donorName, String bloodGroup, String phone, int age) 
        {
            this.campName = campName;
            this.donorName = donorName;
            this.bloodGroup = bloodGroup;
            this.phone = phone;
            this.campDate = campDate;
            this.number = number;
            this.age = age;
        }
        

   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        viewDonorsTableScroll = new javax.swing.JScrollPane();
        viewDonorsTable = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        viewDonorsDateCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        viewDonorsNameCombo = new javax.swing.JComboBox();
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

        jLabel3.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel3.setText("Date");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(610, 30, 50, 30);

        viewDonorsDateCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Select--" }));
        viewDonorsDateCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                viewDonorsDateComboItemStateChanged(evt);
            }
        });
        getContentPane().add(viewDonorsDateCombo);
        viewDonorsDateCombo.setBounds(660, 30, 100, 30);

        jLabel2.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel2.setText("Name");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(770, 30, 40, 30);

        viewDonorsNameCombo.setEnabled(false);
        getContentPane().add(viewDonorsNameCombo);
        viewDonorsNameCombo.setBounds(810, 30, 120, 30);

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

    private void viewDonorsDateComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_viewDonorsDateComboItemStateChanged
        viewDonorsNameCombo.removeAllItems();
        String date = viewDonorsDateCombo.getSelectedItem().toString();
        if(date.equals("--Select--"))
        {
            viewDonorsNameCombo.addItem("");
            viewDonorsNameCombo.setEnabled(false);
        }
        else
        {
            try
            {
                Connection con = DataBaseConnection.open();
                java.sql.Statement stmt = (java.sql.Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("select camp_name from camp where camp_date='"+date+"'");
                while(rs.next())
                {
                    viewDonorsNameCombo.addItem(rs.getString("camp_name"));
                }
                DataBaseConnection.close(con);
                viewDonorsNameCombo.setEnabled(true);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_viewDonorsDateComboItemStateChanged

    private void viewDonorsFilterGoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewDonorsFilterGoButtonActionPerformed
        String date = viewDonorsDateCombo.getSelectedItem().toString();
        String name = viewDonorsNameCombo.getSelectedItem().toString();
        if(date.equals("--Select--"))
        {
            generateList("","");
        }
        else
        {
            generateList(date, name);
        }
        viewDonorsTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(), listOfIds, "donors"));
        viewDonorsTableModle.fireTableDataChanged();
    }//GEN-LAST:event_viewDonorsFilterGoButtonActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Global.mainForm.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        if(!isFirstTime)
        {
            
            donorList.clear();
            generateList(viewDonorsDateCombo.getSelectedItem().toString(),viewDonorsNameCombo.getSelectedItem().toString());
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
            java.util.logging.Logger.getLogger(ViewDonorsCampWise.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewDonorsCampWise.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewDonorsCampWise.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewDonorsCampWise.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ViewDonorsCampWise().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox viewDonorsDateCombo;
    private javax.swing.JButton viewDonorsFilterGoButton;
    private javax.swing.JComboBox viewDonorsNameCombo;
    private javax.swing.JTable viewDonorsTable;
    private javax.swing.JScrollPane viewDonorsTableScroll;
    // End of variables declaration//GEN-END:variables
}

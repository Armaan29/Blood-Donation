
import com.mysql.jdbc.Statement;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
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
public class ViewHospitals extends javax.swing.JFrame {
    ArrayList<Hospital> hospitalList;
    TableModle viewHospitalsTableModle;
    ArrayList<Integer> listOfIds;
    boolean isFirstTime = false;
    public ViewHospitals() {
        initComponents();
    this.getContentPane().setBackground(new Color(240, 240, 240));
        Global.currentForm = this;
        hospitalList = new ArrayList<>();
        viewHospitalsTableModle = new TableModle();
        generateList();
        viewHospitalsTable.setModel(viewHospitalsTableModle);
        viewHospitalsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        viewHospitalsTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        viewHospitalsTable.getColumnModel().getColumn(2).setPreferredWidth(275);
        viewHospitalsTable.getColumnModel().getColumn(3).setPreferredWidth(75);
        viewHospitalsTable.getColumnModel().getColumn(4).setPreferredWidth(75);
        viewHospitalsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        viewHospitalsTable.getColumnModel().getColumn(6).setPreferredWidth(75);
        
        viewHospitalsTable.setRowSelectionAllowed(false);
        viewHospitalsTable.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        viewHospitalsTable.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(),listOfIds,"hospitals"));
        
        viewHospitalsTableScroll.getViewport().setOpaque(false);
        viewHospitalsTableScroll.setViewportBorder(null);
        isFirstTime = true;
    }
    
    void generateList()
    {
        try
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from hospital order by hospital_name");
            int count = 1;
            listOfIds = new ArrayList<>();
            while(rs.next())
            {
                listOfIds.add(rs.getInt("hospital_id"));
                Hospital h = new Hospital(rs.getString("hospital_name"),rs.getString("hospital_address"),rs.getString("hospital_city"),rs.getString("hospital_state"), rs.getString("hospital_phone"),count);  
                hospitalList.add(h);
                count++;
            }
            DataBaseConnection.close(con);
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    private class TableModle extends AbstractTableModel
    {
        String columnName[] = {"S.No.","Name","Address","City","State","Phone","Edit"};
        @Override
        public int getRowCount() 
        {
            return hospitalList.size();
        }

        @Override
        public int getColumnCount() 
        {
            return 7;
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
                    return hospitalList.get(rowIndex).number;
                case 1:
                    return hospitalList.get(rowIndex).name;
                case 2:
                    return hospitalList.get(rowIndex).address;
                case 3:
                    return hospitalList.get(rowIndex).city;
                case 4:
                    return hospitalList.get(rowIndex).state;
                case 5:
                    return hospitalList.get(rowIndex).phone;
                case 6:
                        return "Edit";
            }
            return 0;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return columnIndex == 6;
        }
        
    }

   class Hospital
   {
       String name,address,city,state,phone;
       int number;

        public Hospital(String name, String address, String city, String state, String phone, int number) 
        {
            this.name = name;
            this.address = address;
            this.city = city;
            this.state = state;
            this.phone = phone;
            this.number = number;
        }
        
   }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        viewHospitalsTableScroll = new javax.swing.JScrollPane();
        viewHospitalsTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
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
        jLabel1.setText("Hospitals");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(309, 0, 377, 86);

        viewHospitalsTableScroll.setBorder(null);

        viewHospitalsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        viewHospitalsTable.setRowHeight(30);
        viewHospitalsTableScroll.setViewportView(viewHospitalsTable);

        getContentPane().add(viewHospitalsTableScroll);
        viewHospitalsTableScroll.setBounds(102, 104, 800, 410);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1016)/2, (screenSize.height-639)/2, 1016, 639);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Global.mainForm.setEnabled(true);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        if(!isFirstTime)
        {
            hospitalList.clear();
            generateList();
            viewHospitalsTable.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(),listOfIds,"hospitals"));
            viewHospitalsTableModle.fireTableDataChanged();
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
            java.util.logging.Logger.getLogger(ViewHospitals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewHospitals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewHospitals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewHospitals.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ViewHospitals().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTable viewHospitalsTable;
    private javax.swing.JScrollPane viewHospitalsTableScroll;
    // End of variables declaration//GEN-END:variables
}

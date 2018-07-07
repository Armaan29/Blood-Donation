
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
public class ViewSupply extends javax.swing.JFrame {

    /**
     * Creates new form ViewSupply
     */
    
    ArrayList<Supply> supplyList;
    TableModle viewSupplyTableModle;
    ArrayList<Integer> listOfIds;
    public ViewSupply() {
        initComponents();
        this.getContentPane().setBackground(new Color(240, 240, 240));
        Global.currentForm = this;
        supplyList = new ArrayList<>();
        viewSupplyTableModle = new TableModle();
        generateList();
        viewSupplyTable.setModel(viewSupplyTableModle);
        viewSupplyTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        viewSupplyTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        viewSupplyTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        viewSupplyTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        viewSupplyTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        viewSupplyTable.setRowSelectionAllowed(false);
        viewSupplyTable.getColumn("Details").setCellRenderer(new ButtonRenderer());
        viewSupplyTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(),listOfIds,"supply"));
        
        viewSupplyTableScroll.getViewport().setOpaque(false);
        viewSupplyTableScroll.setViewportBorder(null);
    }
    
    void generateList()
    {
        try
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from supply order by supply_date desc");
            int count = 1;
            listOfIds = new ArrayList<>();
            while(rs.next())
            {
                listOfIds.add(rs.getInt("supply_id"));
                Supply c = new Supply(count,getHospitalDetails(rs.getInt("hospital_id")), rs.getString("supply_date"), rs.getInt("supply_total_amount"));  
                supplyList.add(c);
                count++;
            }
            DataBaseConnection.close(con);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    String getHospitalDetails(int id)
    {
        try
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from hospital where hospital_id="+id);
            rs.next();
            String name = rs.getString("hospital_name");
            String city = rs.getString("hospital_city");
            String state = rs.getString("hospital_state");
            String hospital = name+", "+city+", "+state;
            DataBaseConnection.close(con);
            return hospital;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    private class TableModle extends AbstractTableModel
    {
        String columnName[] = {"S.No.","Hospital","Date","Supply","Details"};
        @Override
        public int getRowCount() 
        {
            return supplyList.size();
        }

        @Override
        public int getColumnCount() 
        {
            return 5;
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
                    return supplyList.get(rowIndex).number;
                case 1:
                    return supplyList.get(rowIndex).hospital;
                
                case 2:
                    return supplyList.get(rowIndex).date;
                case 3:
                    return supplyList.get(rowIndex).totalCollection;
                case 4:
                        return "View";
            }
            return 0;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return columnIndex == 4;
        }
        
    }

   class Supply
   {
       String hospital,date;
       int number,totalCollection;
        public Supply(int number, String hospital, String date, int totalCollection) 
        {
            this.number = number;
            this.hospital = hospital;
            this.date = date;
            this.totalCollection = totalCollection;
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
        viewSupplyTableScroll = new javax.swing.JScrollPane();
        viewSupplyTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Supplies");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(309, 0, 377, 86);

        viewSupplyTableScroll.setBorder(null);

        viewSupplyTable.setModel(new javax.swing.table.DefaultTableModel(
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
        viewSupplyTable.setRowHeight(30);
        viewSupplyTableScroll.setViewportView(viewSupplyTable);

        getContentPane().add(viewSupplyTableScroll);
        viewSupplyTableScroll.setBounds(102, 104, 800, 410);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1016)/2, (screenSize.height-639)/2, 1016, 639);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        Global.mainForm.setEnabled(true);
        Global.mainForm.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(ViewSupply.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewSupply.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewSupply.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewSupply.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ViewSupply().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTable viewSupplyTable;
    private javax.swing.JScrollPane viewSupplyTableScroll;
    // End of variables declaration//GEN-END:variables
}

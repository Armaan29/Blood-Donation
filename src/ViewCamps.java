
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
public class ViewCamps extends javax.swing.JFrame {

    ArrayList<Camp> campList;
    TableModle viewCampsTableModle;
    ArrayList<Integer> listOfIds;
    public ViewCamps() {
        initComponents();
        
        this.getContentPane().setBackground(new Color(240, 240, 240));
        Global.currentForm = this;
        campList = new ArrayList<>();
        viewCampsTableModle = new TableModle();
        generateList();
        viewCampsTable.setModel(viewCampsTableModle);
        viewCampsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        viewCampsTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        viewCampsTable.getColumnModel().getColumn(2).setPreferredWidth(350);
        viewCampsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        viewCampsTable.getColumnModel().getColumn(4).setPreferredWidth(75);
        viewCampsTable.getColumnModel().getColumn(5).setPreferredWidth(75);
        
        viewCampsTable.setRowSelectionAllowed(false);
        viewCampsTable.getColumn("Details").setCellRenderer(new ButtonRenderer());
        viewCampsTable.getColumn("Details").setCellEditor(new ButtonEditor(new JCheckBox(),listOfIds,"camps"));
        
        viewCampsTableScroll.getViewport().setOpaque(false);
        viewCampsTableScroll.setViewportBorder(null);
        
    }
    
    void generateList()
    {
        try
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from camp order by camp_date desc");
            int count = 1;
            listOfIds = new ArrayList<>();
            while(rs.next())
            {
                listOfIds.add(rs.getInt("camp_id"));
                Camp c = new Camp(count,rs.getString("camp_name"),rs.getString("camp_location")+", "+rs.getString("camp_city")+", "+rs.getString("camp_state"), rs.getString("camp_date"), rs.getInt("camp_total_amount"));  
                campList.add(c);
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
        String columnName[] = {"S.No.","Camp Name","Location","Date","Collection","Details"};
        @Override
        public int getRowCount() 
        {
            return campList.size();
        }

        @Override
        public int getColumnCount() 
        {
            return 6;
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
                    return campList.get(rowIndex).number;
                case 1:
                    return campList.get(rowIndex).name;
                case 2:
                    return campList.get(rowIndex).location;
                case 3:
                    return campList.get(rowIndex).date;
                case 4:
                    return campList.get(rowIndex).totalCollection;
                case 5:
                        return "View";
            }
            return 0;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex)
        {
            return columnIndex == 5;
        }
        
    }

   class Camp
   {
       String name,location,date;
       int number,totalCollection;
        public Camp(int number, String name, String location, String date, int totalCollection) 
        {
            this.number = number;
            this.name = name;
            this.location = location;
            this.date = date;
            this.totalCollection = totalCollection;
        }

   }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        viewCampsTableScroll = new javax.swing.JScrollPane();
        viewCampsTable = new javax.swing.JTable();

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
        jLabel1.setText("Camps Organized");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(309, 0, 377, 86);

        viewCampsTableScroll.setBorder(null);

        viewCampsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        viewCampsTable.setRowHeight(30);
        viewCampsTableScroll.setViewportView(viewCampsTable);

        getContentPane().add(viewCampsTableScroll);
        viewCampsTableScroll.setBounds(102, 104, 800, 410);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1016)/2, (screenSize.height-639)/2, 1016, 639);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(ViewCamps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewCamps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewCamps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewCamps.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ViewCamps().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTable viewCampsTable;
    private javax.swing.JScrollPane viewCampsTableScroll;
    // End of variables declaration//GEN-END:variables
}


import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pavilion
 */
public class ViewSupplyDetails extends javax.swing.JFrame {

    /**
     * Creates new form ViewSupplyDetails
     */
    public ViewSupplyDetails(int id)
    {
        initComponents();
        this.getContentPane().setBackground(Color.white);
        JLabel bloodCount[] = new JLabel[9];
        int yCordinate = 45;
        
        for(int i = 0;i<bloodCount.length;i++)
        {
            bloodCount[i] = new JLabel();
            bloodCount[i].setBounds(200,yCordinate+=45, 120, 30);
            bloodCount[i].setBorder(BorderFactory.createLineBorder(Color.black));
            bloodCount[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            this.add(bloodCount[i]);
        }
        int count = 0;
        try
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select supply_blood_group,supply_total_amount from supply where supply_id="+id);
            rs.next();
            
            
            String bloodCollection = rs.getString("supply_blood_group");
            int totalAmount = Integer.parseInt(rs.getString("supply_total_amount"));
            DataBaseConnection.close(con);
            StringTokenizer bloodGroupToken = new StringTokenizer(bloodCollection, ",");
            for(int i = 0;i<bloodCount.length-1;i++)
            {
                bloodCount[i].setText(bloodGroupToken.nextToken());
            }
            bloodCount[8].setText(""+totalAmount);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewCampDetailsNameText = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(null);

        viewCampDetailsNameText.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        viewCampDetailsNameText.setForeground(new java.awt.Color(255, 51, 51));
        viewCampDetailsNameText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        viewCampDetailsNameText.setText("Supply Details");
        getContentPane().add(viewCampDetailsNameText);
        viewCampDetailsNameText.setBounds(3, 0, 400, 72);

        jLabel1.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLabel1.setText("A - Positive");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(90, 90, 80, 30);

        jLabel2.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLabel2.setText("A - Negative");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(90, 135, 80, 30);

        jLabel3.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLabel3.setText("B - Positive");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(90, 180, 80, 30);

        jLabel4.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLabel4.setText("B - Negative");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(90, 225, 80, 30);

        jLabel5.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLabel5.setText("O - Positive");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(90, 270, 80, 30);

        jLabel6.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLabel6.setText("O - Negative");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(90, 315, 80, 30);

        jLabel7.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLabel7.setText("AB - Positive");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(90, 360, 80, 30);

        jLabel8.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        jLabel8.setText("AB - Negative");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(90, 405, 80, 30);

        jLabel9.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        jLabel9.setText("TOTAL");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(90, 450, 80, 30);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-416)/2, (screenSize.height-539)/2, 416, 539);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Global.mainForm.setVisible(true);
        Global.currentForm.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    
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
            java.util.logging.Logger.getLogger(ViewSupplyDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewSupplyDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewSupplyDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewSupplyDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ViewSupplyDetails(1).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel viewCampDetailsNameText;
    // End of variables declaration//GEN-END:variables
}

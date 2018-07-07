
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JProgressBar;
import javax.swing.Painter;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

public class WelcomeScreen extends javax.swing.JFrame implements Runnable
{
    public WelcomeScreen() 
    {
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        new Thread(this).start();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        welcomeScreenProgressBar = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        welcomeScreenInfoLable = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(null);
        getContentPane().add(welcomeScreenProgressBar);
        welcomeScreenProgressBar.setBounds(0, 367, 600, 33);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/welcome_screen.jpg"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 70, 600, 300);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(224, 0, 36));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Welcome");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(4, 4, 590, 60);

        welcomeScreenInfoLable.setForeground(new java.awt.Color(0, 63, 122));
        welcomeScreenInfoLable.setText("Loading modules");
        getContentPane().add(welcomeScreenInfoLable);
        welcomeScreenInfoLable.setBounds(10, 350, 590, 14);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
    }// </editor-fold>//GEN-END:initComponents

    
    public static void main(String args[]) {
       
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run()
            {
                new WelcomeScreen().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel welcomeScreenInfoLable;
    private javax.swing.JProgressBar welcomeScreenProgressBar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() 
    {
        int i = 1;
                while(i<=100)
                {
                    
                    welcomeScreenProgressBar.setValue(i);
                    i++;
                    try 
                    {
                        Thread.sleep(50);
                    } 
                    catch (Exception e) 
                    {
                        e.printStackTrace();
                    }
                }
                this.dispose();
                
                try 
                {
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) 
                    {
                        if ("Nimbus".equals(info.getName())) 
                        {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                }
                catch (Exception ex) 
                {
                    ex.printStackTrace();
                } 
                new LoginForm().setVisible(true);
    }
    
}

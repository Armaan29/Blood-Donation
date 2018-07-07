
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;


public class WorkingCamp extends javax.swing.JFrame 
{
    int campId;
    int donorId;
    DateTextField workingCampNewDonorDateOfBirthText;
    DateTextField workingCampExistingDonorDateOfBirthText;
    ArrayList<Donor> donorList;
    ArrayList<String> matchingDonors = new ArrayList<>();
    ArrayList<String> existingDonorNames = new ArrayList<>();
    StringBuffer nameTyped = new StringBuffer("");
    public WorkingCamp(int id) 
    {
        initComponents();
        workingCampNewDonorDateOfBirthText = new DateTextField();
        workingCampNewDonorDateOfBirthText.setBounds(340, 110, 160, 32);
        workingCampNewDonorPanel.add(workingCampNewDonorDateOfBirthText);
        
        workingCampExistingDonorDateOfBirthText = new DateTextField();
        workingCampExistingDonorDateOfBirthText.setBounds(380, 30, 160, 32);
        workingCampExistingDonorDateOfBirthText.addFocusListener(new FocusListener() 
        {
            public void focusGained(FocusEvent e) 
            {
                workingCampDonorNameSuggestionScrollPane.setVisible(false);
                workingCampExistingDonorNameText.setText("");
            }

            public void focusLost(FocusEvent e) 
            {
                String dateOfBirth = workingCampExistingDonorDateOfBirthText.getText();
                existingDonorNames.clear();
                try
                {
                    Connection con = DataBaseConnection.open();
                    Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("select donor_name,donor_blood_group from donor where donor_dob='"+dateOfBirth+"'");
                    while(rs.next())
                    {
                        existingDonorNames.add(rs.getString("donor_name").toUpperCase()+" ("+rs.getString("donor_blood_group")+")");
                    }
                    DataBaseConnection.close(con);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                nameTyped = new StringBuffer("");
            }
        });
        
        workingCampExistingDonorPanel.add(workingCampExistingDonorDateOfBirthText);
        
        workingCampDonorNameSuggestionScrollPane.setVisible(false);
        
        workingCampExistingDonorAddressText.setEditable(false);
        workingCampExistingDonorPhoneText.setEditable(false);
        workingCampExistingDonorLastDonationDateText.setEditable(false);
        
        workingCampExistingDonorNameText.addKeyListener(new KeyAdapter() 
        {
//            StringBuffer 
            public void keyTyped(KeyEvent e) 
            {
                
                char ch = e.getKeyChar();
                if(ch == '\b')
                {
                    if(!(nameTyped.length() == 0))
                    {
                        nameTyped.deleteCharAt(nameTyped.length()-1);
                    }
                }
                else if(ch == ' ')
                {
                    if(workingCampExistingDonorNameText.getText().equals(""))
                    {
                        e.consume();
                    }
                }
                else
                {
                    nameTyped.append((""+ch).toUpperCase());
                }
                matchingDonors.clear();
                for(int i = 0;i<existingDonorNames.size();i++)
                {
                    String donor = existingDonorNames.get(i);
//                    System.out.println(donor);
                    if(donor.startsWith(nameTyped.toString()))
                    {
//                        System.out.println(donor+"  added");
                        if(!workingCampDonorNameSuggestionScrollPane.isVisible())
                        {
                            workingCampDonorNameSuggestionScrollPane.setVisible(true);
                        }
                        matchingDonors.add(donor);
                    }
                }
                        workingCampExistingDonorSuggestionsList.setModel(new MyListModel());
            }
        });
        
        
        
        workingCampNewDonorPhoneText.addKeyListener(new KeyAdapter() 
        {
            public void keyTyped(KeyEvent e) 
            {
                char ch = e.getKeyChar();
                if(!isNumeric(ch) || workingCampNewDonorPhoneText.getText().length()>9)
                {
                    e.consume();
                }
            }
        });

        donorList = new ArrayList<>();
        if(Global.mainForm != null)
            Global.mainForm.setEnabled(false);
        
        campId = id;
        this.getContentPane().setBackground(Color.white);

        
        try
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from camp where camp_id="+id);
            rs.next();
            String name = rs.getString("camp_name");
            String location = rs.getString("camp_location");
            String city = rs.getString("camp_city");
            String state = rs.getString("camp_state");
            String date = rs.getString("camp_date");
            DataBaseConnection.close(con);
            workingCampCityText.setText(city);
            workingCampDateText.setText(date);
            workingCampStateText.setText(state);
            workingCampLocationText.setText(location);
            workingCampNameText.setText(name);
            getCollection();
            generateDonorList();
            new Thread(new UpdateDonorTable(workingCampNewDonorTable,workingCampNewDonorScrollPane)).start();
            new Thread(new UpdateDonorTable(workingCampExistingDonorTable,workingCampExistingDonorScrollPane)).start();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
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
    
    class MyListModel extends AbstractListModel<Object>
    {

        @Override
        public int getSize()
        {
            return matchingDonors.size();
        }

        @Override
        public String getElementAt(int index)
        {
            return matchingDonors.get(index);
        }
        
    }
    
    class Donor
    {
        int sNo;
        String name,bloodGroup;

        public Donor(int sNo,String name, String bloodGroup) 
        {
            this.sNo = sNo;
            this.name = name;
            this.bloodGroup = bloodGroup;
        }
        
    }
    
    void getCollection()
    {
        try
        {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select camp_blood_collection,camp_total_amount from camp where camp_id="+campId);
            rs.next();
            String bloodCollection = rs.getString("camp_blood_collection");
            int totalAmount = Integer.parseInt(rs.getString("camp_total_amount"));
            DataBaseConnection.close(con);
            StringTokenizer bloodGroupToken = new StringTokenizer(bloodCollection, ",");

            workingCampCollection_A_Positive.setText(bloodGroupToken.nextToken());
            workingCampCollection_A_Nagative.setText(bloodGroupToken.nextToken());
            workingCampCollection_B_Positive.setText(bloodGroupToken.nextToken());
            workingCampCollection_B_Nagative.setText(bloodGroupToken.nextToken());
            workingCampCollection_O_Positive.setText(bloodGroupToken.nextToken());
            workingCampCollection_O_Nagative.setText(bloodGroupToken.nextToken());
            workingCampCollection_AB_Positive.setText(bloodGroupToken.nextToken());
            workingCampCollection_AB_Nagative.setText(bloodGroupToken.nextToken());
            workingCampCollection_Total.setText(""+totalAmount);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        workingCampNameText = new javax.swing.JLabel();
        workingCampCityLabel = new javax.swing.JLabel();
        workingCampLocationText = new javax.swing.JLabel();
        workingCampLocationLabel = new javax.swing.JLabel();
        workingCampCityText = new javax.swing.JLabel();
        workingCampStateLabel = new javax.swing.JLabel();
        workingCampStateText = new javax.swing.JLabel();
        workingCampDateLabel = new javax.swing.JLabel();
        workingCampDateText = new javax.swing.JLabel();
        workingCampCollectionDetailsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        workingCampCollection_A_Positive = new javax.swing.JLabel();
        workingCampCollection_A_Nagative = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        workingCampCollection_B_Positive = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        workingCampCollection_B_Nagative = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        workingCampCollection_O_Positive = new javax.swing.JLabel();
        workingCampCollection_O_Nagative = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        workingCampCollection_AB_Positive = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        workingCampCollection_AB_Nagative = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        workingCampCollection_Total = new javax.swing.JLabel();
        WorkingCampDonnerTab = new javax.swing.JTabbedPane();
        workingCampNewDonorPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        workingCampNewDonorNameText = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        workingCampNewDonorGenderMale = new javax.swing.JRadioButton();
        workingCampNewDonorGenderFemale = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        workingCampNewDonorPhoneText = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        workingCampNewDonorAddressText = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        workingCampNewDonorEmailText = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        workingCampNewDonorBloodGroupCombo = new javax.swing.JComboBox();
        workingCampNewDonorSaveButton = new javax.swing.JButton();
        workingCampNewDonorScrollPane = new javax.swing.JScrollPane();
        workingCampNewDonorTable = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        workingCampExistingDonorPanel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        workingCampExistingDonorScrollPane = new javax.swing.JScrollPane();
        workingCampExistingDonorTable = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        workingCampExistingDonorNameText = new javax.swing.JTextField();
        workingCampDonorNameSuggestionScrollPane = new javax.swing.JScrollPane();
        workingCampExistingDonorSuggestionsList = new javax.swing.JList();
        workingCampExistingDonorGetButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        workingCampExistingDonorPhoneText = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        workingCampExistingDonorAddressText = new javax.swing.JTextArea();
        workingCampExistingDonorLastDonationDateText = new javax.swing.JTextField();
        workingCampExistingDonorDonateBloodButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        workingCampNameText.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        workingCampNameText.setForeground(new java.awt.Color(255, 51, 51));
        workingCampNameText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workingCampNameText.setText("Camp Name");

        workingCampCityLabel.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        workingCampCityLabel.setText("City");

        workingCampLocationText.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        workingCampLocationText.setText("jLabel3");
        workingCampLocationText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        workingCampLocationLabel.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        workingCampLocationLabel.setText("Location");

        workingCampCityText.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        workingCampCityText.setText("dsf");
        workingCampCityText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        workingCampStateLabel.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        workingCampStateLabel.setText("State");

        workingCampStateText.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        workingCampStateText.setText("dsf");
        workingCampStateText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        workingCampDateLabel.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        workingCampDateLabel.setText("Date");

        workingCampDateText.setFont(new java.awt.Font("Candara", 0, 14)); // NOI18N
        workingCampDateText.setText("dsf");
        workingCampDateText.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        workingCampCollectionDetailsPanel.setBackground(new java.awt.Color(255, 255, 255));
        workingCampCollectionDetailsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)), "Collection Details", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Candara", 1, 14), java.awt.Color.red)); // NOI18N
        workingCampCollectionDetailsPanel.setLayout(null);

        jLabel1.setText("A+");
        workingCampCollectionDetailsPanel.add(jLabel1);
        jLabel1.setBounds(42, 31, 29, 38);

        workingCampCollection_A_Positive.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workingCampCollection_A_Positive.setText("jLabel2");
        workingCampCollection_A_Positive.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampCollectionDetailsPanel.add(workingCampCollection_A_Positive);
        workingCampCollection_A_Positive.setBounds(77, 31, 66, 38);

        workingCampCollection_A_Nagative.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workingCampCollection_A_Nagative.setText("jLabel2");
        workingCampCollection_A_Nagative.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampCollectionDetailsPanel.add(workingCampCollection_A_Nagative);
        workingCampCollection_A_Nagative.setBounds(184, 31, 66, 38);

        jLabel4.setText("A-");
        workingCampCollectionDetailsPanel.add(jLabel4);
        jLabel4.setBounds(149, 31, 29, 38);

        workingCampCollection_B_Positive.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workingCampCollection_B_Positive.setText("jLabel2");
        workingCampCollection_B_Positive.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampCollectionDetailsPanel.add(workingCampCollection_B_Positive);
        workingCampCollection_B_Positive.setBounds(291, 31, 66, 38);

        jLabel6.setText("B+");
        workingCampCollectionDetailsPanel.add(jLabel6);
        jLabel6.setBounds(256, 31, 29, 38);

        workingCampCollection_B_Nagative.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workingCampCollection_B_Nagative.setText("jLabel2");
        workingCampCollection_B_Nagative.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampCollectionDetailsPanel.add(workingCampCollection_B_Nagative);
        workingCampCollection_B_Nagative.setBounds(398, 31, 66, 38);

        jLabel8.setText("B-");
        workingCampCollectionDetailsPanel.add(jLabel8);
        jLabel8.setBounds(363, 31, 29, 38);

        jLabel9.setText("O+");
        workingCampCollectionDetailsPanel.add(jLabel9);
        jLabel9.setBounds(470, 31, 29, 38);

        workingCampCollection_O_Positive.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workingCampCollection_O_Positive.setText("jLabel2");
        workingCampCollection_O_Positive.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampCollectionDetailsPanel.add(workingCampCollection_O_Positive);
        workingCampCollection_O_Positive.setBounds(505, 31, 66, 38);

        workingCampCollection_O_Nagative.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workingCampCollection_O_Nagative.setText("jLabel2");
        workingCampCollection_O_Nagative.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampCollectionDetailsPanel.add(workingCampCollection_O_Nagative);
        workingCampCollection_O_Nagative.setBounds(612, 31, 66, 38);

        jLabel12.setText("O-");
        workingCampCollectionDetailsPanel.add(jLabel12);
        jLabel12.setBounds(577, 31, 29, 38);

        workingCampCollection_AB_Positive.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workingCampCollection_AB_Positive.setText("jLabel2");
        workingCampCollection_AB_Positive.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampCollectionDetailsPanel.add(workingCampCollection_AB_Positive);
        workingCampCollection_AB_Positive.setBounds(719, 31, 66, 38);

        jLabel14.setText("AB+");
        workingCampCollectionDetailsPanel.add(jLabel14);
        jLabel14.setBounds(684, 31, 29, 38);

        jLabel15.setText("AB-");
        workingCampCollectionDetailsPanel.add(jLabel15);
        jLabel15.setBounds(791, 31, 29, 38);

        workingCampCollection_AB_Nagative.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workingCampCollection_AB_Nagative.setText("jLabel2");
        workingCampCollection_AB_Nagative.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampCollectionDetailsPanel.add(workingCampCollection_AB_Nagative);
        workingCampCollection_AB_Nagative.setBounds(826, 31, 66, 38);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Total Collection");
        workingCampCollectionDetailsPanel.add(jLabel2);
        jLabel2.setBounds(350, 100, 120, 32);

        workingCampCollection_Total.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        workingCampCollection_Total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        workingCampCollection_Total.setText("jLabel3");
        workingCampCollection_Total.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampCollectionDetailsPanel.add(workingCampCollection_Total);
        workingCampCollection_Total.setBounds(490, 100, 110, 30);

        WorkingCampDonnerTab.setFont(new java.awt.Font("Candara", 1, 14)); // NOI18N
        WorkingCampDonnerTab.setName("");

        workingCampNewDonorPanel.setBackground(new java.awt.Color(255, 255, 255));
        workingCampNewDonorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampNewDonorPanel.setLayout(null);

        jLabel3.setText("Name");
        workingCampNewDonorPanel.add(jLabel3);
        jLabel3.setBounds(250, 20, 45, 32);

        workingCampNewDonorNameText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                workingCampNewDonorNameTextKeyTyped(evt);
            }
        });
        workingCampNewDonorPanel.add(workingCampNewDonorNameText);
        workingCampNewDonorNameText.setBounds(340, 20, 160, 32);

        jLabel5.setText("Gender");
        workingCampNewDonorPanel.add(jLabel5);
        jLabel5.setBounds(250, 70, 45, 20);

        workingCampNewDonorGenderMale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(workingCampNewDonorGenderMale);
        workingCampNewDonorGenderMale.setSelected(true);
        workingCampNewDonorGenderMale.setText("Male");
        workingCampNewDonorPanel.add(workingCampNewDonorGenderMale);
        workingCampNewDonorGenderMale.setBounds(340, 70, 70, 23);

        workingCampNewDonorGenderFemale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(workingCampNewDonorGenderFemale);
        workingCampNewDonorGenderFemale.setText("Female");
        workingCampNewDonorPanel.add(workingCampNewDonorGenderFemale);
        workingCampNewDonorGenderFemale.setBounds(410, 70, 80, 23);

        jLabel7.setText("Phone");
        workingCampNewDonorPanel.add(jLabel7);
        jLabel7.setBounds(570, 20, 69, 32);
        workingCampNewDonorPanel.add(workingCampNewDonorPhoneText);
        workingCampNewDonorPhoneText.setBounds(680, 20, 174, 32);

        jLabel10.setText("Address");
        workingCampNewDonorPanel.add(jLabel10);
        jLabel10.setBounds(250, 170, 70, 32);

        workingCampNewDonorAddressText.setColumns(19);
        workingCampNewDonorAddressText.setLineWrap(true);
        workingCampNewDonorAddressText.setRows(4);
        jScrollPane1.setViewportView(workingCampNewDonorAddressText);

        workingCampNewDonorPanel.add(jScrollPane1);
        jScrollPane1.setBounds(340, 150, 158, 90);

        jLabel11.setText("Date Of Birth");
        workingCampNewDonorPanel.add(jLabel11);
        jLabel11.setBounds(250, 110, 80, 32);

        jLabel13.setText("Email");
        workingCampNewDonorPanel.add(jLabel13);
        jLabel13.setBounds(570, 60, 69, 32);
        workingCampNewDonorPanel.add(workingCampNewDonorEmailText);
        workingCampNewDonorEmailText.setBounds(680, 60, 174, 32);

        jLabel16.setText("Blood Group");
        workingCampNewDonorPanel.add(jLabel16);
        jLabel16.setBounds(570, 110, 69, 32);

        workingCampNewDonorBloodGroupCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Select--", "A-Positive", "A-Negetive", "B-Positive", "B-Negetive", "O-Positive", "O-Negetive", "AB-Positive", "AB-Negetive" }));
        workingCampNewDonorPanel.add(workingCampNewDonorBloodGroupCombo);
        workingCampNewDonorBloodGroupCombo.setBounds(680, 110, 170, 30);

        workingCampNewDonorSaveButton.setText("Save");
        workingCampNewDonorSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                workingCampNewDonorSaveButtonActionPerformed(evt);
            }
        });
        workingCampNewDonorPanel.add(workingCampNewDonorSaveButton);
        workingCampNewDonorSaveButton.setBounds(640, 170, 100, 50);

        workingCampNewDonorScrollPane.setBorder(null);

        workingCampNewDonorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        workingCampNewDonorScrollPane.setViewportView(workingCampNewDonorTable);
        workingCampNewDonorTable.getColumnModel().getColumn(0).setResizable(false);
        workingCampNewDonorTable.getColumnModel().getColumn(1).setResizable(false);
        workingCampNewDonorTable.getColumnModel().getColumn(2).setResizable(false);

        workingCampNewDonorPanel.add(workingCampNewDonorScrollPane);
        workingCampNewDonorScrollPane.setBounds(30, 30, 200, 210);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("Recent Donors");
        workingCampNewDonorPanel.add(jLabel17);
        jLabel17.setBounds(30, 4, 100, 20);

        WorkingCampDonnerTab.addTab("New Donner", workingCampNewDonorPanel);

        workingCampExistingDonorPanel.setBackground(new java.awt.Color(255, 255, 255));
        workingCampExistingDonorPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        workingCampExistingDonorPanel.setLayout(null);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Recent Donors");
        workingCampExistingDonorPanel.add(jLabel18);
        jLabel18.setBounds(30, 4, 100, 20);

        workingCampExistingDonorScrollPane.setBorder(null);

        workingCampExistingDonorTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        workingCampExistingDonorScrollPane.setViewportView(workingCampExistingDonorTable);
        workingCampExistingDonorTable.getColumnModel().getColumn(0).setResizable(false);
        workingCampExistingDonorTable.getColumnModel().getColumn(1).setResizable(false);
        workingCampExistingDonorTable.getColumnModel().getColumn(2).setResizable(false);

        workingCampExistingDonorPanel.add(workingCampExistingDonorScrollPane);
        workingCampExistingDonorScrollPane.setBounds(30, 30, 200, 210);

        jLabel19.setText("Date Of Birth");
        workingCampExistingDonorPanel.add(jLabel19);
        jLabel19.setBounds(290, 30, 80, 32);

        jLabel20.setText("Name");
        workingCampExistingDonorPanel.add(jLabel20);
        jLabel20.setBounds(290, 80, 45, 32);
        workingCampExistingDonorPanel.add(workingCampExistingDonorNameText);
        workingCampExistingDonorNameText.setBounds(380, 80, 160, 32);

        workingCampExistingDonorSuggestionsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        workingCampExistingDonorSuggestionsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                workingCampExistingDonorSuggestionsListMouseClicked(evt);
            }
        });
        workingCampDonorNameSuggestionScrollPane.setViewportView(workingCampExistingDonorSuggestionsList);

        workingCampExistingDonorPanel.add(workingCampDonorNameSuggestionScrollPane);
        workingCampDonorNameSuggestionScrollPane.setBounds(380, 110, 160, 110);

        workingCampExistingDonorGetButton.setText("Get Donor");
        workingCampExistingDonorGetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                workingCampExistingDonorGetButtonActionPerformed(evt);
            }
        });
        workingCampExistingDonorPanel.add(workingCampExistingDonorGetButton);
        workingCampExistingDonorGetButton.setBounds(340, 170, 140, 40);

        jPanel1.setLayout(null);

        jLabel22.setText("Phone");
        jPanel1.add(jLabel22);
        jLabel22.setBounds(20, 30, 80, 26);

        jLabel23.setText("Address");
        jPanel1.add(jLabel23);
        jLabel23.setBounds(20, 90, 80, 27);

        jLabel24.setText("Last Donation");
        jPanel1.add(jLabel24);
        jLabel24.setBounds(20, 150, 80, 29);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setText("Donor Details");
        jPanel1.add(jLabel25);
        jLabel25.setBounds(10, 6, 82, 14);
        jPanel1.add(workingCampExistingDonorPhoneText);
        workingCampExistingDonorPhoneText.setBounds(110, 30, 123, 28);

        workingCampExistingDonorAddressText.setColumns(20);
        workingCampExistingDonorAddressText.setLineWrap(true);
        workingCampExistingDonorAddressText.setRows(3);
        jScrollPane2.setViewportView(workingCampExistingDonorAddressText);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(110, 70, 123, 70);
        jPanel1.add(workingCampExistingDonorLastDonationDateText);
        workingCampExistingDonorLastDonationDateText.setBounds(110, 150, 123, 29);

        workingCampExistingDonorDonateBloodButton.setText("Donate Blood");
        workingCampExistingDonorDonateBloodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                workingCampExistingDonorDonateBloodButtonActionPerformed(evt);
            }
        });
        jPanel1.add(workingCampExistingDonorDonateBloodButton);
        workingCampExistingDonorDonateBloodButton.setBounds(60, 190, 160, 30);

        workingCampExistingDonorPanel.add(jPanel1);
        jPanel1.setBounds(610, 10, 270, 230);

        WorkingCampDonnerTab.addTab("Existing Donner", workingCampExistingDonorPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(WorkingCampDonnerTab, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(workingCampCollectionDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(workingCampLocationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(workingCampLocationText, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(workingCampCityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(workingCampCityText, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(workingCampStateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(workingCampStateText, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(38, 38, 38)
                            .addComponent(workingCampDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(workingCampDateText, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(174, 174, 174)
                            .addComponent(workingCampNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(workingCampNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(workingCampLocationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(workingCampLocationText, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(workingCampCityLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(workingCampCityText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(workingCampStateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(workingCampStateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(workingCampDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(workingCampDateText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(workingCampCollectionDetailsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(WorkingCampDonnerTab, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1016)/2, (screenSize.height-639)/2, 1016, 639);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(Global.mainForm != null)
        Global.mainForm.setEnabled(true);
    }//GEN-LAST:event_formWindowClosing

    private void workingCampNewDonorSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_workingCampNewDonorSaveButtonActionPerformed
        String name = workingCampNewDonorNameText.getText();
        String gender = null;
        if(workingCampNewDonorGenderMale.isSelected())
        {
            gender = "Male";
        }
        else
        {
            gender = "Female";
        }
        String dateOfBirth = workingCampNewDonorDateOfBirthText.getText();
        String address = workingCampNewDonorAddressText.getText();
        String phone = workingCampNewDonorPhoneText.getText();
        String email = workingCampNewDonorEmailText.getText();
        int bloodGroupNumber = workingCampNewDonorBloodGroupCombo.getSelectedIndex();
        
        
        if(name.isEmpty() || dateOfBirth.isEmpty() || address.isEmpty() || phone.isEmpty() || email.isEmpty() || bloodGroupNumber == 0)
        {
            JOptionPane.showMessageDialog(workingCampNewDonorPanel, "All feilds are mandatory !!");
        }
        else if(getAge(dateOfBirth)<18)
        {
            JOptionPane.showMessageDialog(this, "Age not valid less than 18");
        }
        else
        {
            try
            {
                boolean isNotExist = true;
                String bloodGroup = null;
                switch(bloodGroupNumber)
                {
                    case 1:
                        bloodGroup = "A+";
                        break;
                    case 2:
                        bloodGroup = "A-";
                        break;
                    case 3:
                        bloodGroup = "B+";
                        break;
                    case 4:
                        bloodGroup = "B-";
                        break;
                    case 5:
                        bloodGroup = "O+";
                        break;
                    case 6:
                        bloodGroup = "O-";
                        break;
                    case 7:
                        bloodGroup = "AB+";
                        break;
                    case 8:
                        bloodGroup = "AB-";
                        break;
                }
                Connection testCon = DataBaseConnection.open();
                Statement testStmt = (Statement)testCon.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet testRs = testStmt.executeQuery("select * from donor where donor_name='"+name+"' and donor_dob='"+dateOfBirth+"' and donor_blood_group='"+bloodGroup+"'");
                if(testRs.next())
                {
                    JOptionPane.showMessageDialog(workingCampNewDonorPanel, "Entry already exist !!");
                    isNotExist = false;
                }
                DataBaseConnection.close(testCon);
                if(isNotExist)
                {
                    Connection con = DataBaseConnection.open();
                    Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = stmt.executeQuery("select * from donor");
                    rs.moveToInsertRow();
                    rs.updateString("donor_name", name);
                    rs.updateString("donor_phone", phone);
                    rs.updateString("donor_email", email);
                    rs.updateString("donor_address", address);
                    rs.updateString("donor_dob", dateOfBirth);
                    rs.updateString("donor_gender", gender);
                    rs.updateString("donor_blood_group", bloodGroup);
                    rs.updateInt("donor_total_donations", 1);
                    rs.insertRow();
                    rs.last();
                    int donorId = rs.getInt("donor_id");
                    DataBaseConnection.close(con);
                    
                    Connection con2 = DataBaseConnection.open();
                    Statement stmt2 = (Statement)con2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs2 = stmt2.executeQuery("select * from donors_in_camp");
                    rs2.moveToInsertRow();
                    rs2.updateInt("camp_id", campId);
                    rs2.updateInt("donor_id", donorId);
                    rs2.insertRow();
                    DataBaseConnection.close(con2);
                    generateDonorList();
                    new Thread(new UpdateDonorTable(workingCampNewDonorTable,workingCampNewDonorScrollPane)).start();
                    new Thread(new UpdateDonorTable(workingCampExistingDonorTable,workingCampExistingDonorScrollPane)).start();
                    new Thread(new DataSetBloodCollection(bloodGroup)).start();
                    JOptionPane.showMessageDialog(workingCampNewDonorPanel, "Entery Saved !!");
                    clearEntry();
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_workingCampNewDonorSaveButtonActionPerformed

    private void workingCampExistingDonorSuggestionsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_workingCampExistingDonorSuggestionsListMouseClicked
        workingCampExistingDonorNameText.setText(workingCampExistingDonorSuggestionsList.getSelectedValue().toString());
        workingCampDonorNameSuggestionScrollPane.setVisible(false);
    }//GEN-LAST:event_workingCampExistingDonorSuggestionsListMouseClicked

    private void workingCampExistingDonorGetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_workingCampExistingDonorGetButtonActionPerformed
        String date = workingCampExistingDonorDateOfBirthText.getText();
        String nameData = workingCampExistingDonorNameText.getText();
        
        if(!(date.isEmpty() || nameData.isEmpty()))
        {
            StringTokenizer st = new StringTokenizer(nameData, "(");
            String name = st.nextToken();
            String bloodGroupData = st.nextToken();
            String bloodGroup = new StringBuffer(bloodGroupData).deleteCharAt(bloodGroupData.length()-1).toString();
            name = name.trim();
            try 
            {
                Connection con = DataBaseConnection.open();
                Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("select * from donor where donor_name='"+name+"' and donor_dob='"+date+"' and donor_blood_group='"+bloodGroup+"'");
                rs.next();
                workingCampExistingDonorPhoneText.setText(rs.getString("donor_phone"));
                workingCampExistingDonorAddressText.setText(rs.getString("donor_address"));
                donorId = rs.getInt("donor_id");
                Timestamp stamp = rs.getTimestamp("donor_last_donation_date");
                Date d = new Date(stamp.getTime());
                String dateOfDonation = new SimpleDateFormat("dd/MM/yyyy").format(d);
                workingCampExistingDonorLastDonationDateText.setText(dateOfDonation);
                
                DataBaseConnection.close(con);
            }
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
//        workingCampExistingDonorNameText.setText("");
    }//GEN-LAST:event_workingCampExistingDonorGetButtonActionPerformed

    private void workingCampExistingDonorDonateBloodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_workingCampExistingDonorDonateBloodButtonActionPerformed
       try
       {
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from donor where donor_id="+donorId);
            rs.next();
            rs.updateInt("donor_total_donations", rs.getInt("donor_total_donations")+1);
            rs.updateTimestamp("donor_last_donation_date", new Timestamp(System.currentTimeMillis()));
            String bloodGroup = rs.getString("donor_blood_group");
            rs.updateRow();
            DataBaseConnection.close(con);

            Connection con2 = DataBaseConnection.open();
            Statement stmt2 = (Statement)con2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs2 = stmt2.executeQuery("select * from donors_in_camp");
            rs2.moveToInsertRow();
            rs2.updateInt("camp_id", campId);
            rs2.updateInt("donor_id", donorId);
            rs2.insertRow();
            DataBaseConnection.close(con2);
            generateDonorList();
            new Thread(new UpdateDonorTable(workingCampNewDonorTable,workingCampNewDonorScrollPane)).start();
            new Thread(new UpdateDonorTable(workingCampExistingDonorTable,workingCampExistingDonorScrollPane)).start();
            new Thread(new DataSetBloodCollection(bloodGroup)).start();
            JOptionPane.showMessageDialog(workingCampNewDonorPanel, "Entery Saved !!");
            clearExistingEntry();
            
       }
       catch (Exception ex)
       {
           ex.printStackTrace();
        }
    }//GEN-LAST:event_workingCampExistingDonorDonateBloodButtonActionPerformed

    private void workingCampNewDonorNameTextKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_workingCampNewDonorNameTextKeyTyped
        int ch = evt.getKeyChar();
        if(!isAlphabet(ch))
        {
            evt.consume();
        }
    }//GEN-LAST:event_workingCampNewDonorNameTextKeyTyped

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
    
    
    boolean isAlphabet(int ch)
    {
        if((ch>=65 && ch<=90) || (ch>=97 && ch<=122))
        {
            return true;
        }
        if(ch == 8)
        {
            return true;
        }
        if(ch == '\t')
        {
            return true;
        }
        if(ch == 32)
        {
            return true;
        }
        return false;
    }
    
    void generateDonorList()
    {
        try 
        {
            int count = 1;
            donorList.clear();
            Connection con = DataBaseConnection.open();
            Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select donor_id from donors_in_camp where camp_id="+campId);
            while(rs.next())
            {
                int donorId = rs.getInt("donor_id");
                Connection con1 = DataBaseConnection.open();
                Statement stmt1 = (Statement)con1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs1 = stmt1.executeQuery("select * from donor where donor_id="+donorId);
                rs1.next();
                String name = rs1.getString("donor_name");
                String bloodGroup = rs1.getString("donor_blood_group");
                donorList.add(new Donor(count++, name, bloodGroup));
                DataBaseConnection.close(con1);
            }
            DataBaseConnection.close(con);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    void clearEntry()
    {
        workingCampNewDonorNameText.setText("");
        workingCampNewDonorGenderMale.setSelected(true);
        workingCampNewDonorDateOfBirthText.setText("");
        workingCampNewDonorAddressText.setText("");
        workingCampNewDonorPhoneText.setText("");
        workingCampNewDonorEmailText.setText("");
        workingCampNewDonorBloodGroupCombo.setSelectedIndex(0);
    }
    
    void clearExistingEntry()
    {
        workingCampExistingDonorNameText.setText("");
        workingCampExistingDonorDateOfBirthText.setText("");
        workingCampExistingDonorAddressText.setText("");
        workingCampExistingDonorPhoneText.setText("");
        workingCampExistingDonorLastDonationDateText.setText("");
        
    }
    
    class UpdateDonorTable implements Runnable
    {
        TableModle donorTableModel;
        JTable currentTable;
        JScrollPane currentScrollPane;

        public UpdateDonorTable(JTable currentTable, JScrollPane currentScrollPane) 
        {
            this.currentTable = currentTable;
            this.currentScrollPane = currentScrollPane;
        }
        
        public void run() 
        {
            donorTableModel = new TableModle();
            currentScrollPane.getViewport().setOpaque(false);
            currentScrollPane.setViewportBorder(null);
            currentTable.setModel(donorTableModel);
            
            currentTable.getColumnModel().getColumn(0).setPreferredWidth(45);
            currentTable.getColumnModel().getColumn(1).setPreferredWidth(105);
            currentTable.getColumnModel().getColumn(2).setPreferredWidth(50);

            JViewport viewport = (JViewport)currentTable.getParent();
            Rectangle rect = currentTable.getCellRect(currentTable.getRowCount()-1, 0, true);
            viewport.scrollRectToVisible(rect);
            Point p = viewport.getViewPosition();
            viewport.setViewPosition(new Point(p.x, p.y+16));
            
        }
    }
    
    class DataSetBloodCollection implements Runnable
    {
        String bloodGroup;
        public DataSetBloodCollection(String bloodGroup) 
        {
            this.bloodGroup = bloodGroup;
        }
        
        public void run() 
        {
            try 
            {
                Connection con = DataBaseConnection.open();
                Statement stmt = (Statement)con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("select * from camp where camp_id="+campId);
                rs.next();
                String bloodGroupCollection = rs.getString("camp_blood_collection");
                int totalCollection = rs.getInt("camp_total_amount");
                
                StringTokenizer st = new StringTokenizer(bloodGroupCollection, ",");
                int group[] = new int[8];
                for(int i = 0;i<group.length;i++)
                {
                    group[i] = Integer.parseInt(st.nextToken());
                }
                
                Connection con1 = DataBaseConnection.open();
                Statement stmt1 = (Statement)con1.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs1 = stmt1.executeQuery("select * from blood_bank");
                do
                {
                    rs1.next();
                }while(!(rs1.getString("blood_group").equals(bloodGroup)));
                
                rs1.updateInt("blood_count", rs1.getInt("blood_count")+1);
                rs1.updateRow();
                switch(bloodGroup)
                {
                    case "A+":
                        group[0]++;
                        break;
                    case "A-":
                        group[1]++;
                        break;
                    case "B+":
                        group[2]++;
                        break;
                    case "B-":
                        group[3]++;
                        break;
                    case "O+":
                        group[4]++;
                        break;
                    case "O-":
                        group[5]++;
                        break;
                    case "AB+":
                        group[6]++;
                        break;
                    case "AB-":
                        group[7]++;
                        break;
                }
                
                String newBloodGroupCollection = "";
                for(int i = 0;i<group.length;i++)
                {
                    newBloodGroupCollection += group[i];
                    if(i<group.length-1)
                    {
                        newBloodGroupCollection += ",";
                    }
                }
                rs.updateString("camp_blood_collection", newBloodGroupCollection);
                rs.updateInt("camp_total_amount", totalCollection+1);
                
                rs.updateRow();
                DataBaseConnection.close(con);
                getCollection();
            }
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        
        }
        
    }
    
    private class TableModle extends AbstractTableModel
    {
        String columnName[] = {"S.No.","Name","Group"};
        @Override
        public int getRowCount() 
        {
            return donorList.size();
        }

        @Override
        public int getColumnCount() 
        {
            return 3;
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
                    return donorList.get(rowIndex).sNo;
                case 1:
                    return donorList.get(rowIndex).name;
                case 2:
                    return donorList.get(rowIndex).bloodGroup;
            }
            return 0;
        }
        
    }
    
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
            java.util.logging.Logger.getLogger(WorkingCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WorkingCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WorkingCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WorkingCamp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new WorkingCamp(1).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane WorkingCampDonnerTab;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel workingCampCityLabel;
    private javax.swing.JLabel workingCampCityText;
    private javax.swing.JPanel workingCampCollectionDetailsPanel;
    private javax.swing.JLabel workingCampCollection_AB_Nagative;
    private javax.swing.JLabel workingCampCollection_AB_Positive;
    private javax.swing.JLabel workingCampCollection_A_Nagative;
    private javax.swing.JLabel workingCampCollection_A_Positive;
    private javax.swing.JLabel workingCampCollection_B_Nagative;
    private javax.swing.JLabel workingCampCollection_B_Positive;
    private javax.swing.JLabel workingCampCollection_O_Nagative;
    private javax.swing.JLabel workingCampCollection_O_Positive;
    private javax.swing.JLabel workingCampCollection_Total;
    private javax.swing.JLabel workingCampDateLabel;
    private javax.swing.JLabel workingCampDateText;
    private javax.swing.JScrollPane workingCampDonorNameSuggestionScrollPane;
    private javax.swing.JTextArea workingCampExistingDonorAddressText;
    private javax.swing.JButton workingCampExistingDonorDonateBloodButton;
    private javax.swing.JButton workingCampExistingDonorGetButton;
    private javax.swing.JTextField workingCampExistingDonorLastDonationDateText;
    private javax.swing.JTextField workingCampExistingDonorNameText;
    private javax.swing.JPanel workingCampExistingDonorPanel;
    private javax.swing.JTextField workingCampExistingDonorPhoneText;
    private javax.swing.JScrollPane workingCampExistingDonorScrollPane;
    private javax.swing.JList workingCampExistingDonorSuggestionsList;
    private javax.swing.JTable workingCampExistingDonorTable;
    private javax.swing.JLabel workingCampLocationLabel;
    private javax.swing.JLabel workingCampLocationText;
    private javax.swing.JLabel workingCampNameText;
    private javax.swing.JTextArea workingCampNewDonorAddressText;
    private javax.swing.JComboBox workingCampNewDonorBloodGroupCombo;
    private javax.swing.JTextField workingCampNewDonorEmailText;
    private javax.swing.JRadioButton workingCampNewDonorGenderFemale;
    private javax.swing.JRadioButton workingCampNewDonorGenderMale;
    private javax.swing.JTextField workingCampNewDonorNameText;
    private javax.swing.JPanel workingCampNewDonorPanel;
    private javax.swing.JTextField workingCampNewDonorPhoneText;
    private javax.swing.JButton workingCampNewDonorSaveButton;
    private javax.swing.JScrollPane workingCampNewDonorScrollPane;
    private javax.swing.JTable workingCampNewDonorTable;
    private javax.swing.JLabel workingCampStateLabel;
    private javax.swing.JLabel workingCampStateText;
    // End of variables declaration//GEN-END:variables
}

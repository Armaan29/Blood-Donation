
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class ButtonEditor extends DefaultCellEditor
  {
    private JButton button;
    private String label;
    private boolean clicked;
    private int row, col;
    private JTable table;
    ArrayList<Integer> listOfIds;
    String type;
    public ButtonEditor(JCheckBox checkBox,ArrayList<Integer> listOfIds,String type)
    {
      super(checkBox);
      button = new JButton();
      button.setOpaque(true);
      button.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          fireEditingStopped();
        }
      });
      this.listOfIds = listOfIds;
      this.type = type;
    }
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
      this.table = table;
      this.row = row;
      this.col = column;

      button.setForeground(Color.black);
      button.setBackground(UIManager.getColor("Button.background"));
      label = (value == null) ? "" : value.toString();
      button.setText(label);
      clicked = true;
      return button;
    }
    public Object getCellEditorValue()
    {
      if (clicked)
      {
          int i = Integer.parseInt(""+(table.getValueAt(row, 0)));
          Global.currentForm.setVisible(false);
          Global.mainForm.setVisible(true);

          if(type.equals("camps"))
          {
                new ViewCampDetails(listOfIds.get(i-1)).setVisible(true);
          }
          else if(type.equals("donors"))
          {
                new ViewDonorDetails(listOfIds.get(i-1)).setVisible(true);
          }
          else if(type.equals("hospitals"))
          {
              new EditHospitalDetails(listOfIds.get(i-1)).setVisible(true);
          }
          else if(type.equals("supply"))
          {
              new ViewSupplyDetails(listOfIds.get(i-1)).setVisible(true);
          }
      }
      clicked = false;
      return new String(label);
    }

    public boolean stopCellEditing()
    {
      clicked = false;
      return super.stopCellEditing();
    }

    protected void fireEditingStopped()
    {
      super.fireEditingStopped();
    }
  }
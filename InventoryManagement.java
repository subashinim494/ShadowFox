
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class InventoryItem {
    int id;
    String name;
    int quantity;
    double price;

    public InventoryItem(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}

public class InventoryManagement extends JFrame {
    private ArrayList<InventoryItem> inventory = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField idField, nameField, quantityField, priceField;

    public InventoryManagement() {
        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

       
        String[] columnNames = {"ID", "Name", "Quantity", "Price"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

       
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);
        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);
        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

      
        addButton.addActionListener(e -> addItem());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());

        setVisible(true);
    }

    private void addItem() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            
            
          if (id <= 0) throw new IllegalArgumentException("ID must be a positive number.");
          if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
          if (!name.matches("[a-zA-Z ]+")) throw new IllegalArgumentException("Name must contain only letters and spaces.");
          if (quantity < 0) throw new IllegalArgumentException ("Quantity must be zero or positive.");
          if (price < 0) throw new IllegalArgumentException("Price must be zero or positive.");

            

            inventory.add(new InventoryItem(id, name, quantity, price));
            tableModel.addRow(new Object[]{id, name, quantity, price});
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID, Quantity and Price must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String name = nameField.getText().trim();
                int quantity = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());

                
                
              if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
              if (!name.matches("[a-zA-Z ]+")) throw new IllegalArgumentException("Name must contain only letters and spaces.");
              if (quantity < 0) throw new IllegalArgumentException("Quantity must be zero or positive.");
              if (price < 0) throw new IllegalArgumentException("Price must be zero or positive.");


                InventoryItem item = inventory.get(selectedRow);
                item.name = name;
                item.quantity = quantity;
                item.price = price;

                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(quantity, selectedRow, 2);
                tableModel.setValueAt(price, selectedRow, 3);
                clearFields();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Quantity and Price must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to update", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            inventory.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        new InventoryManagement();
    }
}

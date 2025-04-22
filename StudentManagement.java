import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Student {
    int id;
    String name;
    int age;
    String course;

    public Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }
}

public class StudentManagement extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField idField, nameField, ageField, courseField;

    public StudentManagement() {
        setTitle("Student Information System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        String[] columnNames = {"ID", "Name", "Age", "Course"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Input Fields & Buttons
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);
        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);
        panel.add(new JLabel("Course:"));
        courseField = new JTextField();
        panel.add(courseField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        add(panel, BorderLayout.SOUTH);

        
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());

        setVisible(true);
    }


    private void addStudent() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String course = courseField.getText().trim();
    
            // Validations
            if (id <= 0) throw new IllegalArgumentException("ID must be a positive number.");
            if (!name.matches("^[a-zA-Z\\s]+$")) throw new IllegalArgumentException("Name must contain only letters and spaces.");
            if (age < 5 || age > 100) throw new IllegalArgumentException("Age must be between 5 and 100.");
            if (course.isEmpty()) throw new IllegalArgumentException("Course cannot be empty.");
    
            students.add(new Student(id, name, age, course));
            tableModel.addRow(new Object[]{id, name, age, course});
            clearFields();
    
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID and Age must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void updateStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String course = courseField.getText().trim();
    
               
                if (!name.matches("^[a-zA-Z\\s]+$")) throw new IllegalArgumentException("Name must contain only letters and spaces.");
                if (age < 5 || age > 100) throw new IllegalArgumentException("Age must be between 5 and 100.");
                if (course.isEmpty()) throw new IllegalArgumentException("Course cannot be empty.");
    
                
                Student student = students.get(selectedRow);
                student.name = name;
                student.age = age;
                student.course = course;
    
                
                tableModel.setValueAt(name, selectedRow, 1);
                tableModel.setValueAt(age, selectedRow, 2);
                tableModel.setValueAt(course, selectedRow, 3);
    
                clearFields();
            }
             catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Age must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } 
            catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else 
        {
            JOptionPane.showMessageDialog(this, "Select a row to update", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    
    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            students.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        courseField.setText("");
    }

    public static void main(String[] args) {
        new StudentManagement();
    }
}
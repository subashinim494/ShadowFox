import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LibraryManagement extends JFrame {
    private Connection conn;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField idField, titleField, authorField, yearField, searchField;

    public LibraryManagement() {
        setTitle("Library Management System");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        connectDatabase();
        createTable();

        // Table
        String[] columnNames = {"ID", "Title", "Author", "Year"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        loadBooks();

        // Input Fields & Buttons
        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("ID:"));
        idField = new JTextField();
        panel.add(idField);
        panel.add(new JLabel("Title:"));
        titleField = new JTextField();
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        authorField = new JTextField();
        panel.add(authorField);
        panel.add(new JLabel("Year:"));
        yearField = new JTextField();
        panel.add(yearField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        // Search Field
        panel.add(new JLabel("Search Title:"));
        searchField = new JTextField();
        panel.add(searchField);
        JButton searchButton = new JButton("Search");
        panel.add(searchButton);

        add(panel, BorderLayout.SOUTH);

        // Event Handlers
        addButton.addActionListener(e -> addBook());
        updateButton.addActionListener(e -> updateBook());
        deleteButton.addActionListener(e -> deleteBook());
        searchButton.addActionListener(e -> searchBook());

        setVisible(true);
    }

    private void connectDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:library.db");
            System.out.println("Connected to SQLite");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS books (id INTEGER PRIMARY KEY, title TEXT, author TEXT, year INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {
        tableModel.setRowCount(0);
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("year")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addBook() {
        try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO books (id, title, author, year) VALUES (?, ?, ?, ?)");) {
            pstmt.setInt(1, Integer.parseInt(idField.getText()));
            pstmt.setString(2, titleField.getText());
            pstmt.setString(3, authorField.getText());
            pstmt.setInt(4, Integer.parseInt(yearField.getText()));
            pstmt.executeUpdate();
            loadBooks();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try (PreparedStatement pstmt = conn.prepareStatement("UPDATE books SET title=?, author=?, year=? WHERE id=?")) {
                pstmt.setString(1, titleField.getText());
                pstmt.setString(2, authorField.getText());
                pstmt.setInt(3, Integer.parseInt(yearField.getText()));
                pstmt.setInt(4, Integer.parseInt(idField.getText()));
                pstmt.executeUpdate();
                loadBooks();
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to update");
        }
    }

    private void deleteBook() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books WHERE id = ?")) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                loadBooks();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
        }
    }

    private void searchBook() {
        String searchText = searchField.getText();
        tableModel.setRowCount(0);
        try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM books WHERE title LIKE ?")) {
            pstmt.setString(1, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                tableModel.addRow(new Object[]{rs.getInt("id"), rs.getString("title"), rs.getString("author"), rs.getInt("year")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        searchField.setText("");
    }

    public static void main(String[] args) {
        new LibraryManagement();
    }
}

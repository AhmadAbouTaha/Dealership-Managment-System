/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Ahmad
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SalespersonManager extends JFrame {
    private JTextField sidField, snameField, phoneField, emailField;
    private JComboBox<Integer> customerDropdown;
    private JButton addButton, loadButton;
    private JTextArea displayArea;

    public SalespersonManager() {
        setTitle("Salesperson Manager");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Salesperson ID:"));
        sidField = new JTextField();
        inputPanel.add(sidField);

        inputPanel.add(new JLabel("Name:"));
        snameField = new JTextField();
        inputPanel.add(snameField);

        inputPanel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Assign to Customer (CID):"));
        customerDropdown = new JComboBox<>();
        inputPanel.add(customerDropdown);

        addButton = new JButton("Add Salesperson");
        inputPanel.add(addButton);

        loadButton = new JButton("Load Salespeople");
        inputPanel.add(loadButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        addButton.addActionListener(e -> addSalespersonToDB());
        loadButton.addActionListener(e -> loadSalespeopleFromDB());

        loadCustomerCIDs();

        setVisible(true);
    }

    private void loadCustomerCIDs() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT CID FROM Customer")) {

            customerDropdown.removeAllItems(); 
            while (rs.next()) {
                customerDropdown.addItem(rs.getInt("CID"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading customers: " + e.getMessage());
        }
    }

    private void addSalespersonToDB() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO Salesperson (SID, Sname, Phonenumber, Email, CID) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setInt(1, Integer.parseInt(sidField.getText()));
            stmt.setString(2, snameField.getText());
            stmt.setString(3, phoneField.getText());
            stmt.setString(4, emailField.getText());
            stmt.setInt(5, (Integer) customerDropdown.getSelectedItem());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Salesperson added!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadSalespeopleFromDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Salesperson")) {

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("SID: ").append(rs.getInt("SID")).append(", ");
                sb.append("Name: ").append(rs.getString("Sname")).append(", ");
                sb.append("Phone: ").append(rs.getString("Phonenumber")).append(", ");
                sb.append("Email: ").append(rs.getString("Email")).append(", ");
                sb.append("Customer ID: ").append(rs.getInt("CID")).append("\n");
            }

            displayArea.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        sidField.setText("");
        snameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        customerDropdown.setSelectedIndex(0);
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/dealership", "root", "");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SalespersonManager::new);
    }
}


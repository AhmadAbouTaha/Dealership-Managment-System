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

public class SalesTransactionManager extends JFrame {
    private JTextField dateField, priceField, discountField, cashField, checkField;
    private JComboBox<String> vinDropdown;
    private JButton addButton, loadButton;
    private JTextArea displayArea;

    public SalesTransactionManager() {
        setTitle("Sales Transaction Manager");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Date of Sale (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Sale Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Discount:"));
        discountField = new JTextField();
        inputPanel.add(discountField);

        inputPanel.add(new JLabel("Cash Payment:"));
        cashField = new JTextField();
        inputPanel.add(cashField);

        inputPanel.add(new JLabel("Check Payment:"));
        checkField = new JTextField();
        inputPanel.add(checkField);

        inputPanel.add(new JLabel("Select Vehicle VIN:"));
        vinDropdown = new JComboBox<>();
        inputPanel.add(vinDropdown);

        addButton = new JButton("Add Sale");
        inputPanel.add(addButton);

        loadButton = new JButton("Load Sales");
        inputPanel.add(loadButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        addButton.addActionListener(e -> addSaleToDB());
        loadButton.addActionListener(e -> loadSalesFromDB());

        loadVINs(); 
        setVisible(true);
    }

    private void loadVINs() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT VIN FROM Vehicle")) {

            vinDropdown.removeAllItems(); // Clear first
            while (rs.next()) {
                vinDropdown.addItem(rs.getString("VIN"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading VINs: " + e.getMessage());
        }
    }

    private void addSaleToDB() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO Salestransaction (DateOfSale, Saleprice, Discount, Cash, Checkk, VIN) VALUES (?, ?, ?, ?, ?, ?)")) {

            stmt.setDate(1, Date.valueOf(dateField.getText()));
            stmt.setString(2, priceField.getText());
            stmt.setString(3, discountField.getText());
            stmt.setString(4, cashField.getText());
            stmt.setString(5, checkField.getText());
            stmt.setString(6, (String) vinDropdown.getSelectedItem());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Sale recorded successfully!");
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadSalesFromDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Salestransaction")) {

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("Date: ").append(rs.getDate("DateOfSale")).append(", ");
                sb.append("Price: ").append(rs.getString("Saleprice")).append(", ");
                sb.append("Discount: ").append(rs.getString("Discount")).append(", ");
                sb.append("Cash: ").append(rs.getString("Cash")).append(", ");
                sb.append("Check: ").append(rs.getString("Checkk")).append(", ");
                sb.append("VIN: ").append(rs.getString("VIN")).append("\n");
            }

            displayArea.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        dateField.setText("");
        priceField.setText("");
        discountField.setText("");
        cashField.setText("");
        checkField.setText("");
        vinDropdown.setSelectedIndex(0);
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/dealership", "root", "");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SalesTransactionManager::new);
    }
}


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

public class MaintenanceManager extends JFrame {
    private JTextField mnumberField, costField, performedField, dateField;
    private JComboBox<String> vinDropdown;
    private JButton addButton, loadButton;
    private JTextArea displayArea;

    public MaintenanceManager() {
        setTitle("Maintenance Manager");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Maintenance Number:"));
        mnumberField = new JTextField();
        inputPanel.add(mnumberField);

        inputPanel.add(new JLabel("Associated Cost:"));
        costField = new JTextField();
        inputPanel.add(costField);

        inputPanel.add(new JLabel("Performed:"));
        performedField = new JTextField();
        inputPanel.add(performedField);

        inputPanel.add(new JLabel("Service Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Select Vehicle VIN:"));
        vinDropdown = new JComboBox<>();
        inputPanel.add(vinDropdown);

        addButton = new JButton("Add Maintenance");
        inputPanel.add(addButton);

        loadButton = new JButton("Load Records");
        inputPanel.add(loadButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        addButton.addActionListener(e -> addMaintenanceToDB());
        loadButton.addActionListener(e -> loadMaintenanceFromDB());

        loadVINs();

        setVisible(true);
    }

    private void loadVINs() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT VIN FROM Vehicle")) {

            vinDropdown.removeAllItems();
            while (rs.next()) {
                vinDropdown.addItem(rs.getString("VIN"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading VINs: " + e.getMessage());
        }
    }

    private void addMaintenanceToDB() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO Maintenance (Mnumber, Acost, Performed, Servicedate, VIN) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setInt(1, Integer.parseInt(mnumberField.getText()));
            stmt.setString(2, costField.getText());
            stmt.setString(3, performedField.getText());
            stmt.setDate(4, Date.valueOf(dateField.getText()));
            stmt.setString(5, (String) vinDropdown.getSelectedItem());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Maintenance record added!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadMaintenanceFromDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Maintenance")) {

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("M#: ").append(rs.getInt("Mnumber")).append(", ");
                sb.append("Cost: ").append(rs.getString("Acost")).append(", ");
                sb.append("Performed: ").append(rs.getString("Performed")).append(", ");
                sb.append("Date: ").append(rs.getDate("Servicedate")).append(", ");
                sb.append("VIN: ").append(rs.getString("VIN")).append("\n");
            }

            displayArea.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        mnumberField.setText("");
        costField.setText("");
        performedField.setText("");
        dateField.setText("");
        vinDropdown.setSelectedIndex(0);
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/dealership", "root", "");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MaintenanceManager::new);
    }
}

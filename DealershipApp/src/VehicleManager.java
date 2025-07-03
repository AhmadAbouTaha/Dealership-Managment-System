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

public class VehicleManager extends JFrame {
    private JTextField vinField, brandField, colorField, yearField, conditionField;
    private JButton addButton, loadButton;
    private JTextArea displayArea;

    public VehicleManager() {
        setTitle("Vehicle Manager");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("VIN:"));
        vinField = new JTextField();
        inputPanel.add(vinField);

        inputPanel.add(new JLabel("Brand:"));
        brandField = new JTextField();
        inputPanel.add(brandField);

        inputPanel.add(new JLabel("Color:"));
        colorField = new JTextField();
        inputPanel.add(colorField);

        inputPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        inputPanel.add(yearField);

        inputPanel.add(new JLabel("Condition:"));
        conditionField = new JTextField();
        inputPanel.add(conditionField);

        addButton = new JButton("Add Vehicle");
        inputPanel.add(addButton);

        loadButton = new JButton("Load Vehicles");
        inputPanel.add(loadButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        
        addButton.addActionListener(e -> addVehicleToDB());
        loadButton.addActionListener(e -> loadVehiclesFromDB());

        setVisible(true);
    }

    private void addVehicleToDB() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO Vehicle (VIN, Conndition, Brand, Color, Year) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setString(1, vinField.getText());
            stmt.setString(2, conditionField.getText());
            stmt.setString(3, brandField.getText());
            stmt.setString(4, colorField.getText());
            stmt.setInt(5, Integer.parseInt(yearField.getText()));

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Vehicle added!");
            clearFields();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void loadVehiclesFromDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Vehicle")) {

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("VIN: ").append(rs.getString("VIN")).append(", ");
                sb.append("Brand: ").append(rs.getString("Brand")).append(", ");
                sb.append("Color: ").append(rs.getString("Color")).append(", ");
                sb.append("Year: ").append(rs.getInt("Year")).append(", ");
                sb.append("Condition: ").append(rs.getString("Conndition")).append("\n");
            }

            displayArea.setText(sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void clearFields() {
        vinField.setText("");
        brandField.setText("");
        colorField.setText("");
        yearField.setText("");
        conditionField.setText("");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/dealership", "root", "");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VehicleManager::new);
    }
}

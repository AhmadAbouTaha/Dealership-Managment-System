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

public class CustomerManager extends JFrame {
    private JTextField cidField, cnameField, anumberField, streetField, cityField;
    private JButton addButton, loadButton;
    private JTextArea displayArea;

    public CustomerManager() {
        setTitle("Customer Manager");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Customer ID:"));
        cidField = new JTextField();
        inputPanel.add(cidField);

        inputPanel.add(new JLabel("Name:"));
        cnameField = new JTextField();
        inputPanel.add(cnameField);

        inputPanel.add(new JLabel("Apartment Number:"));
        anumberField = new JTextField();
        inputPanel.add(anumberField);

        inputPanel.add(new JLabel("Street:"));
        streetField = new JTextField();
        inputPanel.add(streetField);

        inputPanel.add(new JLabel("City:"));
        cityField = new JTextField();
        inputPanel.add(cityField);

        addButton = new JButton("Add Customer");
        inputPanel.add(addButton);

        loadButton = new JButton("Load Customers");
        inputPanel.add(loadButton);

        add(inputPanel, BorderLayout.NORTH);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        
        addButton.addActionListener(e -> addCustomerToDB());
        loadButton.addActionListener(e -> loadCustomersFromDB());

        setVisible(true);
    }

    private void addCustomerToDB() {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO Customer (CID, Cname, Anumber, Street, City) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setInt(1, Integer.parseInt(cidField.getText()));
            stmt.setString(2, cnameField.getText());
            stmt.setInt(3, Integer.parseInt(anumberField.getText()));
            stmt.setString(4, streetField.getText());
            stmt.setString(5, cityField.getText());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Customer added!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadCustomersFromDB() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Customer")) {

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("CID")).append(", ");
                sb.append("Name: ").append(rs.getString("Cname")).append(", ");
                sb.append("Address: Apt ").append(rs.getInt("Anumber")).append(", ");
                sb.append(rs.getString("Street")).append(", ");
                sb.append(rs.getString("City")).append("\n");
            }

            displayArea.setText(sb.toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        cidField.setText("");
        cnameField.setText("");
        anumberField.setText("");
        streetField.setText("");
        cityField.setText("");
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/dealership", "root", "");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomerManager::new);
    }
}


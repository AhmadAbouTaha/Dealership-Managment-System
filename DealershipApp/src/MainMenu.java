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

public class MainMenu extends JFrame {

    public MainMenu() {
        JLabel title = new JLabel("Dealership Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title);

        JLabel team = new JLabel("By Ahmad Abou Taha", SwingConstants.CENTER);
        team.setFont(new Font("Arial", Font.PLAIN, 12));
        add(team);
        setTitle("Dealership Management System");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 1, 10, 10));

        JButton vehicleBtn = new JButton("Vehicle Manager");
        JButton customerBtn = new JButton("Customer Manager");
        JButton salespersonBtn = new JButton("Salesperson Manager");
        JButton salesBtn = new JButton("Sales Transaction Manager");
        JButton maintenanceBtn = new JButton("Maintenance Manager");
        JButton exitBtn = new JButton("Exit");

        add(vehicleBtn);
        add(customerBtn);
        add(salespersonBtn);
        add(salesBtn);
        add(maintenanceBtn);
        add(exitBtn);

        vehicleBtn.addActionListener(e -> launchWindow("VehicleManager"));
        customerBtn.addActionListener(e -> launchWindow("CustomerManager"));
        salespersonBtn.addActionListener(e -> launchWindow("SalespersonManager"));
        salesBtn.addActionListener(e -> launchWindow("SalesTransactionManager"));
        maintenanceBtn.addActionListener(e -> launchWindow("MaintenanceManager"));
        exitBtn.addActionListener(e -> System.exit(0));

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void launchWindow(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            JFrame frame = (JFrame) clazz.getDeclaredConstructor().newInstance();
            frame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading: " + className + "\n" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}


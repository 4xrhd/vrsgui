/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uits.vehicle3;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Vehicle3 extends JFrame {
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private JList<String> vehicleList;
    private DefaultListModel<String> listModel;
    private JButton rentButton;
    private JButton returnButton;
    private JButton addButton;
    private JButton addInitialButton;
    private JButton removeButton;

    private boolean initialVehiclesAdded = false; // Flag to track if initial vehicles have been added

    public Vehicle3() {
        setTitle("Vehicle Rental System");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create list model
        listModel = new DefaultListModel<>();
        updateListModel();

        // Create JList
        vehicleList = new JList<>(listModel);
        vehicleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create JScrollPane
        JScrollPane scrollPane = new JScrollPane(vehicleList);

        // Create buttons
        rentButton = new JButton("Rent");
        returnButton = new JButton("Return");
        addButton = new JButton("Add");
        addInitialButton = new JButton("Initial Vehicles");
        removeButton = new JButton("Remove");

        // Add action listeners to buttons
        rentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rentVehicle();
            }
        });
        returnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                returnVehicle();
            }
        });
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewVehicle();
            }
        });
        addInitialButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addInitialVehicles();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeVehicle();
            }
        });

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(rentButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(addButton);
        buttonPanel.add(addInitialButton);
        buttonPanel.add(removeButton);

        // Add components to content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to update the list model with vehicle details
    private void updateListModel() {
        listModel.clear();
        for (Vehicle vehicle : vehicles) {
            listModel.addElement(vehicle.brand + " " + vehicle.model + " (" + (vehicle.available ? "Available" : "Not Available") + ") - Rental Rate: $" + vehicle.perKmRate + "/KM");
        }
    }

    // Method to rent a vehicle
    private void rentVehicle() {
        int index = vehicleList.getSelectedIndex();
        if (index != -1) {
            Vehicle vehicle = vehicles.get(index);
            if (vehicle.available) {
                try {
                    int distance = Integer.parseInt(JOptionPane.showInputDialog("Enter the distance traveled in kilometers:"));
                    double cost = vehicle.rent(distance);
                    if (cost != -1) {
                        JOptionPane.showMessageDialog(this, "Total Cost: $" + cost);
                        updateListModel();
                    } else {
                        JOptionPane.showMessageDialog(this, "Sorry, the selected vehicle is not available for rent.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid input for distance.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sorry, the selected vehicle is not available for rent.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to rent.");
        }
    }

    // Method to return a vehicle
    private void returnVehicle() {
        int index = vehicleList.getSelectedIndex();
        if (index != -1) {
            Vehicle vehicle = vehicles.get(index);
            if (!vehicle.available) {
                vehicle.returnVehicle();
                updateListModel();
            } else {
                JOptionPane.showMessageDialog(this, "This vehicle is not rented.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to return.");
        }
    }

    // Method to add a new vehicle
    private void addNewVehicle() {
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField rateField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Brand:"));
        panel.add(brandField);
        panel.add(new JLabel("Model:"));
        panel.add(modelField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Per Kilometer Rental Rate:"));
        panel.add(rateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Vehicle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String brand = brandField.getText();
                String model = modelField.getText();
                int year = Integer.parseInt(yearField.getText());
                double rate = Double.parseDouble(rateField.getText());
                Vehicle newVehicle = new Vehicle(brand, model, year, rate);
                vehicles.add(newVehicle);
                updateListModel();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numeric values for year and rate.");
            }
        }
    }

    // Method to add initial vehicles
    private void addInitialVehicles() {
        if (!initialVehiclesAdded) {
            vehicles.add(new Vehicle("Toyota", "Camry", 2020, 0.2));
            vehicles.add(new Vehicle("Honda", "Civic", 2019, 0.18));
            vehicles.add(new Vehicle("Chevrolet", "Malibu", 2021, 0.22));
            updateListModel();
            initialVehiclesAdded = true; // Mark initial vehicles as added
        } else {
            JOptionPane.showMessageDialog(this, "Initial vehicles have already been added.");
        }
    }

    // Method to remove a vehicle
    private void removeVehicle() {
        int index = vehicleList.getSelectedIndex();
        if (index != -1) {
            vehicles.remove(index);
            updateListModel();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a vehicle to remove.");
        }
    }

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Vehicle3().setVisible(true);
            }
        });
    }
}

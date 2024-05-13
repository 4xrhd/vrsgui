    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package uits.vehicle3;

/**
 *
 * @author 4xrhd
 */
public class Vehicle {
    protected String brand;
    protected String model;
    protected int year;
    protected double perKmRate; // Rental rate per kilometer
    protected boolean available;

    // Constructor
    Vehicle(String brand, String model, int year, double perKmRate) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.perKmRate = perKmRate;
        this.available = true;
    }

    // Method to display vehicle details
    public void display() {
        System.out.println("\nBrand: " + brand);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Per Kilometer Rental Rate: $" + perKmRate);
        System.out.println("Availability: " + (available ? "Available" : "Not Available"));
    }

    // Method to rent the vehicle
    public double rent(int distance) {
        if (available) {
            available = false;
            return perKmRate * distance;
        } else {
            return -1; // Indicate that the vehicle is not available
        }
    }

    // Method to return the vehicle
    public void returnVehicle() {
        available = true;
    }
}
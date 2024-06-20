package gui;

import model.*;
import util.DataLoader;

import javax.swing.*;
import java.io.File;
import java.util.*;



public class Simulation {

    public static final String appConfigPath = "resources" + File.separator +"app.properties";
    public static Map<String , Vehicle > vehicles = new HashMap<>();
    public static List<Rental> rentals = new ArrayList<>();


    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Vehicle Rental Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        GridPanel gridPanel = new GridPanel(20, 20);
        frame.add(gridPanel);
        frame.setVisible(true);
        DataLoader.loadVehicles();
        for (Map.Entry<String, Vehicle> entry : vehicles.entrySet()) {
            System.out.println(entry.getValue());
        }
        System.out.println(vehicles.size());
        DataLoader.loadRentals(gridPanel);
        rentals.forEach(r-> System.out.println(r));
        System.out.println(rentals.size());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}


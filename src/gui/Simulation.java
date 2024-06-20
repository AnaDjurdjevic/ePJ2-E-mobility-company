package gui;

import model.*;
import util.DataLoader;

import javax.swing.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;



public class Simulation {

    public static final String appConfigPath = "resources" + File.separator +"app.properties";
    public static Map<String , Vehicle > vehicles = new HashMap<>();
    public static List<Rental> rentals = new ArrayList<>();
    public static Map<LocalDateTime,List<Rental>> blockOfRentals = new HashMap<>();

    private static void simulateMovement(VehicleMovementGUI vMGui)
    {
        for (Map.Entry<LocalDateTime, List<Rental>> entry : Simulation.blockOfRentals.entrySet()) {
            List<Rental> rentalList = entry.getValue();
            for (Rental rental : rentalList) {
                rental.start();
            }
            for (Rental rental : rentalList) {
                try {
                    rental.join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            vMGui.resetGrid();
        }
    }
    public static void main(String[] args) {
        VehicleMovementGUI gui = new VehicleMovementGUI();
        DataLoader.loadVehicles();
        DataLoader.loadRentals(gui);
        simulateMovement(gui);
    }
}


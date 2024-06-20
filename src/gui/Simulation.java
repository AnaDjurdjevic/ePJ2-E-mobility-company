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


    public static void main(String[] args) {
        VehicleMovementGUI gui = new VehicleMovementGUI();
        DataLoader.loadVehicles();
        DataLoader.loadRentals(gui);
        gui.simulateMovement();
    }
}


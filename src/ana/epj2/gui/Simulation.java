package ana.epj2.gui;

import ana.epj2.model.*;
import ana.epj2.util.DataLoader;

import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;


public class Simulation {

    public static final String appConfigPath = "resources" + File.separator +"app.properties";
    public static Map<String , Vehicle > vehicles = new HashMap<>();
    public static List<Rental> rentals = new ArrayList<>();
    public static Map<LocalDateTime,List<Rental>> blockOfRentals = new TreeMap<>();

    private static synchronized  void simulateMovement(VehicleMovementGUI vMGui)
    {
        for (Map.Entry<LocalDateTime, List<Rental>> entry : Simulation.blockOfRentals.entrySet()) {
            List<Rental> rentalList = entry.getValue();
            System.out.println(entry.getKey());
            for (Rental rental : rentalList) {
                rental.start();
                //TODO kreiraj racun
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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainMenu frame = new MainMenu();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        VehicleMovementGUI gui = new VehicleMovementGUI();
        DataLoader.loadVehicles();
        DataLoader.loadRentals(gui);
        simulateMovement(gui);
    }
}


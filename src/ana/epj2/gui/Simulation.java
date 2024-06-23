package ana.epj2.gui;

import ana.epj2.model.*;
import ana.epj2.util.BillsCreator;
import ana.epj2.util.DataLoader;
import ana.epj2.util.SerializationUtil;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

/**
 * Main class for the simulation of vehicle rentals and movements.
 */
public class Simulation {
    /**
     * Path to the application configuration file.
     */
    public static final String APP_CONFIG_PATH = "resources" + File.separator +"app.properties";
    /**
     * Map of vehicles, keyed by vehicle ID.
     */
    public static Map<String , Vehicle > vehicles = new HashMap<>();
    /**
     * List of all rentals.
     */
    public static List<Rental> rentals = new ArrayList<>();
    /**
     * Map of rental blocks, keyed by rental date and time.
     */
    public static Map<LocalDateTime,List<Rental>> blockOfRentals = new TreeMap<>();
    /**
     * Simulates the movement of vehicles based on the rentals and prints the bills for rentals.
     *
     * @param vMGui the GUI for vehicle movement
     */
    private static synchronized  void simulateMovement(VehicleMovementGUI vMGui)
    {
        for (Map.Entry<LocalDateTime, List<Rental>> entry : Simulation.blockOfRentals.entrySet()) {
            List<Rental> rentalList = entry.getValue();
            System.out.println(entry.getKey());
            for (Rental rental : rentalList) {
                rental.start();
            }
            for (Rental rental : rentalList) {
                try {
                    rental.join();
                    LocalDate date = rental.getDateAndTime().toLocalDate();
                    List<Bill> billsForDate = BillsCreator.bills.get(date);

                    if (billsForDate != null) {
                        for (Bill bill : billsForDate) {
                            if (bill.getRental().equals(rental)) {
                                bill.printBill();
                                break;
                            }
                        }
                    }
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
    /**
     * Main method to start the simulation.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        DataLoader.loadVehicles();
        VehicleMovementGUI gui = new VehicleMovementGUI();
        DataLoader.loadRentals(gui);
        BillsCreator.addBills();
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
        SerializationUtil.serializeBrokenVehicles();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            SerializationUtil.serializeBrokenVehicles();
        }));
        simulateMovement(gui);
    }
}


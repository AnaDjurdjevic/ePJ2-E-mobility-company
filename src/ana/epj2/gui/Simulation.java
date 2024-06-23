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


public class Simulation {

    public static final String APP_CONFIG_PATH = "resources" + File.separator +"app.properties";
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


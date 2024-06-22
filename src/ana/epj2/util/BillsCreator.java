package ana.epj2.util;

import ana.epj2.gui.Simulation;
import ana.epj2.model.Bill;
import ana.epj2.model.Rental;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;

public class BillsCreator {
    public static Map<LocalDateTime,List<Bill>> bills = new TreeMap<>();

    public static void checkRentalsArea()
    {
        for (Map.Entry<LocalDateTime, List<Rental>> entry : Simulation.blockOfRentals.entrySet()) {
            List<Rental> rentalList = entry.getValue();
            for (Rental rental : rentalList) {
                int currentX = rental.getStartLocation().getX();
                int currentY = rental.getStartLocation().getY();
                rental.checkWiderPart(currentX, currentY);
                currentX = moveHorizontally(currentX, currentY, rental);
                moveVertically(currentX, currentY, rental);
            }
        }
    }
    private static int moveHorizontally(int currentX, int currentY,Rental rental) {
        while (currentX != rental.getEndLocation().getX()) {
            currentX += (rental.getEndLocation().getX() - rental.getStartLocation().getX()) / Math.abs(rental.getEndLocation().getX() - rental.getStartLocation().getX());
            rental.checkWiderPart(currentX, currentY);
        }
        return currentX;
    }

    private static void moveVertically(int currentX, int currentY,Rental rental) {
        while (currentY != rental.getEndLocation().getY()) {
            currentY += (rental.getEndLocation().getY() - rental.getStartLocation().getY()) / Math.abs(rental.getEndLocation().getY() - rental.getStartLocation().getY());
            rental.checkWiderPart(currentX, currentY);
        }
    }

    public static void addBills() {
        checkRentalsArea();
        for (Map.Entry<LocalDateTime, List<Rental>> entry : Simulation.blockOfRentals.entrySet()) {
            LocalDateTime rentalDate = entry.getKey();
            List<Rental> rentalList = entry.getValue();

            for (Rental rental : rentalList) {
                Bill bill = new Bill(rental);
                addBillToMap(rentalDate, bill);
            }
        }
    }
    private static void addBillToMap(LocalDateTime rentalDate, Bill bill) {
        if (!bills.containsKey(rentalDate)) {
            bills.put(rentalDate, new ArrayList<>());
        }
        bills.get(rentalDate).add(bill);
    }
}

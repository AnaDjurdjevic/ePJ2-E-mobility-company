package ana.epj2.util;

import ana.epj2.gui.Simulation;
import ana.epj2.model.Bill;
import ana.epj2.model.Rental;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
/**
 * Utility class for creating and managing bills for rentals.
 */
public class BillsCreator {
    public static Map<LocalDate,List<Bill>> bills = new TreeMap<>();
    /**
     * Checks the area of rentals and updates the wasInTheWiderPart attribute for each rental.
     */
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
    /**
     * Moves the rental horizontally to the end location while updating the wasInTheWiderPart attribute.
     *
     * @param currentX the current x-coordinate
     * @param currentY the current y-coordinate
     * @param rental   the rental being processed
     * @return the updated x-coordinate
     */
    private static int moveHorizontally(int currentX, int currentY,Rental rental) {
        while (currentX != rental.getEndLocation().getX()) {
            currentX += (rental.getEndLocation().getX() - rental.getStartLocation().getX()) / Math.abs(rental.getEndLocation().getX() - rental.getStartLocation().getX());
            rental.checkWiderPart(currentX, currentY);
        }
        return currentX;
    }
    /**
     * Moves the rental vertically to the end location while updating the wasInTheWiderPart attribute.
     *
     * @param currentX the current x-coordinate
     * @param currentY the current y-coordinate
     * @param rental   the rental being processed
     */
    private static void moveVertically(int currentX, int currentY,Rental rental) {
        while (currentY != rental.getEndLocation().getY()) {
            currentY += (rental.getEndLocation().getY() - rental.getStartLocation().getY()) / Math.abs(rental.getEndLocation().getY() - rental.getStartLocation().getY());
            rental.checkWiderPart(currentX, currentY);
        }
    }
    /**
     * Adds bills for all rentals in the Simulation.blockOfRentals map.
     */
    public static void addBills() {
        checkRentalsArea();
        for (Map.Entry<LocalDateTime, List<Rental>> entry : Simulation.blockOfRentals.entrySet()) {
            LocalDateTime rentalDate = entry.getKey();
            List<Rental> rentalList = entry.getValue();

            for (Rental rental : rentalList) {
                Bill bill = new Bill(rental);
                addBillToMap(rentalDate.toLocalDate(), bill);
            }
        }
    }
    /**
     * Adds a bill to the bills map, grouped by the rental date.
     *
     * @param rentalDate the rental date
     * @param bill       the bill to be added
     */
    private static void addBillToMap(LocalDate rentalDate, Bill bill) {
        if (!bills.containsKey(rentalDate)) {
            bills.put(rentalDate, new ArrayList<>());
        }
        bills.get(rentalDate).add(bill);
    }
}

package ana.epj2.model;

import ana.epj2.gui.VehicleMovementGUI;
import javax.swing.*;
import java.time.LocalDateTime;

/**
 * Represents a vehicle rental.
 * This class handles the rental details including the user, vehicle,
 * start and end locations, duration, discounts and promotions.
 * It also manages the vehicle's movement and updates the GUI accordingly.
 */
public class Rental extends Thread{
    private static int numOfRentals;
    private LocalDateTime dateAndTime;
    private User user;
    private Vehicle vehicle;
    private Location startLocation;
    private Location endLocation;
    private int duration;
    private boolean hasPromotion = false;
    private boolean hasDiscount = false;
    protected boolean wasInTheWiderPart;
    private VehicleMovementGUI vMGui;
    /**
     * Constructs a new Rental with the specified details.
     *
     * @param dateAndTime the date and time of the rental
     * @param user the user who rented the vehicle
     * @param vehicle the vehicle that is rented
     * @param startLocation the starting location of the rental
     * @param endLocation the ending location of the rental
     * @param duration the duration of the rental in seconds
     * @param hasPromotion whether the rental has a promotion
     * @param vMGui the GUI to update with vehicle movement
     */
    public Rental(LocalDateTime dateAndTime, User user, Vehicle vehicle,
                  Location startLocation, Location endLocation, int duration,
                  boolean hasPromotion, VehicleMovementGUI vMGui)
    {
        this.dateAndTime = dateAndTime;
        this.user = user;
        this.vehicle = vehicle;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.duration = duration;
        this.hasPromotion = hasPromotion;
        this.vMGui = vMGui;
        numOfRentals++;
        if(numOfRentals % 10 == 0) {
            this.hasDiscount = true;
        }
        this.wasInTheWiderPart = false;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isHasDiscount() {
        return hasDiscount;
    }

    public void setHasDiscount(boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
    }

    public boolean isHasPromotion() {
        return hasPromotion;
    }
    public void setHasPromotion(boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }
    public boolean isWasInTheWiderPart() {
        return wasInTheWiderPart;
    }
    public void setWasInTheWiderPart(boolean wasInTheWiderPart) {
        this.wasInTheWiderPart = wasInTheWiderPart;
    }
    /**
     * Checks if the given coordinates are within the wider part of the area.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if the coordinates are in the wider part, false otherwise
     */
    private boolean isLocationInWider(int x, int y)
    {
        return !(x >= 5 && x <= 14 && y >= 5 && y <= 14);
    }
    /**
     * Runs the rental simulation, moving the vehicle from the start location to the end location.
     * Updates the GUI with the vehicle's position at each step.
     * If the vehicle's battery is empty at the start, it will be charged to 100%.
     * If the vehicle has a malfunction, it will be displayed at the start location for 3 seconds before being removed.
     */
    @Override
    public void run() {
        if(vehicle.emptyBattery == true)
        {
            vehicle.chargeBattery(100);
        }
        int steps = Math.abs(endLocation.getX() - startLocation.getX()) + Math.abs(endLocation.getY() - startLocation.getY()) + 1;
        int stepDuration = duration / steps;
        int currentX = startLocation.getX();
        int currentY = startLocation.getY();
        final int curX1 = currentX;
        if (vehicle.getMalfunction() != null) {
            SwingUtilities.invokeLater(() -> vMGui.updateGrid(-1, -1, curX1, currentY, vehicle));
            sleepForDuration(3);
            SwingUtilities.invokeLater(() -> vMGui.removeVehicleFromGrid(curX1, currentY, vehicle));
            return;
        }
        SwingUtilities.invokeLater(() ->vMGui.updateGrid(-1, -1, curX1, currentY, vehicle));
        sleepForDuration(stepDuration);
        if (!vehicle.emptyBattery) {
            currentX = moveHorizontally(currentX, currentY, stepDuration);
        }
        if (!vehicle.emptyBattery) {
            moveVertically(currentX, currentY, stepDuration);
        }
        final int curX2 = currentX;
        if (!vehicle.emptyBattery) {
            SwingUtilities.invokeLater(() -> vMGui.updateGrid(curX2, currentY, endLocation.getX(), endLocation.getY(), vehicle));
            sleepForDuration(stepDuration);
        }
    }
    /**
     * Moves the vehicle horizontally towards the end location, updating the GUI at each step.
     * Discharges the vehicle's battery and stops if the battery becomes empty.
     *
     * @param currentX the current x-coordinate of the vehicle
     * @param currentY the current y-coordinate of the vehicle
     * @param stepDuration the duration of each step in seconds
     * @return the updated x-coordinate of the vehicle
     */
    private synchronized int moveHorizontally(int currentX, int currentY, int stepDuration) {
        while (currentX != endLocation.getX()) {
            int prevX = currentX;
            currentX += (endLocation.getX() - startLocation.getX()) / Math.abs(endLocation.getX() - startLocation.getX());
            vehicle.dischargeBattery(stepDuration);
            if (vehicle.emptyBattery == true) {
                final int curX1 = prevX;
                SwingUtilities.invokeLater(() -> vMGui.updateGrid(-1, -1, curX1, currentY, vehicle));
                sleepForDuration(3);
                SwingUtilities.invokeLater(() -> vMGui.removeVehicleFromGrid(curX1, currentY, vehicle));
                return currentX;
            }
            final int curX = currentX;
            SwingUtilities.invokeLater(() ->vMGui.updateGrid(prevX, currentY, curX, currentY, vehicle));
            sleepForDuration(stepDuration);
        }
        return currentX;
    }
    /**
     * Moves the vehicle vertically towards the end location, updating the GUI at each step.
     * Discharges the vehicle's battery and stops if the battery becomes empty.
     *
     * @param currentX the current x-coordinate of the vehicle
     * @param currentY the current y-coordinate of the vehicle
     * @param stepDuration the duration of each step in seconds
     */
    private synchronized void moveVertically(int currentX, int currentY, int stepDuration) {
        while (currentY != endLocation.getY()) {
            int prevY = currentY;
            currentY += (endLocation.getY() - startLocation.getY()) / Math.abs(endLocation.getY() - startLocation.getY());
            vehicle.dischargeBattery(stepDuration);
            if (vehicle.emptyBattery == true) {
                final int curX1 = currentX;
                final int curY1 = prevY;
                SwingUtilities.invokeLater(() -> vMGui.updateGrid(-1, -1, curX1, curY1, vehicle));
                sleepForDuration(3);
                SwingUtilities.invokeLater(() -> vMGui.removeVehicleFromGrid(curX1, curY1, vehicle));
                return;
            }
            final int curY = currentY;
            SwingUtilities.invokeLater(() ->vMGui.updateGrid(currentX, prevY, currentX, curY, vehicle));
            sleepForDuration(stepDuration);
        }
    }
    /**
     * Checks if the current location (x, y) is in the wider part of the map.
     * If it is, sets the `wasInTheWiderPart` flag to true.
     *
     * @param x the x-coordinate of the current location
     * @param y the y-coordinate of the current location
     */
    public void checkWiderPart(int x, int y) {
        if (!wasInTheWiderPart && isLocationInWider(x, y)) {
            wasInTheWiderPart = true;
        }
    }
    /**
     * Puts the current thread to sleep for the specified duration.
     *
     * @param stepDuration the duration to sleep in seconds
     */
    private void sleepForDuration(int stepDuration) {
        try {
            sleep(stepDuration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Checks if this Rental object is equal to the specified object.
     * Two Rental objects are considered equal if their date and time, and vehicle are equal.
     *
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null )
            return false;
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rental other = (Rental)obj;
        if (!this.dateAndTime.equals(other.dateAndTime)|| !this.vehicle.equals(other.vehicle))
        {
            return false;
        }
        return true;
    }
    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
    @Override
    public String toString()
    {
        return "[Date=" + dateAndTime +
                ", User=" + user.getName() +
                ", vehicle ID=" + vehicle.getID() +
                ", start location=" + startLocation+
                ", end location=" + endLocation+
                ", duration=" + duration+
                ", malfunction=" + vehicle.getMalfunction();
    }
}
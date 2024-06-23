package ana.epj2.model;

import ana.epj2.gui.VehicleMovementGUI;
import javax.swing.*;
import java.time.LocalDateTime;

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
    private boolean isLocationInWider(int x, int y)
    {
        return !(x >= 5 && x <= 14 && y >= 5 && y <= 14);
    }
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
    public void checkWiderPart(int x, int y) {
        if (!wasInTheWiderPart && isLocationInWider(x, y)) {
            wasInTheWiderPart = true;
        }
    }
    private void sleepForDuration(int stepDuration) {
        try {
            sleep(stepDuration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
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
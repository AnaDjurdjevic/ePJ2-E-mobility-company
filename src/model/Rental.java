package model;

import gui.GridPanel;
import gui.VehicleMovementGUI;

import java.time.LocalDateTime;
import java.util.Random;

public class Rental extends Thread{
    private static int numOfRentals;
    private LocalDateTime dateAndTime;
    private User user;
    private Vehicle vehicle;
    private Location startLocation;//trenutna lokacija gdje je prevozno sredstvo preuzeto Location
    private Location endLocation;//lokacija gdje se prevozno sredstvo ostavlja nakon korišćenja Location
    private int duration;// trajanje korišćenja u sekundama
    private boolean hasPromotion = false;
    private boolean hasDiscount = false;
    protected boolean wasInTheWiderPart;
    private GridPanel gridPanel;
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

    public void setHasPromotion(boolean hasPromotion) {
        this.hasPromotion = hasPromotion;
    }

    private boolean isLocationInWider(int x, int y)
    {
        return x < 5 || x > 14 || y < 5 || y > 14;
    }
    @Override
    public void run() {
        int steps = Math.abs(endLocation.getX() - startLocation.getX())+ Math.abs(endLocation.getY() - startLocation.getY());
        int stepDuration = duration / steps;
        /*for (int x = startLocation.getX(); x != endLocation.getX(); x += (endLocation.getX() - startLocation.getX()) / Math.abs(endLocation.getX() - startLocation.getX())) {
            //azuriraj izgled mape
            if(isLocationInWider(x,startLocation.getY()))
            {
                wasInTheWiderPart = true;
            }
            System.out.println("Vozilo ID" + vehicle.getID()+ " je na polju (" + x + ", " + startLocation.getY() + ")");
            vMGui.updateGrid(x, startLocation.getY(),vehicle);
            //gridPanel.addVehicle(x,startLocation.getY(),vehicle);
        }
        for (int y = startLocation.getY(); y != endLocation.getY(); y += (endLocation.getY() - startLocation.getY()) / Math.abs(endLocation.getY() - startLocation.getY())) {
            //azuriraj izgled mape
            if(isLocationInWider(y,endLocation.getX()))
            {
                wasInTheWiderPart = true;
            }
            System.out.println("Vozilo ID" + vehicle.getID()+ " je na polju (" + endLocation.getX() + ", " + y + ")");
            vMGui.updateGrid(endLocation.getX(), y,vehicle);
            //gridPanel.addVehicle(endLocation.getX(),y,vehicle);
        }
        System.out.println("Vozilo je stiglo na cilj (" + endLocation.getX() + ", " + endLocation.getY() + ")");
        gridPanel.addVehicle(endLocation.getX(),endLocation.getY(),vehicle);*/
        int currentX = startLocation.getX();
        int currentY = startLocation.getY();

        for (int x = currentX; x != endLocation.getX(); x += (endLocation.getX() - currentX) / Math.abs(endLocation.getX() - currentX)) {
            // Update grid and move to next cell
            vMGui.updateGrid(currentX, currentY, x, currentY, vehicle);
            currentX = x;
            vehicle.dischargeBattery(stepDuration);
        }
        for (int y = currentY; y != endLocation.getY(); y += (endLocation.getY() - currentY) / Math.abs(endLocation.getY() - currentY)) {
            // Update grid and move to next cell
            vMGui.updateGrid(currentX, currentY, currentX, y, vehicle);
            currentY = y;
            vehicle.dischargeBattery(stepDuration);
        }
        // Final update for end location
        vMGui.updateGrid(currentX, currentY, endLocation.getX(), endLocation.getY(), vehicle);

        //TODO kako se vozilo pomijera provjeravaj ima li kvar i da li je baterija prazna i prazni bateriju vremenom
            try {
                Thread.sleep(stepDuration * 1000);
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
        return "[Datum=" + dateAndTime +
                ", korisnik=" + user.getName() +
                ", ID prevoznog sredstva=" + vehicle.getID() +
                ", pocetna lokacija=" + startLocation+
                ", odrediste=" + endLocation+
                ", trajanje=" + duration+
                ", kvar=" + vehicle.getMalfunction();
    }
}

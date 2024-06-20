package model;

import gui.VehicleMovementGUI;

import java.time.LocalDateTime;

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
        int currentX = startLocation.getX();
        int currentY = startLocation.getY();
        vMGui.updateGrid(-1, -1, currentX, currentY, vehicle);
        while (currentX != endLocation.getX()) {
            int prevX = currentX;
            currentX += (endLocation.getX() - startLocation.getX()) / Math.abs(endLocation.getX() - startLocation.getX());
            vehicle.dischargeBattery(stepDuration);
            if(isLocationInWider(prevX,currentX))
            {
                wasInTheWiderPart = true;
            }
            vMGui.updateGrid(prevX, currentY, currentX, currentY, vehicle);
            try {
                sleep(stepDuration * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (currentY != endLocation.getY()) {
            int prevY = currentY;
            currentY += (endLocation.getY() - startLocation.getY()) / Math.abs(endLocation.getY() - startLocation.getY());
            vehicle.dischargeBattery(stepDuration);
            if(isLocationInWider(currentX,prevY))
            {
                wasInTheWiderPart = true;
            }
            vMGui.updateGrid(currentX, prevY, currentX, currentY, vehicle);
            try {
                sleep(stepDuration * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            sleep(stepDuration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vMGui.updateGrid(currentX, currentY, endLocation.getX(), endLocation.getY(), vehicle);
        //TODO kako se vozilo pomijera provjeravaj ima li kvar i da li je baterija prazna
        //TODO omoguciti preklapanje vise vozila
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

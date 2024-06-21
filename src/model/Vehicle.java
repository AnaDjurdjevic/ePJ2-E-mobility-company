package model;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Random;

public abstract class Vehicle {
    protected String ID;
    protected String manufacturer;
    protected String model;
    protected double purchasePrice;
    protected int currentBatteryLevel;
    protected boolean morePassengers;
    protected boolean malfunctioned = false;//ima li potrebe? Moze samo da provjeri da li mu je instanciran objekat Malfunction,
    //ili je laksa provjera putem boolean atributa
    protected Malfunction malfunction;
    protected Color color;

    public Vehicle(String ID, String manufacturer, String model,
                   double purchasePrice, boolean morePassengers,Malfunction malfunction)
    {
        Random random = new Random();
        this.ID = ID;
        this.manufacturer = manufacturer;
        this.model = model;
        this.purchasePrice = purchasePrice;
        this.morePassengers = morePassengers;
        this.malfunction = null;
        this.currentBatteryLevel = 100;
        color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getCurrentBatteryLevel() {
        return currentBatteryLevel;
    }

    public void setCurrentBatteryLevel(int currentBatteryLevel) {
        this.currentBatteryLevel = currentBatteryLevel;
    }

    public Malfunction getMalfunction() {
        return malfunction;
    }

    public void setMalfunction(Malfunction malfunction) {
        this.malfunction = malfunction;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void chargeBattery(int amount)
    {
        this.currentBatteryLevel += amount;
        if (this.currentBatteryLevel > 100) {
            this.currentBatteryLevel = 100;
        }
    }

    public void dischargeBattery(int amount)
    {
        this.currentBatteryLevel -= amount;
        if(this.currentBatteryLevel < 0)
        {
            this.currentBatteryLevel = 0;
            //this.malfunction = new Malfunction("Empty battery", LocalDateTime.now());TODO ne treba da se biljezi kao kvar
        }
    }


    public void malfunctionHappened(String reason, LocalDateTime dateAndTime)
    {
        this.malfunctioned = true;
        this.malfunction = new Malfunction(reason, dateAndTime);

    }

    public void repair()
    {
        this.malfunctioned = false;
        this.malfunction = null;//TODO mozda neka mapa kvarova da ipak postoji?
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass())
        {
            return false;
        }
        final Vehicle other = (Vehicle)obj;
        if(!this.ID.equals(other.ID))
        {
            return false;
        }
        return true;
    }
    @Override
    public String toString()
    {
        return "[ID=" + ID +
                ", manufacturer=" + manufacturer +
                ", model=" + model +
                ", purchase price=" + purchasePrice +
                ", malfunction=" + malfunction;
    }
}

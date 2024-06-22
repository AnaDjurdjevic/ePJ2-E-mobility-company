package ana.epj2.model;

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
    protected Malfunction malfunction;
    protected Color color;
    protected boolean emptyBattery = false;
    protected double repairCoefficient;

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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public boolean isEmptyBattery() {
        return emptyBattery;
    }

    public void setEmptyBattery(boolean emptyBattery) {
        this.emptyBattery = emptyBattery;
    }

    public void chargeBattery(int amount)
    {
        this.currentBatteryLevel += amount;
        if (this.currentBatteryLevel > 100) {
            this.currentBatteryLevel = 100;
        }
        emptyBattery = false;
    }

    public void dischargeBattery(int amount)
    {
        this.currentBatteryLevel -= amount;
        if(this.currentBatteryLevel < 10)
        {
            this.currentBatteryLevel = 10;
            emptyBattery = true;
        }
    }


    public void malfunctionHappened(String reason, LocalDateTime dateAndTime)
    {
        this.malfunction = new Malfunction(reason, dateAndTime);

    }

    public void repair()
    {
        this.malfunction = null;
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
                ", ana.epj2.model=" + model +
                ", purchase price=" + purchasePrice +
                ", malfunction=" + malfunction;
    }
}

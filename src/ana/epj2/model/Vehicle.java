package ana.epj2.model;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Represents a generic vehicle in the e-mobility system.
 */
public abstract class Vehicle implements Serializable {
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

    /**
     * Constructs a new Vehicle with the specified details.
     *
     * @param ID the unique identifier for the vehicle
     * @param manufacturer the manufacturer of the vehicle
     * @param model the model of the vehicle
     * @param purchasePrice the purchase price of the vehicle
     * @param morePassengers indicates if the vehicle can carry more passengers
     * @param malfunction the malfunction details of the vehicle, if any
     */
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

    public double getRepairCoefficient() {
        return repairCoefficient;
    }

    public void setRepairCoefficient(double repairCoefficient) {
        this.repairCoefficient = repairCoefficient;
    }
    /**
     * Charges the battery of the vehicle by the specified amount.
     * If the resulting battery level exceeds 100%, it is capped at 100%.
     * Also sets the emptyBattery flag to false.
     *
     * @param amount the amount to charge the battery
     */
    public void chargeBattery(int amount)
    {
        this.currentBatteryLevel += amount;
        if (this.currentBatteryLevel > 100) {
            this.currentBatteryLevel = 100;
        }
        emptyBattery = false;
    }
    /**
     * Discharges the battery of the vehicle by the specified amount.
     * If the resulting battery level falls below 10%, it is set to 10%
     * and the emptyBattery flag is set to true.
     *
     * @param amount the amount to discharge the battery
     */
    public void dischargeBattery(int amount)
    {
        this.currentBatteryLevel -= amount;
        if(this.currentBatteryLevel < 10)
        {
            this.currentBatteryLevel = 10;
            emptyBattery = true;
        }
    }
    /**
     * Repairs the vehicle by setting the malfunction attribute to null.
     * This indicates that the vehicle no longer has a malfunction.
     */
    public void repair()
    {
        this.malfunction = null;
    }
    /**
     * Indicates whether some other object is "equal to" this one.
     * * This method compares the following fields:
     *  * <ul>
     *  *   <li>{@code ID} - the unique identifier of the vehicle</li>
     *  * </ul>
     *  *
     * @param obj the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise
     */
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
    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
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

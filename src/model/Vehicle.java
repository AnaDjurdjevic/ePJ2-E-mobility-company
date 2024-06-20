package model;

import java.time.LocalDateTime;

public abstract class Vehicle {
    protected String ID;
    protected String manufacturer;
    protected String model;
    protected double purchasePrice;
    protected double currentBatteryLevel;
    protected boolean morePassengers;
    protected boolean malfunctioned = false;//ima li potrebe? Moze samo da provjeri da li mu je instanciran objekat Malfunction,
    //ili je laksa provjera putem boolean atributa
    protected Malfunction malfunction;
    protected boolean wasInTheWiderPart;

    public Vehicle(String ID, String manufacturer, String model,
                   double purchasePrice, boolean morePassengers,Malfunction malfunction)
    {
        this.ID = ID;
        this.manufacturer = manufacturer;
        this.model = model;
        this.purchasePrice = purchasePrice;
        this.morePassengers = morePassengers;
        this.malfunction = null; //TODO za sad ovako, kreira se bez kvara
        this.currentBatteryLevel = 100;// u pocetku je nivo baterije 100
        this.wasInTheWiderPart = false;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getCurrentBatteryLevel() {
        return currentBatteryLevel;
    }

    public void setCurrentBatteryLevel(double currentBatteryLevel) {
        this.currentBatteryLevel = currentBatteryLevel;
    }

    public boolean isWasInTheWiderPart() {
        return wasInTheWiderPart;
    }

    public void setWasInTheWiderPart(boolean wasInTheWiderPart) {
        this.wasInTheWiderPart = wasInTheWiderPart;
    }

    public Malfunction getMalfunction() {
        return malfunction;
    }

    public void setMalfunction(Malfunction malfunction) {
        this.malfunction = malfunction;
    }
    public void chargeBattery(double amount)
    {
        this.currentBatteryLevel += amount;
        if (this.currentBatteryLevel > 100) {
            this.currentBatteryLevel = 100;
        }
    }

    public void dischargeBattery(double amount)
    {
        //TODO
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
                ", proizvodjac=" + manufacturer +
                ", model=" + model +
                ", cijena nabavke=" + purchasePrice +
                ", kvar=" + malfunction;
    }
}

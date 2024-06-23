package ana.epj2.model;

/**
 * Represents a scooter in the e-mobility system.
 */
public class Scooter extends Vehicle{
    private double maxSpeed;
    /**
     * Constructs a new Scooter with the specified details.
     *
     * @param ID the unique identifier for the scooter
     * @param manufacturer the manufacturer of the scooter
     * @param model the model of the scooter
     * @param purchasePrice the purchase price of the scooter
     * @param maxSpeed the maximum speed of the scooter
     */
    public Scooter(String ID, String manufacturer, String model,
                   double purchasePrice,double maxSpeed)
    {
        super(ID,manufacturer,model,purchasePrice,false, null);
        this.maxSpeed = maxSpeed;
        this.repairCoefficient = 0.02;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
    @Override
    public String toString()
    {
        return "Scooter " + super.toString()
                + ", maximum speed=" + maxSpeed + "]";
    }
}

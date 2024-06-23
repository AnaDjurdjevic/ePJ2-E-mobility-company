package ana.epj2.model;

/**
 * Represents a bicycle in the e-mobility system.
 */
public class Bicycle extends Vehicle{

    private double autonomy;

    /**
     * Constructs a new Bicycle with the specified details.
     *
     * @param ID the unique identifier for the bicycle
     * @param manufacturer the manufacturer of the bicycle
     * @param model the model of the bicycle
     * @param purchasePrice the purchase price of the bicycle
     * @param autonomy the autonomy(range per charge) of the bicycle
     */
    public Bicycle(String ID, String manufacturer, String model,
                   double purchasePrice, double autonomy)
    {
        super(ID,manufacturer,model,purchasePrice,false, null);
        this.autonomy = autonomy;
        this.repairCoefficient = 0.04;
    }

    public double getAutonomy() {
        return autonomy;
    }

    public void setAutonomy(double autonomy) {
        this.autonomy = autonomy;
    }
    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
    @Override
    public String toString()
    {
        return "Bicycle " + super.toString()
                + ", autonomy=" + autonomy + "]";
    }
}

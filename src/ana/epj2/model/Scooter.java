package ana.epj2.model;

public class Scooter extends Vehicle{
    private double maxSpeed;

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

    @Override
    public String toString()
    {
        return "Scooter " + super.toString()
                + ", maximum speed=" + maxSpeed + "]";
    }
}

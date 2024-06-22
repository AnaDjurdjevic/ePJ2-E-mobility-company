package ana.epj2.model;

public class Bicycle extends Vehicle{

    private double autonomy;

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

    @Override
    public String toString()
    {
        return "Bicycle " + super.toString()
                + ", autonomy=" + autonomy + "]";
    }
}

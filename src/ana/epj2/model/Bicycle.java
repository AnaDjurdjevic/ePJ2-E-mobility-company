package ana.epj2.model;

public class Bicycle extends Vehicle{

    private double autonomy;//moze double jer se ne prati domet tokom kretanja

    public Bicycle(String ID, String manufacturer, String model,
                   double purchasePrice, double autonomy)
    {
        super(ID,manufacturer,model,purchasePrice,true, null);
        this.autonomy = autonomy;
        this.repairCoefficient = 0.04;
    }

    @Override
    public String toString()
    {
        return "Bicycle " + super.toString()
                + ", autonomy=" + autonomy + "]";
    }
}

package model;

public class Bicycle extends Vehicle{

    private double autonomy;//moze double jer se ne prati domet tokom kretanja

    public Bicycle(String ID, String manufacturer, String model,
                   double purchasePrice, double autonomy)
    {
        super(ID,manufacturer,model,purchasePrice,true, null);
        this.autonomy = autonomy;
    }

    @Override
    public String toString()
    {
        return "Bicikl " + super.toString()
                + ", autonomija=" + autonomy + "]";
    }
}

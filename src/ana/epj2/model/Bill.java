package ana.epj2.model;

import ana.epj2.util.Parameters;

public class Bill {
    private Rental rental;
    private double amount;
    private double totalPayment;
    private double discount = 0.0;
    private double discountProm = 0.0;
    private double distance;
    private double initialPrice;

    public Bill(Rental rental)
    {
        this.rental = rental;
        amount = 0.0;
        totalPayment = 0.0;
        distance = 0.0;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    private void calculateDistance() {
        double unitPrice = getUnitPrice();
        double distancePrice = getDistancePrice();
        distance = unitPrice * distancePrice;
    }
    private void calculateDiscount()
    {
        Parameters parameters = Parameters.getInstance();
        if(rental.isHasDiscount())
        {
            discount = initialPrice * parameters.getDiscount()/100;
        }
    }

    private void calculateDiscountProm()
    {
        Parameters parameters = Parameters.getInstance();
        if(rental.isHasPromotion())
        {
            discountProm = initialPrice * parameters.getDiscountProm()/100;
        }
    }

    private void calculateInitialPrice()//TODO provjeri za kvar
    {
        if(rental.getVehicle().getMalfunction()==null)
        {
            double unitPrice = getUnitPrice();
            initialPrice = rental.getDuration() * unitPrice;
        }else {
            initialPrice = 0.0;
        }
    }
    private void calculateAmount()
    {
        try {
            amount = distance * rental.getDuration();
        }catch (IllegalArgumentException ex)
        {
            amount = 0.0;
            ex.printStackTrace();
        }
    }

    private void calculateTotalPayment()
    {
        totalPayment = amount - (amount * discount)- (amount * discountProm);
    }
    private double getUnitPrice()
    {
        double unitPrice;
        Parameters parameters = Parameters.getInstance();
        if (rental.getVehicle() instanceof Car) {
            unitPrice = parameters.getCarUnitPrice();
        } else if (rental.getVehicle() instanceof Bicycle) {
            unitPrice = parameters.getBikeUnitPrice();
        } else if (rental.getVehicle() instanceof Scooter) {
            unitPrice = parameters.getScooterUnitPrice();
        } else {
            throw new IllegalArgumentException("Unknown vehicle type");
        }
        return unitPrice;
    }
    private double getDistancePrice()
    {
        double distancePrice;
        Parameters parameters = Parameters.getInstance();
        if (rental.wasInTheWiderPart) {
            distancePrice = parameters.getDistanceWide();
        } else {
            distancePrice = parameters.getDistanceNarrow();
        }
        return distancePrice;
    }
}

package model;

import util.Parameters;

public class Bill {
    private Rental rental;
    private double amount;
    private double totalPayment;
    private double discount = 0.0;
    private double discountProm = 0.0;

    public Bill(Rental rental)
    {
        this.rental = rental;
        amount = 0.0;
        totalPayment = 0.0;
        Parameters parameters = Parameters.getInstance();
        if(rental.isHasDiscount())
        {
            discount = parameters.getDiscount();
        }
        if(rental.isHasPromotion())
        {
            discount = parameters.getDiscountProm();
        }
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

    private double calculateDistance() {

        double unitPrice;
        double distancePrice;
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

        if (rental.getVehicle().wasInTheWiderPart) {
            distancePrice = parameters.getDistanceWide();
        } else {
            distancePrice = parameters.getDistanceNarrow();
        }

        return unitPrice * distancePrice;
    }
    private void calculateAmount()
    {
        try {
            amount = calculateDistance() * rental.getDuration();
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
}

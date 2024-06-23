package ana.epj2.model;

import ana.epj2.util.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Bill implements Serializable {
    public static final String BILL_STORE_PATH = "resources" + File.separator +"Bills";
    private static int id;
    private Rental rental;
    private double amount;
    private double totalPayment;
    private double discount = 0.0;
    private double discountProm = 0.0;
    private double distance;
    private double initialPrice;

    public Bill(Rental rental)
    {
        id++;
        this.rental = rental;
        calculateInitialPrice();
        calculateDistance();
        calculateDiscount();
        calculateDiscountProm();
        calculateAmount();
        calculateTotalPayment();
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountProm() {
        return discountProm;
    }

    public void setDiscountProm(double discountProm) {
        this.discountProm = discountProm;
    }

    private void calculateDistance() {
        distance = initialPrice * getDistancePrice();
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

    private void calculateInitialPrice()
    {
        if(rental.getVehicle().getMalfunction()==null)
        {
            try {
                initialPrice = rental.getDuration() * getUnitPrice();
            }catch(IllegalArgumentException ex)
            {
                ex.printStackTrace();
            }
        }else {
            initialPrice = 0.0;
        }
    }
    private void calculateAmount()
    {
        try {
            amount = distance;
        }catch (IllegalArgumentException ex)
        {
            amount = 0.0;
            ex.printStackTrace();
        }
    }

    private void calculateTotalPayment()
    {
        totalPayment = amount - discount - discountProm;
    }
    private double getUnitPrice() throws IllegalArgumentException
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
    public boolean createBillFile() {
        File destinationFolder = new File(BILL_STORE_PATH);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d_M_yyyy");
        String formattedDate = rental.getDateAndTime().format(formatter);
        boolean folderExists = destinationFolder.exists();
        if (!folderExists) {
            folderExists = destinationFolder.mkdir();
        }

        if (folderExists) {
            try {
                PrintWriter pw = new PrintWriter(new File(BILL_STORE_PATH + File.separator + id + "_" + rental.getUser().getName() + "_" + formattedDate));
                pw.println(this);
                pw.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    private void printBill()
    {

    }
    @Override
    public String toString()
    {
        return "[Bill for rental=" + rental +
                ", initialPrice=" + initialPrice +
                ", distance=" + distance +
                ", discount=" + discount+
                ", discountProm=" + discountProm+
                ", amount=" + amount+
                ", totalPayment=" + totalPayment+
                ", wider area=" + rental.wasInTheWiderPart + "]";
    }
}

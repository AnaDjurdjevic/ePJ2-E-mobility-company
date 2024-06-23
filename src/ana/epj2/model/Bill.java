package ana.epj2.model;

import ana.epj2.gui.Simulation;
import ana.epj2.util.Parameters;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Bill  {
    private static int id = 0;
    private int ID;
    private Rental rental;
    private double amount;
    private double totalPayment;
    private double discount = 0.0;
    private double discountProm = 0.0;
    private double distance;
    private double initialPrice;

    public Bill(Rental rental)
    {
        ID = id++;
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
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(Simulation.APP_CONFIG_PATH));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        String BILL_STORE_PATH = appProps.getProperty("BILL_STORE_PATH");
        File destinationFolder = new File(BILL_STORE_PATH);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d_M_yyyy");
        String formattedDate = rental.getDateAndTime().format(formatter);
        boolean folderExists = destinationFolder.exists();
        if (!folderExists) {
            folderExists = destinationFolder.mkdir();
        }
        if (folderExists) {
            try {
                PrintWriter pw = new PrintWriter(new File(BILL_STORE_PATH + File.separator + ID + "_" + rental.getUser().getName() + "_" + formattedDate + ".txt"));
                pw.println(this.formatBill());
                pw.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private String formatBill() {
        StringBuilder sb = new StringBuilder();
        sb.append("======================================\n");
        sb.append("            RENTAL BILL               \n");
        sb.append("======================================\n");
        sb.append("Bill ID: ").append(ID).append("\n");
        sb.append("Rental Date: ").append(rental.getDateAndTime()).append("\n");
        sb.append("---------------------------------------------------------------\n");
        sb.append("User Information\n");
        sb.append("======================================\n");
        sb.append("User: ").append(rental.getUser().getName()).append("\n");
        sb.append("Personal ID: ").append(rental.getUser().getIDCard()).append("\n");
        sb.append("Driver's License: ").append(rental.getUser().getDriversLicense()).append("\n");
        sb.append("---------------------------------------------------------------\n");
        sb.append("Vehicle Information\n");
        sb.append("======================================\n");
        sb.append("Vehicle ID: ").append(rental.getVehicle().getID()).append("\n");
        if(rental.getVehicle().getMalfunction() != null) {
            sb.append("Vehicle Malfunction Reason: ").append(rental.getVehicle().getMalfunction().getReason()).append("\n");
            sb.append("Vehicle Malfunction Date And Time: ").append(rental.getVehicle().getMalfunction().getDateAndTime()).append("\n");
        }
        sb.append("---------------------------------------------------------------\n");
        sb.append("Rental Details\n");
        sb.append("======================================\n");
        sb.append("Start Location: ").append(rental.getStartLocation()).append("\n");
        sb.append("End Location: ").append(rental.getEndLocation()).append("\n");
        sb.append("Duration: ").append(rental.getDuration()).append(" seconds\n");
        sb.append("---------------------------------------------------------------\n");
        sb.append("Price Details\n");
        sb.append("======================================\n");
        sb.append("Initial Price: ").append(initialPrice).append("\n");
        sb.append("Distance Price: ").append(distance).append("\n");
        sb.append("Discount: ").append(discount).append("\n");
        sb.append("Promotion Discount: ").append(discountProm).append("\n");
        sb.append("Total Amount: ").append(amount).append("\n");
        sb.append("Total Payment: ").append(totalPayment).append("\n");
        sb.append("======================================\n");
        return sb.toString();
    }
    public void printBill()
    {
        createBillFile();
    }
    public double getRepairCost() {
        double repairCost = 0.0;
        if (rental.getVehicle().getMalfunction() != null) {
            repairCost = rental.getVehicle().getRepairCoefficient() * rental.getVehicle().getPurchasePrice();
        }
        return repairCost;
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

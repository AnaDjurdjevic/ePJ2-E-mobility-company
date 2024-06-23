package ana.epj2.util;

import ana.epj2.gui.Simulation;
import java.io.FileInputStream;
import java.util.Properties;
/**
 * Singleton class that loads and provides access to application parameters.
 */
public class Parameters {
    private static Parameters instance;
    private double carUnitPrice;
    private double bikeUnitPrice;
    private double scooterUnitPrice;
    private double distanceNarrow;
    private double distanceWide;
    private double discount;
    private double discountProm;
    /**
     * Private constructor that loads parameters from the properties file.
     */
    private Parameters()
    {
        loadParameters();
    }
    /**
     * Returns the singleton instance of the Parameters class.
     *
     * @return the singleton instance of Parameters
     */
    public static Parameters getInstance() {
        if (instance == null) {
            synchronized (Parameters.class) {
                if (instance == null) {
                    instance = new Parameters();
                }
            }
        }
        return instance;
    }
    /**
     * Loads parameters from the application properties file.
     */
    private void loadParameters() {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(Simulation.APP_CONFIG_PATH));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        carUnitPrice = Double.parseDouble(appProps.getProperty("CAR_UNIT_PRICE"));
        bikeUnitPrice = Double.parseDouble(appProps.getProperty("BIKE_UNIT_PRICE"));
        scooterUnitPrice = Double.parseDouble(appProps.getProperty("SCOOTER_UNIT_PRICE"));
        distanceNarrow = Double.parseDouble(appProps.getProperty("DISTANCE_NARROW"));
        distanceWide = Double.parseDouble(appProps.getProperty("DISTANCE_WIDE"));
        discount = Double.parseDouble(appProps.getProperty("DISCOUNT"));
        discountProm = Double.parseDouble(appProps.getProperty("DISCOUNT_PROM"));
    }
    public double getCarUnitPrice() {
        return carUnitPrice;
    }
    public void setCarUnitPrice(double carUnitPrice) {
        this.carUnitPrice = carUnitPrice;
    }
    public double getBikeUnitPrice() {
        return bikeUnitPrice;
    }
    public void setBikeUnitPrice(double bikeUnitPrice) {
        this.bikeUnitPrice = bikeUnitPrice;
    }
    public double getScooterUnitPrice() {
        return scooterUnitPrice;
    }
    public void setScooterUnitPrice(double scooterUnitPrice) {
        this.scooterUnitPrice = scooterUnitPrice;
    }
    public double getDistanceNarrow() {
        return distanceNarrow;
    }
    public void setDistanceNarrow(double distanceNarrow) {
        this.distanceNarrow = distanceNarrow;
    }
    public double getDistanceWide() {
        return distanceWide;
    }
    public void setDistanceWide(double distanceWide) {
        this.distanceWide = distanceWide;
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
}
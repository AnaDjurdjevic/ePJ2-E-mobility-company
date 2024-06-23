package ana.epj2.model;

import java.time.LocalDate;

/**
 * Represents a scooter in the e-mobility system.
 */
public class Car extends Vehicle{
    private LocalDate purchaseDate;
    private String description;
    /**
     * Constructs a new Car with the specified details.
     *
     * @param ID the unique identifier for the car
     * @param manufacturer the manufacturer of the car
     * @param model the model of the car
     * @param purchasePrice the purchase price of the car
     * @param purchaseDate the purchase date of the car
     * @param description a description of the car
     */
    public Car(String ID, String manufacturer, String model,
               double purchasePrice, LocalDate purchaseDate, String description)
    {
        super(ID,manufacturer,model,purchasePrice,true, null);
        this.purchaseDate = purchaseDate;
        this.description = description;
        this.repairCoefficient = 0.07;

    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
    @Override
    public String toString()
    {
        return "Car " + super.toString()
                + ", purchase date=" + purchaseDate
                + ", description=" + description + "]";
    }

}

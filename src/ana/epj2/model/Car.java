package ana.epj2.model;

import java.time.LocalDate;

public class Car extends Vehicle{
    private LocalDate purchaseDate;
    private String description;

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

    @Override
    public String toString()
    {
        return "Car " + super.toString()
                + ", purchase date=" + purchaseDate
                + ", description=" + description + "]";
    }

}

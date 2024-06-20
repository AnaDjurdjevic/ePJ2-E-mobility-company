package model;

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

    }

    @Override
    public String toString()
    {
        return "Automobil " + super.toString()
                + ", datum nabavke=" + purchaseDate
                + ", opis=" + description + "]";
    }

}

package ana.epj2.model;

import java.util.Random;

public class User {

    private String name;
    private String IDCard;
    private String driversLicense;
    private String passport;

    public User(String name)
    {
        this.name = name;
        IDCard = generateDocumentation(9);
        driversLicense = generateDocumentation(9);
        passport = generateDocumentation(9);
    }
    private String generateDocumentation(int num)
    {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < num; i++)
        {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getDriversLicense() {
        return driversLicense;
    }

    public void setDriversLicense(String driversLicense) {
        this.driversLicense = driversLicense;
    }

    @Override
    public String toString()
    {
        return "User "+ name;
    }
}

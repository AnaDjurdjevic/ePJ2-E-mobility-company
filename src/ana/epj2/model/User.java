package ana.epj2.model;

import java.util.Random;
/**
 * Represents a user in the e-mobility system.
 */
public class User {

    private String name;
    private String IDCard;
    private String driversLicense;
    private String passport;

    /**
     * Constructs a new User with the specified details.
     *
     * @param name the name of the user
     */
    public User(String name)
    {
        this.name = name;
        IDCard = generateDocumentation(9);
        driversLicense = generateDocumentation(9);
        passport = generateDocumentation(9);
    }
    /**
     * Generates a string of random digits with the specified length.
     *
     * @param num the number of random digits to generate
     * @return a string containing {@code num} random digits
     */
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

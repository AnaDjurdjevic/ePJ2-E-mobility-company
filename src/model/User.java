package model;

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

    @Override
    public String toString()//TODO za sad ovako
    {
        return "User "+ name;
    }
}

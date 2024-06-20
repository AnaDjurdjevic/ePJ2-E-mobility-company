package model;

import java.time.LocalDateTime;

public class Malfunction {

    private String reason;
    private LocalDateTime dateAndTime;

    public Malfunction(String reason, LocalDateTime dateAndTime)
    {
        this.reason = reason;
        this.dateAndTime = dateAndTime;
    }

    @Override
    public String toString()
    {
        return "razlog kvara="+ reason + ", datum i vrijeme kvara=" + dateAndTime;
    }
}

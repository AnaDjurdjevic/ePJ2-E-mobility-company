package ana.epj2.model;

import java.time.LocalDateTime;

public class Malfunction {

    private String reason;
    private LocalDateTime dateAndTime;

    public Malfunction(String reason, LocalDateTime dateAndTime)
    {
        this.reason = reason;
        this.dateAndTime = dateAndTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @Override
    public String toString()
    {
        return " malfunction reason="+ reason + ", date and time of malfunction=" + dateAndTime;
    }
}

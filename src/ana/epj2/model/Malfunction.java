package ana.epj2.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a malfunction of the vehicle in the e-mobility system.
 */
public class Malfunction implements Serializable {

    private String reason;
    private LocalDateTime dateAndTime;
    /**
     * Constructs a new Malfunction with the specified details.
     *
     * @param reason the reason of the malfunction
     * @param dateAndTime the date and time when the malfunction occurred
     */
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
    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
    @Override
    public String toString()
    {
        return " malfunction reason="+ reason + ", date and time of malfunction=" + dateAndTime;
    }
}

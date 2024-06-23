package ana.epj2.exceptions;

/**
 * Exception thrown when a duplicate value is encountered.
 */
public class DuplicateValueException extends Exception{
    /**
     * Constructs a new DuplicateValueException with no specified detail message.
     */
    public DuplicateValueException() {
        super("Vehicle already exists!");
    }
    /**
     * Constructs a new DuplicateValueException with the specified detail message.
     *
     * @param msg the detail message
     */
    public DuplicateValueException(String msg) {
        super(msg);
    }
}

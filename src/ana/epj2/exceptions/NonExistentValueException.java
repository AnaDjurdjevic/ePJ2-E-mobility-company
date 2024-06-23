package ana.epj2.exceptions;
/**
 * Exception thrown when a requested value does not exist.
 */
public class NonExistentValueException extends Exception{
    /**
     * Constructs a new NonExistentValueException with no specified detail message.
     */
    public NonExistentValueException() {
        super("Non Existent Value!");
    }

    /**
     * Constructs a new NonExistentValueException with the specified detail message.
     *
     * @param msg the detail message
     */
    public NonExistentValueException(String msg) {
        super(msg);
    }
}
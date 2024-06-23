package ana.epj2.exceptions;
/**
 * Exception thrown when an input format is incorrect.
 */
public class IncorrectInputFormatException extends Exception{
    /**
     * Constructs a new IncorrectInputFormatException with no specified detail message.
     */
    public IncorrectInputFormatException()
    {
        super("Incorrect input format!");
    }
    /**
     * Constructs a new IncorrectInputFormatException with the specified detail message.
     *
     * @param msg the detail message
     */
    public IncorrectInputFormatException(String msg)
    {
        super(msg);
    }
}

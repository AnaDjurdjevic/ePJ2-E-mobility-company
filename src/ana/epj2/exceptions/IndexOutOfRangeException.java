package ana.epj2.exceptions;
/**
 * Exception thrown when an index is out of the acceptable range.
 */
public class IndexOutOfRangeException extends Exception{
    /**
     * Constructs a new IndexOutOfRangeException with no specified detail message.
     */
    public IndexOutOfRangeException()
    {
        super("Index is out of range!");
    }
    /**
     * Constructs a new IndexOutOfRangeException with the specified detail message.
     *
     * @param msg the detail message
     */
    public IndexOutOfRangeException(String msg)
    {
        super(msg);
    }
}

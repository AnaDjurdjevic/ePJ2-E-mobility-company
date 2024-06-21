package ana.epj2.exceptions;

public class IndexOutOfRangeException extends Exception{
    public IndexOutOfRangeException()
    {
        super("Index is out of range!");
    }
    public IndexOutOfRangeException(String msg)
    {
        super(msg);
    }
}

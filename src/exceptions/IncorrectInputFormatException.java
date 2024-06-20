package exceptions;

public class IncorrectInputFormatException extends Exception{
    public IncorrectInputFormatException()
    {
        super("Incorrect input format!");
    }
    public IncorrectInputFormatException(String msg)
    {
        super(msg);
    }
}

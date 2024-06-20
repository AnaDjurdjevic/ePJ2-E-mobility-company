package exceptions;

public class DuplicateValueException extends Exception{
    public DuplicateValueException() {
        super("Vehicle already exists!");
    }

    public DuplicateValueException(String msg) {
        super(msg);
    }
}

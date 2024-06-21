package ana.epj2.exceptions;
public class NonExistentValueException extends Exception{
    public NonExistentValueException() {
        super("Non Existent Value!");
    }

    public NonExistentValueException(String msg) {
        super(msg);
    }
}
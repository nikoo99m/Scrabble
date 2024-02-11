package pij.main.exceptions;

public class OutOfRangeBoardValueException extends Exception{
    public OutOfRangeBoardValueException() {
        super("Supplied value for board field is out of range.");
    }
}
package pij.main.exceptions;
/**
 * Exception thrown when a supplied value for a board field is out of range.
 */
public class OutOfRangeBoardValueException extends Exception{
    public OutOfRangeBoardValueException() {
        super("Supplied value for board field is out of range.");
    }
}
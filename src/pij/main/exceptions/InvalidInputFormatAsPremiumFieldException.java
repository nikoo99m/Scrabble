package pij.main.exceptions;
/**
 * Exception thrown when an invalid input format is provided for a premium board field.
 */
public class InvalidInputFormatAsPremiumFieldException extends Exception{
    public InvalidInputFormatAsPremiumFieldException(String value) {
        super("Invalid value format provided for premium board field: " + value);
    }
}
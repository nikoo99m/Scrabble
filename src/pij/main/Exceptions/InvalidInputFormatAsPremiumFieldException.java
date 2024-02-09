package pij.main.Exceptions;

public class InvalidInputFormatAsPremiumFieldException extends Exception{
    public InvalidInputFormatAsPremiumFieldException(String value) {
        super("Invalid value format provided for premium board field: " + value);
    }
}
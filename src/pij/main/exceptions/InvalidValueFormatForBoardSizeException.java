package pij.main.exceptions;

/**
 * Exception thrown when an invalid value format is supplied for the board size.
 */
public class InvalidValueFormatForBoardSizeException extends Exception{
    public InvalidValueFormatForBoardSizeException(String size) {
        super("Invalid value format supplied for board size: " + size);
    }
}
package pij.main.Exceptions;

public class InvalidValueFormatForBoardSizeException extends Exception{
    public InvalidValueFormatForBoardSizeException(String size) {
        super("Invalid value format supplied for board size: " + size);
    }
}
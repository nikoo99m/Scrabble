package pij.main.exceptions;

public class InvalidValueFormatForBoardSizeException extends Exception{
    public InvalidValueFormatForBoardSizeException(String size) {
        super("Invalid value format supplied for board size: " + size);
    }
}
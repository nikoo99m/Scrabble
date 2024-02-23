package pij.main.exceptions;
/**
 * Exception thrown when an invalid board size is provided.
 */
public class InvalidBoardSizeException extends Exception{
    public InvalidBoardSizeException(int size) {
        super("An invalid board size was provided: " + size);
    }
}
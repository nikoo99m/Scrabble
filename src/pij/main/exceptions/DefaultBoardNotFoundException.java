package pij.main.exceptions;
/**
 * Exception thrown when there is an issue with loading the default board.
 * This exception indicates that the default board could not be found.
 */
public class DefaultBoardNotFoundException extends Exception{
    public DefaultBoardNotFoundException() {
        super("There's an issue with loading the default board.");
    }
}
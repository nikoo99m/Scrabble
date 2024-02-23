package pij.main.exceptions;
/**
 * Exception thrown when the content of the game board does not match the specified board size.
 */
public class BoardContentSizeMismatchException extends Exception{
    public BoardContentSizeMismatchException() {
        super("The board content does not match the specified board size.");
    }
}
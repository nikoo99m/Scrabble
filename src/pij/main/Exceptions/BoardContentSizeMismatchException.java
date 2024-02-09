package pij.main.Exceptions;

public class BoardContentSizeMismatchException extends Exception{
    public BoardContentSizeMismatchException() {
        super("The board content does not match the specified board size.");
    }
}
package pij.main.Exceptions;

public class InvalidBoardSizeException extends Exception{
    public InvalidBoardSizeException(int size) {
        super("An invalid board size was provided: " + size);
    }
}
package pij.main.exceptions;

public class DefaultBoardNotFoundException extends Exception{
    public DefaultBoardNotFoundException() {
        super("There's an issue with loading the default board.");
    }
}
package pij.main.commands;

import pij.main.services.Board;
import pij.main.exceptions.InvalidInputFormatAsPremiumFieldException;
import pij.main.exceptions.OutOfRangeBoardValueException;
import pij.main.models.squares.Square;

/**
 * Abstract class representing a square on the game board.
 * Subclasses are responsible for creating the specific type of square.
 */
public abstract class SetFieldSquare {
    private final Board board;
    private final int i;
    private final int j;

    public SetFieldSquare(Board board, int i, int j) {
        this.board = board;
        this.i = i;
        this.j = j;
    }

    /**
     * Abstract method to create the specific type of square.
     *
     * @return The created square.
     * @throws InvalidInputFormatAsPremiumFieldException If the input format of the field is invalid.
     * @throws OutOfRangeBoardValueException             If the field value is out of range.
     */
    protected abstract Square createSquare()
            throws InvalidInputFormatAsPremiumFieldException, OutOfRangeBoardValueException;

    /**
     * Sets the field square on the game board.
     *
     * @throws InvalidInputFormatAsPremiumFieldException If the input format of the field is invalid.
     * @throws OutOfRangeBoardValueException             If the field value is out of range.
     */
    public void setFieldSquare()
            throws InvalidInputFormatAsPremiumFieldException, OutOfRangeBoardValueException {
        board.letter[i][j] = createSquare();
    }
}

package pij.main.commands;

import pij.main.services.Board;
import pij.main.exceptions.InvalidInputFormatAsPremiumFieldException;
import pij.main.exceptions.OutOfRangeBoardValueException;
import pij.main.models.Squares.Square;

public abstract class SetFieldSquare
{
    private final Board board;
    private final int i;
    private final int j;

    public SetFieldSquare(Board board, int i, int j) {
        this.board = board;
        this.i = i;
        this.j = j;
    }

    protected abstract Square createSquare()
            throws InvalidInputFormatAsPremiumFieldException, OutOfRangeBoardValueException;
    public void setFieldSquare()
            throws InvalidInputFormatAsPremiumFieldException, OutOfRangeBoardValueException {
        board.letter[i][j] = createSquare();
    }
}

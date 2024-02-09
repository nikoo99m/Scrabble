package pij.main.Commands;

import pij.main.Board;
import pij.main.Exceptions.InvalidInputFormatAsPremiumFieldException;
import pij.main.Exceptions.OutOfRangeBoardValueException;
import pij.main.Squares.Square;

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

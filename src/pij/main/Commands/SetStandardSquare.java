package pij.main.Commands;

import pij.main.Board;
import pij.main.Squares.Square;
import pij.main.Squares.StandardSquare;

public class SetStandardSquare extends SetFieldSquare
{

    public SetStandardSquare(Board board, int i, int j) {
        super(board, i, j);
    }

    @Override
    protected Square createSquare() {
        return new StandardSquare();
    }
}

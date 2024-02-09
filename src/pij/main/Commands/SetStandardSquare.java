package pij.main.Commands;

import pij.main.Board;
import pij.main.Square;
import pij.main.StandardSquare;

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

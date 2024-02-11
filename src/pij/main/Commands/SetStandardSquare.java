package pij.main.Commands;

import pij.main.Services.Board;
import pij.main.Models.Squares.Square;
import pij.main.Models.Squares.StandardSquare;

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

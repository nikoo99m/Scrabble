package pij.main.commands;

import pij.main.services.Board;
import pij.main.models.Squares.Square;
import pij.main.models.Squares.StandardSquare;

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

package pij.main.Commands;

import pij.main.Board;
import pij.main.Squares.BracketSquare;
import pij.main.Squares.Square;

public class SetBracketSquare extends PremiumSquare
{
    private final String field;
    public SetBracketSquare(Board board, int i, int j, String field) {
        super(board, i, j, field, "\\((-?\\d+)\\)");
        this.field = field;
    }

    @Override
    public Square getPremiumSquare(int number) {
        return new BracketSquare(number);
    }
}

package pij.main.Commands;

import pij.main.Board;
import pij.main.Squares.CurlySquare;
import pij.main.Squares.Square;

public class SetCurlySquare extends PremiumSquare
{
    private final String field;
    public SetCurlySquare(Board board, int i, int j, String field) {
        super(board, i, j, field, "\\{(-?\\d+)\\}");
        this.field = field;
    }

    @Override
    public Square getPremiumSquare(int number) {
        return new CurlySquare(number);
    }
}

package pij.main.Commands;

import pij.main.Services.Board;
import pij.main.Models.Squares.CurlySquare;
import pij.main.Models.Squares.Square;

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

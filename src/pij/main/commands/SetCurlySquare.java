package pij.main.commands;

import pij.main.services.Board;
import pij.main.models.Squares.CurlySquare;
import pij.main.models.Squares.Square;

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

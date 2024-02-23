package pij.main.commands;

import pij.main.services.Board;
import pij.main.models.squares.CurlySquare;
import pij.main.models.squares.Square;
/**
 * Class representing a command to set a curly square(Premium word square) on the game board.
 * A curly square is a type of premium square that provides special bonuses or penalties to players.
 */
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

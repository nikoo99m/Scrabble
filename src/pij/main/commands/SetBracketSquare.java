package pij.main.commands;

import pij.main.services.Board;
import pij.main.models.squares.BracketSquare;
import pij.main.models.squares.Square;
/**
 * Class representing a command to set a bracket square(premium letter square) on the game board.
 * A bracket square is a type of premium square that provides special bonuses or penalties to players.
 */
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

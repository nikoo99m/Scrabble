package pij.main.commands;

import pij.main.services.Board;
import pij.main.models.squares.Square;
import pij.main.models.squares.StandardSquare;
/**
 * Class representing a command to set a standard square on the game board.
 * A standard square does not have any special attributes or bonuses.
 */
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

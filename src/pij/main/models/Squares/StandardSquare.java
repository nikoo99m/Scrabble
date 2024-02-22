package pij.main.models.Squares;

import pij.main.utils.StringHelper;

/**
 * Represents a standard square on the game board.
 * Each standard square has a default multiplier of 1.
 */
public class StandardSquare extends Square {
    /**
     * Constructs a standard square with a default multiplier of 1.
     */
    public StandardSquare() {
        this.multiplier = 1;
    }

    /**
     * Generates the text representation of this standard square, which is a single period (".").
     *
     * @return the text representation of this standard square
     */
    @Override
    public String generateSquareText() {
        return StringHelper.set5(".");
    }
}

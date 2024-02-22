package pij.main.models.Squares;

import pij.main.utils.StringHelper;

/**
 * Represents a premium letter square in the game board.
 */
public class BracketSquare extends Square {
    /**
     * Constructor.
     *
     * @param mul The multiplier associated with the bracket square.
     */
    public BracketSquare(int mul)
    {
        this.multiplier = mul;
    }
    /**
     * Generates the text representation of the bracket square.
     *
     * @return A string representing the bracket square with its multiplier.
     */
    @Override public String generateSquareText(){
        return StringHelper.set5("(" + multiplier + ")");
    }
}

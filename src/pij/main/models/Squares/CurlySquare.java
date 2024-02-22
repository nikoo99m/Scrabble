package pij.main.models.Squares;

import pij.main.utils.StringHelper;
/**
 * Represents a premium word square in the game. Extends the Square class.
 * Each CurlySquare has a multiplier value that affects the player's score.
 */
public class CurlySquare extends Square {
    /**
     * Constructor.
     * @param mul the multiplier value for this CurlySquare.
     */
    public CurlySquare(int mul)
    {
        this.multiplier = mul;
    }
    /**
     * Generates the text representation of this CurlySquare.
     * @return A string representing CurlySquare with its multiplier.
     */
    @Override public String generateSquareText(){
        return StringHelper.set5("{" + multiplier + "}");
    }
}


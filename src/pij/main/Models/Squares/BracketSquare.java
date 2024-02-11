package pij.main.Models.Squares;

import pij.main.Utils.StringHelper;

public class BracketSquare extends Square {

    public BracketSquare(int mul)
    {
        this.multiplier = mul;
    }
    @Override public String generateSquareText(){
        return StringHelper.set5("(" + multiplier + ")");
    }
}

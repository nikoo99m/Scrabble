package pij.main.Models.Squares;

import pij.main.Utils.StringHelper;

public class StandardSquare extends Square {

    public StandardSquare()
    {
        this.multiplier = 1;
    }
    @Override public String generateSquareText(){
        return StringHelper.set5(".");
    }
}

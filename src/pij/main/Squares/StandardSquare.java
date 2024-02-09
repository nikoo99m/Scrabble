package pij.main.Squares;

import pij.main.StringHelper;

public class StandardSquare extends Square {

    public StandardSquare()
    {
        this.multiplier = 1;
    }
    @Override public String generateSquareText(){
        return StringHelper.set5(".");
    }
}

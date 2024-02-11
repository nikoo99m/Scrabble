package pij.main.models.Squares;

import pij.main.utils.StringHelper;

public class CurlySquare extends Square {

    public CurlySquare(int mul)
    {
        this.multiplier = mul;
    }
    @Override public String generateSquareText(){
        return StringHelper.set5("{" + multiplier + "}");
    }
}


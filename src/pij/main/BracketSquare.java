package pij.main;

public class BracketSquare extends Square{

    public BracketSquare(int mul)
    {
        this.multiplier = mul;
    }
    @Override public String generateSquareText(){
        return "(" + multiplier + ")";
    }
}

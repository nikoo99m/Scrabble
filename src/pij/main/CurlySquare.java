package pij.main;

public class CurlySquare extends Square{

    public CurlySquare(int mul)
    {
        this.multiplier = mul;
    }
    @Override public String generateSquareText(){
        return "{" + multiplier + "}";
    }
}

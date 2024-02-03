package pij.main;

public abstract class Square{
    int multiplier;
    Tile tile;
    public void setTile(Tile givenTile)
    {
        this.tile = givenTile;
    }
    @Override
    public String toString(){
        if(tile == null)
            return generateSquareText();
        else
            return tile.ToString();
    }
    public abstract String generateSquareText();
}

package pij.main;

public abstract class Square{
    int multiplier;
    Tile tile;
    public void setTile(Tile givenTile)
    {
        this.tile = givenTile;
    }
    public String ToString(){
        if(tile == null)
            return generateSquareText();
        else
            return tile.ToString();
    }
    public abstract String generateSquareText();
}

package pij.main.models.Squares;

import pij.main.models.Tile;

public abstract class Square{
    public int multiplier;
    public Tile tile;
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

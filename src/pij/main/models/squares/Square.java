package pij.main.models.squares;

import pij.main.models.Tile;

/**
 * Represents a square on the game board.
 * Squares can either contain a tile or be empty.
 * Each square may also have a multiplier associated with it.
 * <p>
 * An instance contains
 * multiplier
 * tile
 */
public abstract class Square{
    public int multiplier;
    public Tile tile;
    /**
     * Sets the tile of this square to the given tile.
     * @param givenTile the tile to be set.
     */
    public void setTile(Tile givenTile)
    {
        this.tile = givenTile;
    }

    /**
     * Returns the string representation of this square.
     * If the square contains a tile, returns the tile's string representation.
     * Otherwise, generates and returns the square's text representation.
     * @return the string representation of this square
     */
    @Override
    public String toString(){
        if(tile == null)
            return generateSquareText();
        else
            return tile.ToString();
    }
    /**
     * Generates the text representation of this square.
     * Subclasses must implement this method to provide the specific format of the square's text.
     * @return the text representation of this square
     */
    public abstract String generateSquareText();
}

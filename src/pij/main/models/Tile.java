package pij.main.models;

import pij.main.utils.StringHelper;

/**
 * Represents a tile with a character and a value.
 * <p>
 * An instance contains
 * - character
 * - value
 */
public class Tile {
    public String character;
    public int value;

    /**
     * Constructs a new Tile with the given character and value.
     *
     * @param character the character of the tile
     * @param value     the value of the tile
     */
    public Tile(String character, int value) {
        this.character = character;
        this.value = value;
    }

    /**
     * Returns a string representation of the tile.
     *
     * @return a string representing the tile's character and value
     */
    public String ToString() {
        return StringHelper.set5(character + value);
    }
}

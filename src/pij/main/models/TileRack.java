package pij.main.models;

import pij.main.models.methodReturns.WildCardReturn;

import java.util.Arrays;
import java.util.Objects;
/**
 * Represents a tile rack.
 * The tile rack can hold up to 7 tiles.
 */
public class TileRack {
    public Tile[] Rack = new Tile[7];
    /**
     * Checks if the tile rack is empty.
     *
     * @return true if the tile rack is empty, false otherwise.
     */
    public boolean isEmpty(){
        for (int i = 0; i < Rack.length; i++)
        {
            if(Rack[i] != null)
                return false;
        }
        return true;
    }
    /**
     * Checks if there is at least one empty slot in the tile rack.
     *
     * @return true if there is at least one empty slot, false otherwise.
     */
    public boolean hasEmpty()
    {
        return Arrays.stream(Rack).anyMatch(Objects::isNull);
    }
    /**
     * Adds a tile to the tile rack where there is an empty slot.
     *
     * @param tile the tile to be added to the rack.
     * @return true if the tile was successfully added, false if the rack is full.
     */
    public boolean add(Tile tile) {
        for (int i = 0; i < 7; i++) {
            if (Rack[i] == null) {
                Rack[i] = tile;
                return true;
            }
        }
        return false;
    }
    /**
     * Generates a string representation of the tile rack.
     * Each tile is represented by its character and value enclosed in square brackets.
     * Empty slots are represented by "[ ]".
     *
     * @return a string representation of the tile rack.
     */
    @Override
    public String toString() {
        String m = "";
        for(int i = 0 ; i < Rack.length ; i++){
            if(Rack[i] != null) {
                String n = Rack[i].character;
                int x = Rack[i].value;
                m = m + "[" + n + x + "]";
            }
            else
                m = m + "[ ]";
            if(i + 1 != Rack.length)
                m += ", ";
        }
        return m;
    }
    public Tile pick(int index) {
        return Rack[index];
    }
    public WildCardReturn wildCardExists() {
        for (int i = 0; i < Rack.length; i++) {
            if (Rack[i] != null && Rack[i].character.equals("_")) {
                return new WildCardReturn(i, true);
            }
        }
        return new WildCardReturn(-1, false);
    }
}
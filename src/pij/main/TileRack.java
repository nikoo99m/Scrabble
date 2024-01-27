package pij.main;

import java.lang.reflect.Array;

public class TileRack {
    Tile[] Rack = new Tile[7];

    public boolean add(Tile tile) {
        for (int i = 0; i < 7; i++) {
            if (Rack[i] == null) {
                Rack[i] = tile;
                return true;
            }
        }
        return false;
    }

    public Tile pick(int index) {
        return Rack[index];
    }
}

package pij.main;

import java.lang.reflect.Array;

public class TileRack {
    public Tile[] Rack = new Tile[7];

    public boolean add(Tile tile) {
        for (int i = 0; i < 7; i++) {
            if (Rack[i] == null) {
                Rack[i] = tile;
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String m = "";
        for(int i = 0 ; i < Rack.length ; i++){
            String n = Rack[i].character;
            int x = Rack[i].value;
            m = m + "[" + n + x + "]";

            if(i + 1 != Rack.length)
                m += ", ";
        }
        return m;
    }

    public Tile pick(int index) {
        return Rack[index];
    }
}
package pij.main.models;

import pij.main.models.MethodReturns.WildCardReturn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TileRack {
    public Tile[] Rack = new Tile[7];

    public boolean isEmpty(){
        for (int i = 0; i < Rack.length; i++)
        {
            if(Rack[i] != null)
                return false;
        }
        return true;
    }
    public boolean hasEmpty()
    {
        return Arrays.stream(Rack).anyMatch(Objects::isNull);
    }
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
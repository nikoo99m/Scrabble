package pij.main.Models;

import pij.main.Utils.StringHelper;

public class Tile {
    public String character;
    public int value;
    public Tile(String character, int value)
    {
        this.character = character;
        this.value = value;
    }
    public String ToString(){
        return StringHelper.set5(character + value);
    }
}

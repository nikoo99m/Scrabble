package pij.main;

public class Tile {
    String character;
    int value;
    public Tile(String character, int value)
    {
        this.character = character;
        this.value = value;
    }
    public String ToString(){
        return character + value;
    }
}

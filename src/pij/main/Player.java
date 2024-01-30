package pij.main;

import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {
    public TileRack playerRack;
    public TileBag tileBag;
    public Board board;

    public void Player(TileBag tileBag,Board board) {
        this.tileBag = tileBag;
        this.board =board;
        playerRack= new TileRack();
    }

    public void fillTileRack() {
        while (playerRack.add(tileBag.randomPop()))
            ;
    }
    private int get(char startChar){
           return 'a' - startChar;
    }
    private boolean isVertical(String location){
        if (Character.isDigit(location.charAt(0))){
           return false;
        }
        return true;
    }
    public class Location{
        public String numberPart;
        public String charPart;

        public Location(String numberPart , String charPart){
            this.charPart = charPart;
            this.numberPart = numberPart;
        }
    }
    private Location boz(String inputString){
        Pattern pattern = Pattern.compile("([0-9]+)([a-zA-Z]+)");

        // Create a Matcher object
        Matcher matcher = pattern.matcher(inputString);

        // Check if the pattern matches the input string
        if (matcher.matches()) {
            // Extract the number and character
            String numberPart = matcher.group(1);
            String characterPart = matcher.group(2);

           return new Location(numberPart , characterPart);
        }
        throw new RuntimeException("No location found.");
    }
    public Tile fetchTileFromRack(String c){
        for (int i = 0; i < playerRack.Rack.length; i++) {
            Tile tile = playerRack.Rack[i];
            if (tile.character.equals(c))
                playerRack.Rack[i] = null;
            return tile;
        }
        throw new RuntimeException("Could not find player's choice in Rack.");
    }
    public void move(){
        Scanner scan = new Scanner(System.in);
        String moveAsString = scan.nextLine();
        String[] parts = moveAsString.split(",");
        String word = parts[0];
        String location = parts[1];
        boolean vertical = isVertical(location);
        if (vertical == false){
            Location c= boz(location);
            int i = Integer.parseInt(c.numberPart);
            int j = get(c.charPart.charAt(0));
           if (board.letter[i][j].tile == null){
               board.letter[i][j].setTile(fetchTileFromRack(word));
           }
        }





    }

}


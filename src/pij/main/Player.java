package pij.main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {
    public TileRack playerRack;
    public TileBag tileBag;
    public Board board;

    public Player(TileBag tileBag, Board board) {
        this.tileBag = tileBag;
        this.board = board;
        playerRack = new TileRack();
    }

    public void fillTileRack() {
        while (playerRack.add(tileBag.randomPop()))
            ;
    }

    private int get(char startChar) {
        if (startChar >= 'a' && startChar <= 'z') {
            return startChar - 'a';
        } else {
            // Handle invalid input
            throw new IllegalArgumentException("Invalid input: " + startChar);
        }
    }


    private boolean isVertical(String location) {
        if (Character.isDigit(location.charAt(0))) {
            return false;
        }
        return true;
    }

    public class Location {
        public String numberPart;
        public String charPart;

        public Location(String numberPart, String charPart) {
            this.charPart = charPart;
            this.numberPart = numberPart;
        }
    }

    private Location getStartingLocation(String inputString, boolean useNumberFirst) {
        Pattern pattern = Pattern.compile("([a-zA-Z]+)([0-9]+)|([0-9]+)([a-zA-Z]+)");

        // Create a Matcher object
        Matcher matcher = pattern.matcher(inputString);

        // Check if the pattern matches the input string
        if (matcher.matches()) {
            // Extract the number and character
            String numberPart;
            String characterPart;

            if (useNumberFirst) {
                numberPart = matcher.group(3);
                characterPart = matcher.group(4);
            } else {
                numberPart = matcher.group(2);
                characterPart = matcher.group(1);
            }

            // Create a Location object with the specified ordering
            return new Location(numberPart, characterPart);
        }

        throw new RuntimeException("No location found.");
    }



    public Tile fetchTileFromRack(String c) {
        for (int i = 0; i < playerRack.Rack.length; i++) {
            Tile tile = playerRack.Rack[i];
            if (tile != null && tile.character.equals(c)){
                playerRack.Rack[i] = null;
                return tile;
            }
        }
        throw new RuntimeException("Could not find player's choice in Rack.");
    }

    public void move() {
        System.out.println(playerRack.toString());
        Scanner scan = new Scanner(System.in);
        String moveAsString = scan.nextLine();
        String[] parts = moveAsString.split(",");
        String tileSelection = parts[0];
        String startingPoint = parts[1];

        boolean vertical = isVertical(startingPoint);
        Location location;

        if (!vertical) {
            location = getStartingLocation(startingPoint, true);
        } else {
            location = getStartingLocation(startingPoint, false);
        }

        int i = Integer.parseInt(location.numberPart);
        int j = get(location.charPart.charAt(0));

        for (int m = 0; m < tileSelection.length(); m++) {
            String s = String.valueOf(tileSelection.charAt(m));
            if (board.letter[i][j].tile == null) {
                Tile tile = fetchTileFromRack(s);
                board.letter[i][j].setTile(tile);
            }
            if (!vertical) {
                j++;
            } else {
                i++;
            }
        }
    }
}


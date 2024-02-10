package pij.main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {
    public TileRack playerRack;
    public TileBag tileBag;
    public Board board;
    public Dictionary dictionary;
    private boolean isFirstMove;
    public int score;


    public Player(TileBag tileBag, Board board, Dictionary dictionary, boolean isFirstMove) {
        this.tileBag = tileBag;
        this.board = board;
        this.dictionary = dictionary;
        playerRack = new TileRack();
        this.isFirstMove = isFirstMove;

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
            if (tile != null && tile.character.equals(c)) {
                playerRack.Rack[i] = null;
                return tile;
            }
        }
        throw new RuntimeException("Could not find player's choice in Rack.");
    }

    public MoveReturn move() {
        System.out.println(playerRack.toString());
        Scanner scan = new Scanner(System.in);
        String moveAsString = scan.nextLine();
        String[] parts = moveAsString.split(",");
        String tileSelection = parts[0];
        String startingPoint = parts[1];

        boolean vertical = isVertical(startingPoint);

        Location location = getLocation(vertical, startingPoint);

        int i = Integer.parseInt(location.numberPart);
        int j = get(location.charPart.charAt(0));

        boolean collidesWithBoardEdge = checkCollidesWithBoardEdge(tileSelection, i, j, startingPoint);
        if (collidesWithBoardEdge) {
            System.out.println("Invalid move detected. Selected move results in a tile placement out of bounds of the board.");
            return null;
        }

        CheckIsInDictionaryReturn checkInDictionaryResult = ckeckIsInDictionary(i, j, tileSelection, startingPoint);
        boolean overlapsOnBoard = checkOverlapsExistingOnBoard(tileSelection, i, j, startingPoint);
        boolean isMoved = false;
        if (checkInDictionaryResult.isInDictionary && overlapsOnBoard) {
            //if (checkInDictionaryResult.isInDictionary) {
            setTile(tileSelection, i, j, vertical);
            isMoved = true;
        }

        return new MoveReturn(checkInDictionaryResult.acceptedWord, i, j, isMoved, vertical);
    }

    private void setTile(String tileSelection, int i, int j, boolean vertical) {
        int m = 0;
        while (m < tileSelection.length()) {
            //for (int m = 0; m < tileSelection.length(); m++) {
            String currentCharacterFromSelection = String.valueOf(tileSelection.charAt(m));
            if (board.letter[i][j].tile == null) {
                Tile tile = fetchTileFromRack(currentCharacterFromSelection);
                board.letter[i][j].setTile(tile);
                m++;
            }
            if (!vertical) {
                j++;
            } else {
                i++;
            }
        }
    }

    private boolean nextTileIsEmpty(boolean isVertical, int i, int j) {

        return board.letter[i][j].tile == null;
    }

    public class CheckIsInDictionaryReturn {
        String acceptedWord;
        boolean isInDictionary;

        public CheckIsInDictionaryReturn(String acceptedWord, boolean checkIsInDictionary) {
            this.acceptedWord = acceptedWord;
            this.isInDictionary = checkIsInDictionary;
        }
    }

    private CheckIsInDictionaryReturn ckeckIsInDictionary(int i, int j, String tileSelection, String
            startingPoint) {
        String acceptedWord = "";
        boolean isVertical = isVertical(startingPoint);
        int n = 0;
        while (n < tileSelection.length() || !nextTileIsEmpty(isVertical, i, j)) {
            // }
            // for (int n = 0; n < tileSelection.length(); n++) {
            if (board.letter[i][j].tile == null) {
                acceptedWord += tileSelection.charAt(n);
                n++;
            } else {
                acceptedWord += board.letter[i][j].tile.character;
            }
            if (isVertical) {
                i++;
            } else {
                j++;
            }
        }
        if (dictionary.exists(acceptedWord))
            return new CheckIsInDictionaryReturn(acceptedWord, true);
        return new CheckIsInDictionaryReturn("", false);
    }

    private boolean checkOverlapsExistingOnBoard(String tileSelection, int i, int j, String startingPoint) {
        if (isFirstMove) {
            return true;
        }

        for (int k = 0; k < tileSelection.length() + 1; k++) {
            if (board.letter[i][j].tile != null) {
                return true;
            }
            if (isVertical(startingPoint)) {
                i++;
            } else {
                j++;
            }
        }
        return false;
    }

    private boolean checkCollidesWithBoardEdge(String tileSelection, int i, int j, String startingPoint) {

        int boardSize = board.getSize();

        for (int k = 0; k < tileSelection.length() + 1; k++) {
            if (i > boardSize - 1 || j > boardSize - 1)
                return true;

            if (board.letter[i][j].tile != null) {
                continue;
            }
            if (isVertical(startingPoint)) {
                i++;
            } else {
                j++;
            }
        }
        return false;
    }

    private Location getLocation(boolean vertical, String startingPoint) {
        Location location;
        if (!vertical) {
            location = getStartingLocation(startingPoint, true);
        } else {
            location = getStartingLocation(startingPoint, false);
        }
        return location;
    }
}


package pij.main.services;

import pij.main.models.*;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.MethodReturns.WildCardReturn;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {
    public TileRack playerRack;
    public TileBag tileBag;
    public Board board;
    public Dictionary dictionary;
    private Game game;
    public int score;
    public String name;

    public void setScore(int score) {
        this.score += score;
    }

    public TileRack getRack() {
        return playerRack;
    }

    public int getScore() {
        return score;
    }

    public Player(TileBag tileBag, Board board, Dictionary dictionary, Game game, String name) {
        this.tileBag = tileBag;
        this.board = board;
        this.dictionary = dictionary;
        playerRack = new TileRack();
        this.game = game;
        this.name = name;
    }

    public WildCardReturn wildCardExists() {
        for (int i = 0; i < playerRack.Rack.length; i++) {
            if (playerRack.Rack[i].character.equals("_")) {
                return new WildCardReturn(i, true);
            }
        }
        return new WildCardReturn(-1, false);
    }

    private boolean isVertical(String location) {
        if (Character.isDigit(location.charAt(0))) {
            return false;
        }
        return true;
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

    public boolean checkTileSelectionIsValid(String tileSelection) {
        for (int j = 0; j < tileSelection.length(); j++) {
            String charr = tileSelection.charAt(j) + "";
            for (int i = 0; i < playerRack.Rack.length; i++) {
                String rackChar = playerRack.Rack[i].character;
                if (charr.equals(rackChar)) {
                    break;
                } else if (i == playerRack.Rack.length - 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkStartingPointIsValid(String startingPoint) {
        String pattern = "\\d{1,2}[a-zA-Z]|[a-zA-Z]\\d{1,2}";
        boolean x = startingPoint.matches(pattern);
        if (!x)
            return false;

        boolean vertical = isVertical(startingPoint);
        Location location = getLocation(vertical, startingPoint);
        int i = location.i;
        int j = location.j;
        if (i > board.getSize() - 1 || j > board.getSize() - 1) {
            return false;
        }


        return true;
    }

    public MoveReturn move() {

        System.out.println("Please enter your move in the format: \"word,square\" (without the quotes)");
        Scanner scan = new Scanner(System.in);
        String moveAsString = scan.nextLine();

        if (moveAsString.equals(",")) {
            return new MoveReturn(MoveReturn.MoveResult.Pass);
        }

        String[] parts = moveAsString.split(",");
        String tileSelection = parts[0];
        String startingPoint = parts[1];

        boolean vertical = isVertical(startingPoint);
        Location location = getLocation(vertical, startingPoint);

        if (!checkMoveIsValid(tileSelection, startingPoint, moveAsString, location))
            return new MoveReturn(MoveReturn.MoveResult.Failed);

        String acceptedWord = getAcceptedWord(location, tileSelection, startingPoint);

        setTile(tileSelection, location, vertical);

        return new MoveReturn(acceptedWord, location, MoveReturn.MoveResult.Done, vertical, startingPoint);
    }


    private boolean checkMoveIsValid(String tileSelection, String startingPoint, String moveAsString, Location location) {
        boolean isTileStartingPointValid = checkStartingPointIsValid(startingPoint);
        if (!isTileStartingPointValid) {
            System.out.println("You can not play with this starting point : " + startingPoint);
            return false;
        }

        boolean isTileSelectionValid = checkTileSelectionIsValid(tileSelection);
        if (!isTileSelectionValid) {
            System.out.println("With tiles " + playerRack + " you cannot play word " + moveAsString);
            return false;
        }

        boolean collidesWithBoardEdge = checkCollidesWithBoardEdge(tileSelection, location, startingPoint);
        if (collidesWithBoardEdge) {
            System.out.println("Invalid move detected. Selected move results in a tile placement out of bounds of the board.");
            return false;
        }

        boolean overlapsOnBoard = checkWordPlacementOverlapsExistingOnBoard(tileSelection, location, startingPoint);
        if (!overlapsOnBoard)
            return false;

        boolean isInDictionary = ckeckWordChoiceIsInDictionary(location, tileSelection, startingPoint);
        if (!isInDictionary) {
            System.out.println("Chosen word is not in dictionary.");
            return false;
        }

        return true;
    }

    private void setTile(String tileSelection, Location location, boolean vertical) {
        int m = 0;
        int i = location.i;
        int j = location.j;
        while (m < tileSelection.length()) {
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

    private boolean ckeckWordChoiceIsInDictionary(Location location, String tileSelection, String
            startingPoint) {
        String acceptedWord = getAcceptedWord(location, tileSelection, startingPoint);

        return dictionary.exists(acceptedWord);
    }

    private String getAcceptedWord(Location location, String tileSelection, String startingPoint) {
        String acceptedWord = "";
        boolean isVertical = isVertical(startingPoint);
        int n = 0;
        int i = location.i;
        int j = location.j;

        while (n < tileSelection.length() || !nextTileIsEmpty(isVertical, i, j)) {
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
        return acceptedWord;
    }

    private boolean checkWordPlacementOverlapsExistingOnBoard(String tileSelection, Location location, String startingPoint) {

        int i = location.i;
        int j = location.j;

        for (int k = 0; k < tileSelection.length() + 1; k++) {
            if (game.isFirstMove) {
                Location boardCentre = board.getStartingPoint();
                if (i == boardCentre.i && j == boardCentre.j)
                    return true;
            }
            if (board.letter[i][j].tile != null) {
                return true;
            }
            if (isVertical(startingPoint)) {
                i++;
            } else {
                j++;
            }
        }
        if (game.isFirstMove)
            System.out.println("Selected location does not overlap with the centre of board.");
        else
            System.out.println("Selected word and starting combination does not overlap with an existing word on the board.");

        return false;
    }

    private boolean checkCollidesWithBoardEdge(String tileSelection, Location location, String startingPoint) {

        int boardSize = board.getSize();
        int i = location.i;
        int j = location.j;
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


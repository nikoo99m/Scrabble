package pij.main.services;

import pij.main.models.*;
import pij.main.models.MethodReturns.CheckIsInDictionaryReturn;
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
        //playerRack.Rack[0].character = "_";
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

    public boolean isTileSelectionValid(String tileSelection) {
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

    public boolean isStartingPointValid(String startingPoint) {
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

        WildCardReturn wcr = wildCardExists();
        System.out.println(playerRack.toString());
        while (wcr.isWildCard) {
            System.out.println("Do you want to use the wildCard?");
            System.out.println("If you want just say true otherwise false.");
            Scanner scanner = new Scanner(System.in);
            boolean answer = scanner.nextBoolean();

            System.out.println("You entered: " + answer);
            if (answer) {
                System.out.println("Enter your desired character:" + " ");
                String wildCardValue = scanner.next();
                playerRack.Rack[wcr.index].character = wildCardValue;
                System.out.println(playerRack.toString());
            } else
                break;
            wcr = wildCardExists();
        }

        System.out.println("Please enter your move in the format: \"word,square\" (without the quotes)");
        Scanner scan = new Scanner(System.in);
        String moveAsString = scan.nextLine();
        String[] parts = moveAsString.split(",");
        String tileSelection = parts[0];
        String startingPoint = parts[1];
        boolean isTileSelectionValid = isTileSelectionValid(tileSelection);
        boolean isTileStartingPointValid = isStartingPointValid(startingPoint);
        if (!isTileStartingPointValid) {
            System.out.println("You can not play with this starting point : " + startingPoint);
            return null;
        }
        if (!isTileSelectionValid) {
            System.out.println("With tiles " + playerRack + " you cannot play word " + moveAsString);
            return null;
        }
        boolean vertical = isVertical(startingPoint);

        Location location = getLocation(vertical, startingPoint);

        int i = location.i;
        int j = location.j;

        boolean collidesWithBoardEdge = checkCollidesWithBoardEdge(tileSelection, i, j, startingPoint);
        if (collidesWithBoardEdge) {
            System.out.println("Invalid move detected. Selected move results in a tile placement out of bounds of the board.");
            return null;
        }

        CheckIsInDictionaryReturn checkInDictionaryResult = ckeckIsInDictionary(i, j, tileSelection, startingPoint);
        if (!checkInDictionaryResult.isInDictionary) {
            System.out.println("Chosen word is not in dictionary.");
            return null;
        }

        boolean overlapsOnBoard = checkOverlapsExistingOnBoard(tileSelection, i, j, startingPoint);
        boolean isMoved = false;
        if (checkInDictionaryResult.isInDictionary && overlapsOnBoard) {
            //if (checkInDictionaryResult.isInDictionary) {
            setTile(tileSelection, i, j, vertical);
            isMoved = true;
            isFirstMove = false;
        }
        return new MoveReturn(checkInDictionaryResult.acceptedWord, i, j, isMoved, vertical);
    }

    private void setTile(String tileSelection, int i, int j, boolean vertical) {
        int m = 0;
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

        for (int k = 0; k < tileSelection.length() + 1; k++) {
            if (isFirstMove) {
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
        if (isFirstMove)
            System.out.println("Selected location does not overlap with the centre of board.");
        else
            System.out.println("Selected word and starting combination does not overlap with an existing word on the board.");

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


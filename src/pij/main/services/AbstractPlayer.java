package pij.main.services;

import pij.main.models.*;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.MethodReturns.WildCardReturn;
import pij.main.models.MethodReturns.WordChoice;
import pij.main.utils.StringHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractPlayer {
    public TileRack playerRack;
    public TileBag tileBag;
    public Board board;
    public Dictionary dictionary;
    public int score;
    public String name;
    protected Game game;

    public AbstractPlayer(TileBag tileBag, Board board, Dictionary dictionary, Game game, String name) {
        playerRack = new TileRack();
        this.tileBag = tileBag;
        this.board = board;
        this.dictionary = dictionary;
        this.game = game;
        this.name = name;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public TileRack getRack() {
        return playerRack;
    }

    public int getScore() {
        return score;
    }

    protected WildCardReturn wildCardExists() {
        for (int i = 0; i < playerRack.Rack.length; i++) {
            if (playerRack.Rack[i] != null && playerRack.Rack[i].character.equals("_")) {
                return new WildCardReturn(i, true);
            }
        }
        return new WildCardReturn(-1, false);
    }
    protected boolean isVertical(String location) {
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
                if (playerRack.Rack[i] == null)
                    continue;
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

    public boolean checkStartingPointIsValid(Location startingPoint) {
        int i = startingPoint.i;
        int j = startingPoint.j;
        if (i > board.getSize() - 1 || j > board.getSize() - 1) {
            return false;
        }

        return true;
    }

    public abstract MoveReturn move();

    public abstract void setWildCardIfExists(AbstractPlayer player);

    protected boolean checkMoveIsValid(Result result, String moveAsString, boolean isHuman) {
        boolean isTileStartingPointValid = checkStartingPointIsValid(result.location);
        if (!isTileStartingPointValid) {
            if (isHuman)
                System.out.println("You can not play with this starting point : " + StringHelper.printLocation(result.location(), result.vertical));
            return false;
        }

        boolean selectedTileIsEmpty = checkIfSelectedTileIsEmpty(result);
        if (!selectedTileIsEmpty) {
            if (isHuman)
                System.out.println("There is already a character tile played at the selected location: " + StringHelper.printLocation(result.location(), result.vertical));
            return false;
        }

        boolean isTileSelectionValid = checkTileSelectionIsValid(result.tileSelection);
        if (!isTileSelectionValid) {
            if (isHuman)
                System.out.println("With tiles " + playerRack + " you cannot play word " + moveAsString);
            return false;
        }

        boolean collidesWithBoardEdge = checkCollidesWithBoardEdge(result);
        if (collidesWithBoardEdge) {
            if (isHuman)
                System.out.println("Invalid move detected. Selected move results in a tile placement out of bounds of the board.");
            return false;
        }

        boolean overlapsOnBoard = checkWordPlacementOverlapsExistingOnBoard(result, isHuman);
        if (!overlapsOnBoard)
            return false;

        boolean isInDictionary = ckeckWordChoiceIsInDictionary(result);
        if (!isInDictionary) {
            if (isHuman)
                System.out.println("Chosen word is not in dictionary.");
            return false;
        }

        return true;
    }

    protected void setTile(Result result) {
        int m = 0;
        int i = result.location.i;
        int j = result.location.j;
        while (m < result.tileSelection.length()) {
            String currentCharacterFromSelection = String.valueOf(result.tileSelection.charAt(m));
            if (board.letter[i][j].tile == null) {
                Tile tile = fetchTileFromRack(currentCharacterFromSelection);
                board.letter[i][j].setTile(tile);
                m++;
            }
            if (!result.vertical) {
                j++;
            } else {
                i++;
            }
        }
    }

    private boolean nextTileIsEmpty(boolean isVertical, int i, int j) {

        return board.letter[i][j].tile == null;
    }

    private boolean ckeckWordChoiceIsInDictionary(Result result) {
        WordChoice acceptedWord = getAcceptedWord(result);

        return dictionary.exists(acceptedWord.word);
    }

    protected WordChoice getAcceptedWord(Result result) {
        StringBuilder acceptedWord = new StringBuilder();
        int n = 0;
        int i = result.location.i;
        int j = result.location.j;

        Location wordStartingPoint = new Location(i, j);


        while ((result.vertical && i - 1 > 0 && board.letter[i - 1][j].tile != null) ||
                (!result.vertical && j - 1 > 0 && board.letter[i][j - 1].tile != null)) {
            if (result.vertical)
                i = i - 1;
            else
                j = j - 1;

            wordStartingPoint.i = i;
            wordStartingPoint.j = j;
        }

        while (n < result.tileSelection.length() || !nextTileIsEmpty(result.vertical, i, j)) {
            if (board.letter[i][j].tile == null) {
                acceptedWord.append(result.tileSelection.charAt(n));
                n++;
            } else {
                acceptedWord.append(board.letter[i][j].tile.character);
            }
            if (result.vertical) {
                i++;
            } else {
                j++;
            }
        }
        return new WordChoice(acceptedWord.toString(), wordStartingPoint);
    }

    private boolean checkIfSelectedTileIsEmpty(Result result) {
        int i = result.location.i;
        int j = result.location.j;

        return board.letter[i][j].tile == null;
    }

    private boolean checkWordPlacementOverlapsExistingOnBoard(Result result, boolean isHuman) {

        int i = result.location.i;
        int j = result.location.j;

        if (game.isFirstMove) {
            Location boardCentre = board.getStartingPoint();
            if (i == boardCentre.i && j == boardCentre.j)
                return true;
        }

        if ((result.vertical && i - 1 > 0 && board.letter[i - 1][j].tile != null) ||
                (!result.vertical && j - 1 > 0 && board.letter[i][j - 1].tile != null)) {
            return true;
        }

        for (int k = 0; k <= result.tileSelection.length(); k++) {
            if (i > board.getSize() && j > board.getSize())
                break;

            if (board.letter[i][j].tile != null) {
                return true;
            }
            if (result.vertical) {
                i++;
            } else {
                j++;
            }
        }
        if (isHuman) {
            if (game.isFirstMove)
                System.out.println("Selected location does not overlap with the centre of board.");
            else
                System.out.println("Selected word and starting combination does not overlap with an existing word on the board.");
        }
        return false;
    }

    private boolean checkCollidesWithBoardEdge(Result result) {

        int boardSize = board.getSize();
        int i = result.location.i;
        int j = result.location.j;
        for (int k = 0; k < result.tileSelection.length() + 1; k++) {
            if (i > boardSize - 1 || j > boardSize - 1)
                return true;

            if (board.letter[i][j].tile != null) {
                continue;
            }
            if (result.vertical) {
                i++;
            } else {
                j++;
            }
        }
        return false;
    }

    protected Location getLocation(boolean vertical, String startingPoint) {
        Location location;
        if (!vertical) {
            location = getStartingLocation(startingPoint, true);
        } else {
            location = getStartingLocation(startingPoint, false);
        }
        return location;
    }

    public record Result(String tileSelection, boolean vertical, Location location) {
    }
}

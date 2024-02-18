package pij.main.services;

import pij.main.models.*;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.MethodReturns.WordChoice;
import pij.main.models.interfaces.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractPlayer {
    public TileRack playerRack;
    public TileBag tileBag;
    public Board board;
    public int score;
    public String name;
    protected Game game;
    protected Dictionary dictionary = new Dictionary();
    public AbstractPlayer(TileBag tileBag, Board board, Game game, String name) {
        playerRack = new TileRack();
        this.tileBag = tileBag;
        this.board = board;
        this.game = game;
        this.name = name;
    }

    //region player behaviour
    public void setScore(int score) {
        this.score += score;
    }
    public TileRack getRack() {
        return playerRack;
    }
    public int getScore() {
        return score;
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
    protected void setTile(Result result) {
        int m = 0;
        int i = result.location().i;
        int j = result.location().j;
        while (m < result.tileSelection().length()) {
            String currentCharacterFromSelection = String.valueOf(result.tileSelection().charAt(m));
            if (board.letter[i][j].tile == null) {
                Tile tile = fetchTileFromRack(currentCharacterFromSelection);
                board.letter[i][j].setTile(tile);
                m++;
            }
            if (!result.vertical()) {
                j++;
            } else {
                i++;
            }
        }
    }
    public abstract MoveReturn move();
    public abstract void setWildCardIfExists();

    //endregion

    //region player's input validation

    //endregion

    //region not sure!
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
    protected Location getLocation(boolean vertical, String startingPoint) {
        Location location;
        if (!vertical) {
            location = getStartingLocation(startingPoint, true);
        } else {
            location = getStartingLocation(startingPoint, false);
        }
        return location;
    }
    //endregion

    //region
    //endregion

}

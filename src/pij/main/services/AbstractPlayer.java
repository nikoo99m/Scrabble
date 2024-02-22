package pij.main.services;

import pij.main.models.*;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.MethodReturns.WordChoice;
import pij.main.models.interfaces.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents that there are mutual methods between player and computer player class that some of them may have different implementation.
 */
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


    /**
     * Increases the player's score by the specified amount.
     *
     * @param score the amount by which to increase the player's score
     */
    public void setScore(int score) {
        this.score += score;
    }

    /**
     * Retrieves the player's tile rack.
     *
     * @return the player's tile rack
     */
    public TileRack getRack() {
        return playerRack;
    }

    /**
     * Retrieves the player's current score.
     *
     * @return the player's current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Retrieves a tile from the player's rack.
     *
     * @param c the character representing the tile to fetch
     * @return the tile fetched from the rack
     */
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

    /**
     * Sets the tiles on the board based on the player's move.
     *
     * @param result the result of the player's move
     */
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

    /**
     * Abstract method to determine the player's move which has different implementation in subClasses.
     *
     * @return the result of the move
     */
    public abstract MoveReturn move();

    /**
     * Abstract method to handle the setting of wildcard characters if they exist in the player's rack.
     */
    public abstract void setWildCardIfExists();

    /**
     * Retrieves the starting location based on the input string and orientation.
     *
     * @param inputString    the input string representing the location
     * @param useNumberFirst determines whether the start of the input string is the number or character.
     * @return the starting location
     */
    private Location getStartingLocation(String inputString, boolean useNumberFirst) {
        Pattern pattern = Pattern.compile("([a-zA-Z]+)([0-9]+)|([0-9]+)([a-zA-Z]+)");

        Matcher matcher = pattern.matcher(inputString);

        if (matcher.matches()) {

            String numberPart;
            String characterPart;

            if (useNumberFirst) {
                numberPart = matcher.group(3);
                characterPart = matcher.group(4);
            } else {
                numberPart = matcher.group(2);
                characterPart = matcher.group(1);
            }

            return new Location(numberPart, characterPart);
        }

        throw new RuntimeException("No location found.");
    }

    /**
     * Retrieves the location based on the specified orientation and starting point.
     *
     * @param vertical      determines whether the move is vertical or not
     * @param startingPoint the starting point of the move
     * @return the location of the move
     */

    protected Location getLocation(boolean vertical, String startingPoint) {
        Location location;
        if (!vertical) {
            location = getStartingLocation(startingPoint, true);
        } else {
            location = getStartingLocation(startingPoint, false);
        }
        return location;
    }


}

package pij.main.services;

import pij.main.models.Dictionary;
import pij.main.models.Location;
import pij.main.models.MethodReturns.WordChoice;
import pij.main.models.Result;
import pij.main.models.TileRack;
import pij.main.models.interfaces.Validator;
import pij.main.utils.GameHelper;
import pij.main.utils.StringHelper;

public class PlayerInputValidator implements Validator {

    private Result result;
    private String moveAsString;
    private boolean isHuman;
    private TileRack playerRack;
    private Board board;
    private Dictionary dictionary;
    private Game game;

    public PlayerInputValidator(Result result, String moveAsString, boolean isHuman, TileRack playerRack, Board board, Game game, Dictionary dictionary) {
        this.result = result;
        this.moveAsString = moveAsString;
        this.isHuman = isHuman;
        this.playerRack = playerRack;
        this.board = board;
        this.game = game;
        this.dictionary = dictionary;
    }

    @Override
    public boolean Validate() {
        return checkMoveIsValid();
    }

    private boolean checkMoveIsValid() {
        boolean isTileStartingPointValid = checkStartingPointIsValid(result.location());
        if (!isTileStartingPointValid) {
            if (isHuman)
                System.out.println("You can not play with this starting point : " + StringHelper.printLocation(result.location(), result.vertical()));
            return false;
        }

        boolean selectedTileIsEmpty = checkIfSelectedTileIsEmpty(result);
        if (!selectedTileIsEmpty) {
            if (isHuman)
                System.out.println("There is already a character tile played at the selected location: " + StringHelper.printLocation(result.location(), result.vertical()));
            return false;
        }

        boolean isTileSelectionValid = checkTileSelectionIsValid(result.tileSelection());
        if (!isTileSelectionValid) {
            if (isHuman)
                System.out.println("With tiles " + this.playerRack + " you cannot play word " + moveAsString);
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

    private boolean ckeckWordChoiceIsInDictionary(Result result) {
        WordChoice acceptedWord = GameHelper.getAcceptedWord(result, board);

        return dictionary.exists(acceptedWord.word);
    }

    private boolean checkIfSelectedTileIsEmpty(Result result) {
        int i = result.location().i;
        int j = result.location().j;

        return board.letter[i][j].tile == null;
    }

    private boolean checkWordPlacementOverlapsExistingOnBoard(Result result, boolean isHuman) {

        int i = result.location().i;
        int j = result.location().j;

        if (game.isFirstMove) {
            Location boardCentre = board.getStartingPoint();
            if (i == boardCentre.i && j == boardCentre.j)
                return true;
        }

        if ((result.vertical() && i - 1 > 0 && board.letter[i - 1][j].tile != null) ||
                (!result.vertical() && j - 1 > 0 && board.letter[i][j - 1].tile != null)) {
            return true;
        }

        for (int k = 0; k <= result.tileSelection().length(); k++) {
            if (i > board.getSize() && j > board.getSize())
                break;

            if (board.letter[i][j].tile != null) {
                return true;
            }
            if (result.vertical()) {
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
        int i = result.location().i;
        int j = result.location().j;
        for (int k = 0; k < result.tileSelection().length() + 1; k++) {
            if (i > boardSize - 1 || j > boardSize - 1)
                return true;

            if (board.letter[i][j].tile != null) {
                k = k - 1;
            }
            if (result.vertical()) {
                i++;
            } else {
                j++;
            }
        }
        return false;
    }
}

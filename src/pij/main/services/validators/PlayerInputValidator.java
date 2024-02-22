package pij.main.services.validators;

import pij.main.models.Dictionary;
import pij.main.models.Result;
import pij.main.models.TileRack;
import pij.main.models.interfaces.Validator;
import pij.main.services.Board;
import pij.main.services.Game;

import java.util.Arrays;
import java.util.List;

/**
 * The PlayerInputValidator class validates the input move made by a player.
 * It checks various conditions to ensure that the move is valid according to the game rules.
 */
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

    /**
     * Validates the player's move.
     *
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean validate() {

        List<Validator> validators = Arrays.asList(new StartingPointValidator(result.location(), board, isHuman, result),
                new BoardTileIsEmptyValidator(board, result, isHuman),
                new TileSelectionValidator(result.tileSelection(), playerRack, isHuman, moveAsString),
                new PlayerMoveDoesNotExceedsBoardBoundaryValidator(board, result, isHuman),
                new OverlapsWithExistingWordOnBoardValidator(result, game, board, isHuman),
                new IsInDictionaryValidator(dictionary, result, board, isHuman),
                new MoveIsImmediatelyParallelValidator(game, result, board, isHuman));

        for (Validator validator: validators) {
            if(!validator.validate())
                return false;
        }

        return true;
    }
}

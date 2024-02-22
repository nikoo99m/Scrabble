package pij.main.services.validators;

import pij.main.models.Location;
import pij.main.models.Result;
import pij.main.models.interfaces.Validator;
import pij.main.services.Board;
import pij.main.services.Game;

public class OverlapsWithExistingWordOnBoardValidator implements Validator {
    private Result result;
    private Game game;
    private Board board;
    private boolean isHuman;

    public OverlapsWithExistingWordOnBoardValidator(Result result, Game game, Board board, boolean isHuman) {
        this.result = result;
        this.game = game;
        this.board = board;
        this.isHuman = isHuman;
    }

    /**
     * Checks if the word placement overlaps with existing tiles on the board.
     *
     * @return true if the word placement overlaps with existing tiles, false otherwise.
     */
    @Override
    public boolean validate() {

        int i = result.location().i;
        int j = result.location().j;

        if (game.isFirstMove) {
            Location boardCentre = board.getStartingPoint();
            for (int x = 0; x < result.tileSelection().length(); x++) {
                if (i == boardCentre.i && j == boardCentre.j)
                    return true;
                if (result.vertical())
                    i++;
                else
                    j++;
            }
        }
        i = result.location().i;
        j = result.location().j;

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
}

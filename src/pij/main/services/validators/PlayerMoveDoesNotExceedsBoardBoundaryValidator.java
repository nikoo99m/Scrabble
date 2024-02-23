package pij.main.services.validators;

import pij.main.models.Result;
import pij.main.models.interfaces.Validator;
import pij.main.services.Board;
/**
 * Validator implementation for checking if a player's move exceeds the boundary of the game board.
 * It ensures that the placement of tiles resulting from the move does not go beyond the edge of the board.
 */
public class PlayerMoveDoesNotExceedsBoardBoundaryValidator implements Validator {
    private Board board;
    private Result result;
    private boolean isHuman;

    public PlayerMoveDoesNotExceedsBoardBoundaryValidator(Board board, Result result, boolean isHuman) {
        this.board = board;
        this.result = result;
        this.isHuman = isHuman;
    }

    /**
     * Checks if the move collides with the edge of the board.
     *
     * @return true if the move collides with the edge of the board, false otherwise.
     */
    @Override
    public boolean validate() {
        int boardSize = board.getSize();
        int i = result.location().i;
        int j = result.location().j;
        for (int k = 0; k < result.tileSelection().length() + 1; k++) {
            if (i > boardSize - 1 || j > boardSize - 1) {
                if (isHuman)
                    System.out.println("Invalid move detected. Selected move results in a tile placement out of bounds of the board.");
                return false;
            }
            if (board.letter[i][j].tile != null) {
                k = k - 1;
            }
            if (result.vertical()) {
                i++;
            } else {
                j++;
            }
        }
        return true;
    }
}

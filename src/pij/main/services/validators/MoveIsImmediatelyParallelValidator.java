package pij.main.services.validators;

import pij.main.models.Result;
import pij.main.models.interfaces.Validator;
import pij.main.services.Board;
import pij.main.services.Game;

public class MoveIsImmediatelyParallelValidator implements Validator {
    private Game game;
    private Result result;
    private Board board;
    private boolean isHuman;

    public MoveIsImmediatelyParallelValidator(Game game, Result result, Board board, boolean isHuman) {
        this.game = game;
        this.result = result;
        this.board = board;
        this.isHuman = isHuman;
    }

    /**
     * Checks if the word placement immediately creates parallel words on the board.
     *
     * @return true if the word placement immediately creates parallel words, false otherwise.
     */
    @Override
    public boolean validate() {
        if (game.isFirstMove) return true;

        int i = result.location().i;
        int j = result.location().j;
        for (int k = 0; k < result.tileSelection().length(); k++) {

            if (board.letter[i][j].tile != null)
                k = k - 1;
            else if (hasParallelTileOnBoard(result, i, j))
                return informUserOfError();

            if (result.vertical())
                i++;
            else
                j++;
        }

        return true;
    }

    private boolean informUserOfError() {
        if (isHuman)
            System.out.println("Chosen word creates more than one word board.");
        return false;
    }

    /**
     * Checks if there is a parallel tile on the board at the specified location.
     *
     * @param result The result of the move.
     * @param i      The row index of the location.
     * @param j      The column index of the location.
     * @return true if there is a parallel tile on the board, false otherwise.
     */
    private boolean hasParallelTileOnBoard(Result result, int i, int j) {
        if (!result.vertical()) {
            if (i - 1 <= 0 && board.letter[i + 1][j].tile != null)
                return true;
            else if (i + 1 > board.getSize() && board.letter[i - 1][j].tile != null)
                return true;
            else if ((i + 1 < board.getSize() && board.letter[i + 1][j].tile != null) ||
                    (i - 1 >= 0 && board.letter[i - 1][j].tile != null))
                return true;
        } else {
            if (j - 1 <= 0 && board.letter[i][j + 1].tile != null)
                return true;
            else if (j + 1 > board.getSize() && board.letter[i][j - 1].tile != null)
                return true;
            else if ((j + 1 < board.getSize() && board.letter[i][j + 1].tile != null) ||
                    (j - 1 >= 0 && board.letter[i][j - 1].tile != null))
                return true;
        }
        return false;
    }

}

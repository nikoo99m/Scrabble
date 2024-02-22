package pij.main.services.validators;

import pij.main.models.Result;
import pij.main.models.interfaces.Validator;
import pij.main.services.Board;
import pij.main.utils.StringHelper;

public class BoardTileIsEmptyValidator implements Validator {

    private Board board;
    private Result result;
    private boolean isHuman;

    public BoardTileIsEmptyValidator(Board board, Result result, boolean isHuman) {
        this.board = board;
        this.result = result;
        this.isHuman = isHuman;
    }

    /**
     * Checks if the selected tile for the move is empty.
     *
     * @return true if the selected tile is empty, false otherwise.
     */
    @Override
    public boolean validate() {
        int i = result.location().i;
        int j = result.location().j;

        if (board.letter[i][j].tile == null)
            return true;
        else
        {
            if (isHuman)
                System.out.println("There is already a character tile played at the selected location: " + StringHelper.printLocation(result.location(), result.vertical()));
        return false;
        }
    }
}

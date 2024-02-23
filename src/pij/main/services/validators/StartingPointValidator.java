package pij.main.services.validators;

import pij.main.models.Location;
import pij.main.models.Result;
import pij.main.models.interfaces.Validator;
import pij.main.services.Board;
import pij.main.utils.StringHelper;
/**
 * Validator implementation for checking the validity of the starting point of a move on the game board.
 * It ensures that the specified starting point is within the bounds of the game board.
 */
public class StartingPointValidator implements Validator {
    private Location startingPoint;
    private Board board;
    private boolean isHuman;
    private Result result;

    public StartingPointValidator(Location startingPoint, Board board, boolean isHuman, Result result) {
        this.startingPoint = startingPoint;
        this.board = board;
        this.isHuman = isHuman;
        this.result = result;
    }

    /**
     * Checks if the starting point of the move is valid.
     *
     * @return true if the starting point is valid, false otherwise.
     */
    @Override
    public boolean validate() {
        int i = startingPoint.i;
        int j = startingPoint.j;
        if (i > board.getSize() - 1 || j > board.getSize() - 1  || i < 0  || j < 0) {
            if (isHuman)
                System.out.println("You can not play with this starting point : " + StringHelper.printLocation(result.location(), result.vertical()));
            return false;
        }

        return true;
    }
}

package pij.test.services.validators;

import org.junit.jupiter.api.Test;
import pij.main.exceptions.*;
import pij.main.models.Location;
import pij.main.models.Result;
import pij.main.models.Tile;
import pij.main.models.interfaces.Validator;
import pij.main.services.Board;
import pij.main.services.validators.BoardTileIsEmptyValidator;
import pij.main.services.validators.StartingPointValidator;
import pij.main.utils.BoardHelper;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTileIsEmptyValidatorTest {
    @Test
    void checkIsTrueIfSelectedSquareDoesNotHaveAllocatedTile() throws BoardContentSizeMismatchException, InvalidInputFormatAsPremiumFieldException, InvalidValueFormatForBoardSizeException, OutOfRangeBoardValueException, InvalidBoardSizeException {
        //arrange
        Location startingPoint = new Location(1, 1);
        Board board = BoardHelper.loadBoardFromFile("resources\\defaultBoard.txt");

        Result result = new Result(null, true, startingPoint);
        //action
        Validator validator = new BoardTileIsEmptyValidator(board, result, false);
        boolean isSelectedBoardCellEmpty = validator.validate();
        //assert
        assertTrue(isSelectedBoardCellEmpty);
    }

    @Test
    void checkIsFalseIfSelectedSquareHasAllocatedTile() throws BoardContentSizeMismatchException, InvalidInputFormatAsPremiumFieldException, InvalidValueFormatForBoardSizeException, OutOfRangeBoardValueException, InvalidBoardSizeException {
        //arrange
        Location startingPoint = new Location(1, 1);
        Board board = BoardHelper.loadBoardFromFile("resources\\defaultBoard.txt");

        board.letter[1][1].tile = new Tile("n", 12);

        Result result = new Result(null, true, startingPoint);
        //action
        Validator validator = new BoardTileIsEmptyValidator(board, result, false);
        boolean isSelectedBoardCellEmpty = validator.validate();
        //assert
        assertFalse(isSelectedBoardCellEmpty);
    }
}
package pij.test.services.validators;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pij.main.models.Location;
import pij.main.models.Result;
import pij.main.models.interfaces.Validator;
import pij.main.services.Board;
import pij.main.services.validators.StartingPointValidator;

import static org.junit.jupiter.api.Assertions.*;

public class StartingPointValidatorTest {

    @Test
    void checkIsTrueIfStartingLocationIsWithinBoardBoundary() {
        //arrange
        Location startingPoint = new Location(1, 1);
        Board board = new Board(16);
        Result result = new Result(null, true, startingPoint);
        //action
        Validator validator = new StartingPointValidator(startingPoint, board, false, result);
        boolean isStartingPointValid = validator.validate();
        //assert
        assertTrue(isStartingPointValid);
    }
    @ParameterizedTest
    @ValueSource(ints = {-1, 17})
    void checkIsFalseIfStartingLocationHasInvalidRowValue(int i) {
        //arrange
        Location startingPoint = new Location(i, 1);
        Board board = new Board(16);
        Result result = new Result(null, true, startingPoint);
        //action
        Validator validator = new StartingPointValidator(startingPoint, board, false, result);
        boolean isStartingPointValid = validator.validate();
        //assert
        assertFalse(isStartingPointValid);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 17})
    void checkIsFalseIfStartingLocationHasInvalidColumnValue(int j) {
        //arrange
        Location startingPoint = new Location(1, j);
        Board board = new Board(16);
        Result result = new Result(null, true, startingPoint);
        //action
        Validator validator = new StartingPointValidator(startingPoint, board, false, result);
        boolean isStartingPointValid = validator.validate();
        //assert
        assertFalse(isStartingPointValid);
    }
}
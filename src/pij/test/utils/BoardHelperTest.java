package pij.test.utils;

import org.junit.jupiter.api.Test;
import pij.main.exceptions.*;
import pij.main.utils.BoardHelper;

import static org.junit.jupiter.api.Assertions.*;

class BoardHelperTest {

    @Test
    void exceptionIsRaisedWhenBoardContentDoesNotMatchSpecifiedSize() throws BoardContentSizeMismatchException, InvalidInputFormatAsPremiumFieldException, InvalidValueFormatForBoardSizeException, OutOfRangeBoardValueException, InvalidBoardSizeException {

        assertThrows(BoardContentSizeMismatchException.class, () -> {
            BoardHelper.loadBoardFromFile("resources\\test\\boardContentSizeMismatchException.txt");
        });
    }

    @Test
    void exceptionIsraisedWhenInvalidValueSuppliedForBoardSize() throws BoardContentSizeMismatchException, InvalidInputFormatAsPremiumFieldException, InvalidValueFormatForBoardSizeException, OutOfRangeBoardValueException, InvalidBoardSizeException {

        assertThrows(InvalidValueFormatForBoardSizeException.class, () -> {
            BoardHelper.loadBoardFromFile("resources\\test\\invalidValueFormatForBoardSizeException.txt");
        });
    }

    @Test
    void exceptionIsRaisedWhenOutOfRangeValueIsSuppliedForPremiumSquares() throws BoardContentSizeMismatchException, InvalidInputFormatAsPremiumFieldException, InvalidValueFormatForBoardSizeException, OutOfRangeBoardValueException, InvalidBoardSizeException {

        assertThrows(OutOfRangeBoardValueException.class, () -> {
            BoardHelper.loadBoardFromFile("resources\\test\\outOfRangeBoardValueException.txt");
        });
    }
    @Test
    void loadBoardFromFile5() throws BoardContentSizeMismatchException, InvalidInputFormatAsPremiumFieldException, InvalidValueFormatForBoardSizeException, OutOfRangeBoardValueException, InvalidBoardSizeException {

        assertThrows(InvalidBoardSizeException.class, () -> {
            BoardHelper.loadBoardFromFile("resources\\test\\invalidBoardSizeException.txt");
        });
    }
}
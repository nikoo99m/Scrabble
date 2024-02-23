package pij.test.models.squares;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pij.main.models.squares.CurlySquare;
import pij.main.models.squares.Square;
import pij.main.models.squares.StandardSquare;

import static org.junit.jupiter.api.Assertions.*;

class CurlySquareTest {

    @ParameterizedTest
    @CsvSource({
            "1, '{1}  '",
            "-5, '{-5} '",
            "10, '{10} '",
            "-99, '{-99}'"
    })
    void generateSquareText(int mul, String expectedText) {
        //arrange
        Square standardSquare = new CurlySquare(mul);
        //action
        String text = standardSquare.generateSquareText();
        //assert
        assertEquals(expectedText, text);
    }
}
package pij.test.models.squares;

import org.junit.jupiter.api.Test;
import pij.main.models.squares.Square;
import pij.main.models.squares.StandardSquare;

import static org.junit.jupiter.api.Assertions.*;

public class StandardSquareTest {

    @Test
    void generateSquareText() {
        //arrange
        Square standardSquare = new StandardSquare();
        //action
        String text = standardSquare.generateSquareText();
        //assert
        assertEquals(".    ", text);
    }
}
package pij.main;

import pij.main.Squares.BracketSquare;
import pij.main.Squares.Square;
import pij.main.Squares.StandardSquare;

public class ScoreHelper {



    public static int CalculatingplayerScore(Board board, MoveReturn moveReturn) {

        int score = 0;
        int i = moveReturn.i;
        int j = moveReturn.j;
        int premiumWordMultiplier = 1;
        if (moveReturn.move == true) {
            for (int r = 0; r < moveReturn.word.length(); r++) {
                Square square = board.letter[i][j];
                if (square instanceof BracketSquare) {
                    int value = square.tile.value * square.multiplier;
                    score += value;
                } else if (square instanceof StandardSquare) {
                    int value = square.tile.value;
                    score += value;
                } else {
                    premiumWordMultiplier *= square.multiplier;
                    int value = square.tile.value;
                    score += value;
                }
                if (moveReturn.vertical) {
                    i++;
                } else {
                    j++;
                }
                square.multiplier = 1;
            }
            score *= premiumWordMultiplier;
        }

//        for (int e = 0; e < moveReturn.word.length(); e++) {
//            char character = moveReturn.word.charAt(e);
//            for (int b = 0; b < tileBag.letters.length; b++) {
//                if (tileBag.letters[b] == character) {
//                    score += tileBag.values[b];
//                }
//            }
//        }

        return score;
    }
}

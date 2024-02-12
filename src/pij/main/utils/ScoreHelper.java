package pij.main.utils;

import pij.main.services.Board;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.Squares.BracketSquare;
import pij.main.models.Squares.Square;
import pij.main.models.Squares.StandardSquare;

public class ScoreHelper {



    public static int CalculatingplayerScore(Board board, MoveReturn moveReturn) {

        int score = 0;
        int i = moveReturn.details.i;
        int j = moveReturn.details.j;
        int premiumWordMultiplier = 1;
        if (moveReturn.result == MoveReturn.MoveResult.Done) {
            for (int r = 0; r < moveReturn.details.word.length(); r++) {
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
                if (moveReturn.details.vertical) {
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

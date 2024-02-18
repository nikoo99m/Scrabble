package pij.main.utils;

import pij.main.models.Location;
import pij.main.models.MethodReturns.WordChoice;
import pij.main.models.Result;
import pij.main.services.Board;

public class GameHelper {
    public static boolean isVertical(String location) {
        if (Character.isDigit(location.charAt(0))) {
            return false;
        }
        return true;
    }

    public static WordChoice getAcceptedWord(Result result, Board board) {
        StringBuilder acceptedWord = new StringBuilder();
        int n = 0;
        int i = result.location().i;
        int j = result.location().j;

        Location wordStartingPoint = new Location(i, j);

        while ((result.vertical() && i - 1 > 0 && board.letter[i - 1][j].tile != null) ||
                (!result.vertical() && j - 1 > 0 && board.letter[i][j - 1].tile != null)) {
            if (result.vertical())
                i = i - 1;
            else
                j = j - 1;

            wordStartingPoint.i = i;
            wordStartingPoint.j = j;
        }

        while (n < result.tileSelection().length() ||
                (!outOfBoard(i, j, board) && !nextTileIsEmpty(result.vertical(), i, j, board))) {
            if (board.letter[i][j].tile == null) {
                acceptedWord.append(result.tileSelection().charAt(n));
                n++;
            } else {
                acceptedWord.append(board.letter[i][j].tile.character);
            }
            if (result.vertical()) {
                i++;
            } else {
                j++;
            }
        }
        return new WordChoice(acceptedWord.toString(), wordStartingPoint);
    }

    private static boolean outOfBoard(int i, int j, Board board) {
        return i >= board.getSize() || j >= board.getSize();
    }

    private static boolean nextTileIsEmpty(boolean isVertical, int i, int j, Board board) {

        return board.letter[i][j].tile == null;
    }
}

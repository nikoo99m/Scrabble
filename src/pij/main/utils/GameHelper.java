package pij.main.utils;

import pij.main.models.Location;
import pij.main.models.methodReturns.WordChoice;
import pij.main.models.Result;
import pij.main.services.Board;

/**
 * This class provides utility methods to assist with game logic and operations.
 */
public class GameHelper {
    /**
     * Checks if the provided location string represents a vertical move or not.
     *
     * @param location The location string to be checked.
     * @return {@code true} if the location is vertical, {@code false} otherwise.
     */
    public static boolean isVertical(String location) {
        if (Character.isDigit(location.charAt(0))) {
            return false;
        }
        return true;
    }
    /**
     * Retrieves the accepted word and its starting point based on the result of a move and the current board state.
     *
     * @param result The result of a move.
     * @param board The game board.
     * @return A WordChoice object containing the accepted word and its starting point.
     */
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
    /**
     * Checks if the provided coordinates are out of the game board bounds or not.
     *
     * @param i The row index.
     * @param j The column index.
     * @param board The game board.
     * @return {@code true} if the coordinates are out of bounds, {@code false} otherwise.
     */
    private static boolean outOfBoard(int i, int j, Board board) {
        return i >= board.getSize() || j >= board.getSize();
    }
    /**
     * Checks if the next tile in the specified direction is empty.
     *
     * @param isVertical {@code true} if the direction is vertical, {@code false} otherwise.
     * @param i The current row index.
     * @param j The current column index.
     * @param board The game board.
     * @return {@code true} if the next tile is empty, {@code false} otherwise.
     */
    private static boolean nextTileIsEmpty(boolean isVertical, int i, int j, Board board) {

        return board.letter[i][j].tile == null;
    }
}

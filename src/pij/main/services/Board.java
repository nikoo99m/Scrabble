package pij.main.services;

import pij.main.models.Location;
import pij.main.models.squares.Square;
import pij.main.utils.StringHelper;

/**
 * Represents the game board consisting of squares.
 * The board maintains the state of the game and provides methods
 * for interacting with the squares.
 */
public class Board {
    public Square[][] letter;
    private int size;

    /**
     * Constructs game board with the specified size.
     *
     * @param size the size of the board (number of rows and columns)
     */
    public Board(int size) {
        this.size = size;
        this.letter = new Square[size][size];
    }

    /**
     * Retrieves the size of the board.
     *
     * @return the size of the board
     */
    public int getSize() {
        return size;
    }

    /**
     * Prints a visual representation of the board to the console,
     * including row and column headers.
     */
    public void prettyPrint() {
        printXAxisHeader();
        for (int i = 0; i < size; i++) {
            System.out.print(StringHelper.set5(String.valueOf(i + 1)));
            for (int j = 0; j < size; j++) {
                System.out.print(letter[i][j].toString());
            }
            System.out.print(StringHelper.set5(String.valueOf(i + 1)));
            System.out.println();
        }
        printXAxisHeader();
    }

    private void printXAxisHeader() {
        char ch = 'a';
        System.out.print("     ");
        for (int i = 0; i < size; i++) {
            System.out.print(StringHelper.set5(String.valueOf(ch)));
            ch++;
        }
        System.out.println();
    }
    /**
     * calculate centre of the board.
     * Retrieves the starting point on the board.
     * The starting point should overlap with the center of the board.
     *
     * @return the starting point location on the board
     */
    public Location getStartingPoint() {
        int x = (int) Math.ceil((double) size / 2);
        return new Location(x - 1, x - 1);
    }
    /**
     * Sets the square at the specified row and column on the board.
     *
     * @param row    the row index of the square
     * @param col    the column index of the square
     * @param square the square to set at the specified position
     */
    public void setSquare(int row, int col, Square square) {
        letter[row][col] = square;
    }
}

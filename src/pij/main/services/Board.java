package pij.main.services;

import pij.main.models.Location;
import pij.main.models.Squares.BracketSquare;
import pij.main.models.Squares.CurlySquare;
import pij.main.models.Squares.Square;
import pij.main.models.Squares.StandardSquare;
import pij.main.utils.StringHelper;

import java.util.Random;

public class Board {
    public Square[][] letter;
    private int size;

    public Board(int size) {
        this.size = size;
        this.letter = new Square[size][size];
    }
    public int getSize()
    {
        return size;
    }
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
    public Location getStartingPoint()
    {
        int x = (int)Math.ceil((double) size /2);
        return new Location(x - 1, x - 1);
    }
    public void setSquare(int row, int col, Square square) {
        letter[row][col] = square;
    }
}

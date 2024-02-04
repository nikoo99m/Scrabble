package pij.main;

import java.util.Random;

public class Board {
    public Square[][] letter;
    private int size;

    public Board(int size) {
        this.size = size;
        this.letter = new Square[size][size];
        //makeItAll();
    }

    public void makeItAll() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int randomNumber = random.nextInt(3) + 1;

                if (randomNumber == 1) {
                    letter[i][j] = new StandardSquare();
                } else if (randomNumber == 2) {
                    letter[i][j] = new CurlySquare(random.nextInt(99));
                } else {
                    letter[i][j] = new BracketSquare(random.nextInt(99));
                }
            }
        }
    }

    public void prettyPrint() {
        printXAxisHeader();
        for (int i = 0; i < size; i++) {
            System.out.print(StringHelper.set5(String.valueOf(i)));
            for (int j = 0; j < size; j++) {
                System.out.print(letter[i][j].toString());
            }
            System.out.print(StringHelper.set5(String.valueOf(i)));
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

    public void setSquare(int row, int col, Square square) {
        letter[row][col] = square;
    }
}

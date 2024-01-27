package pij.main;

import java.util.Random;
import java.util.Scanner;

public class Board {
    Square[][] letter;
    int s;

    Scanner scan = new Scanner(System.in);

    public Board() {
        System.out.print("Enter the number of levels (11-26): ");
        s = scan.nextInt();
        if (s < 11 || s > 26) {
            System.out.println("Invalid input. Number of levels must be between 11 and 26.");

        }
        letter = new Square[s][s];
        makeItAll();
    }

    public void makeItAll() {
        Random random = new Random();
        for (int i = 0; i < letter.length; i++) {
            for (int j = 0; j < letter[i].length; j++) {
                //letter[i][j] = new StandardSquare();

                int randomNumber = random.nextInt(3) + 1;

                // Condition 1
                if (randomNumber == 1) {
                    letter[i][j] = new StandardSquare();
                    if(random.nextInt(2) == 0)
                        letter[i][j].setTile(new Tile("Z", 13));
                } else if (randomNumber == 2) {
                    // Condition 2
                    letter[i][j] = new CurlySquare(random.nextInt(99));
                    if(random.nextInt(2) == 0)
                        letter[i][j].setTile(new Tile("A", 26));
                } else {
                    // Condition 3
                    letter[i][j] = new BracketSquare(random.nextInt(99));
                    if(random.nextInt(2) == 0)
                        letter[i][j].setTile(new Tile("T", 39));
                }
            }
        }
    }

    public void prettyPrint() {
        // Print top row with column labels (a, b, c, ...)
        System.out.print("  ");
        for (int i = 0; i < s; i++) {
            System.out.print((char) ('a' + i) + " ");
        }
        System.out.println();


        for (int i = 0; i < letter.length; i++) {

            System.out.print((i + 1) + " ");


            for (int j = 0; j < letter[i].length; j++) {
                System.out.print(letter[i][j].ToString() + " ");
            }

            // Print right-side row number
            System.out.print(i + 1);
            System.out.println();
        }


        System.out.print("  ");
        for (int i = 0; i < s; i++) {
            System.out.print((char) ('a' + i) + " ");
        }
        System.out.println();
    }

    public Square[][] getData() {
        return letter;
    }
}

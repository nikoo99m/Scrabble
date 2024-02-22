package pij.main.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Represents a bag of tiles used in a game.
 * <p>
 * This class contains a collection of tiles, each represented by a character and a value.
 * Tiles can be randomly drawn from the bag.
 */
public class TileBag {
    int[] values;
    char[] letters;
    HashSet<Tile> Bag = new HashSet<>();

    /**
     * Constructs a new TileBag object with a predefined set of tiles and their quantities.
     */
    public TileBag() {

        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '_'};
        int[] values = {1, 3, 3, 2, 1, 4, 2, 4, 1, 9, 6, 1, 3, 1, 1, 3, 12, 1, 1, 1, 1, 4, 4, 9, 5, 11, 5};
        int[] numbersOfLetters = {8, 2, 2, 4, 10, 3, 4, 3, 8, 1, 1, 4, 2, 7, 7, 2, 1, 6, 4, 6, 5, 2, 1, 1, 2, 1, 2};

        for (int i = 0; i < letters.length; i++) {
            char letter = letters[i];
            int value = values[i];
            int num = numbersOfLetters[i];

            for (int j = 0; j < num; j++) {
                Tile tile = new Tile(Character.toString(letter), value);
                Bag.add(tile);
            }
        }
    }

    /**
     * Checks if the tile bag is empty.
     *
     * @return true if the bag is empty, false otherwise
     */
    public boolean isEmpty() {
        return Bag.isEmpty();
    }

    /**
     * Retrieves a randomly selected tile from the bag.
     *
     * @return a Tile randomly selected from the bag, or null if the bag is empty
     */
    public Tile randomPop() {
        if (Bag.isEmpty()) {
            return null;
        }

        ArrayList<Tile> arrayList = new ArrayList<>(Bag);

        Random random = new Random();
        int randomIndex = random.nextInt(arrayList.size());

        Tile poppedElement = arrayList.remove(randomIndex);

        Bag = new HashSet<>(arrayList);

        return poppedElement;
    }
    public HashSet<Tile> getBag() { return Bag;}
}



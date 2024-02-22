package pij.main.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a dictionary of words loaded from a file.
 * The dictionary is used to check if a given word exists in the word list.
 */
public class Dictionary {
    private ArrayList<String> wordList = new ArrayList<String>();

    public Dictionary() {
        load();
    }

    /**
     * Checks if a given word exists in the dictionary.
     *
     * @param word the word to check
     * @return true if the word exists in the dictionary, false otherwise
     */
    public boolean exists(String word) {
        return wordList.contains(word.toLowerCase());
    }

    /**
     * Loads the word list from a file.
     * The file should be located at "resources\\wordlist.txt".
     */
    public void load() {
        File file = new File("resources\\wordlist.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine();

            wordList.add(word);
        }
        scanner.close();
    }
}
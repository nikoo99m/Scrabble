package pij.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary {
    ArrayList<String> wordList = new ArrayList<String>();

    public boolean exists(String word) {
        return wordList.contains(word);
    }

    public void load() {
        File file = new File("C:\\Users\\Diamond\\IdeaProjects\\pij-2023-24-coursework-nikoo99m\\out\\production\\pij-2023-24-coursework-nikoo99m\\pij\\main\\wordlist.txt");
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
package pij.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultBoard {
    private List<String> readLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public Board testt(String filePath) {
        List<String> lines = readLinesFromFile(filePath);
        Board board = null;
        boolean x = false;
        String pattern = "\\.|\\(-?\\d+\\)|\\{-?\\d+\\}";
        int i = -1;
        for (String line : lines) {
            if(x == false)
            {
                int size = Integer.parseInt(line);
                board = new Board(size);
                x = true;
            }
            List<String> fields = extractFields(line, pattern);

            for (int j = 0; j < fields.size();  j++) {
                String field = fields.get(j);
                if (field.contains("{")) {
                    String regex = "\\{(-?\\d+)\\}";
                    Pattern patternx = Pattern.compile(regex);

                    Matcher matcher = patternx.matcher(field);
                    if (matcher.find()) {
                        String numberString = matcher.group(1);
                        // Do something with the numberString
                        board.letter[i][j] = new CurlySquare(Integer.parseInt(numberString));
                    }
                }
                else if (field.contains("(")) {
                    String regex = "\\((-?\\d+)\\)";
                    Pattern patternx = Pattern.compile(regex);

                    Matcher matcher = patternx.matcher(field);
                    if (matcher.find()) {
                        String numberString = matcher.group(1);
                        board.letter[i][j] = new BracketSquare(Integer.parseInt(numberString));
                        // Do something with the numberString
                    }
                }
                else {
                    board.letter[i][j] = new StandardSquare();
                }
                //System.out.println(field);
            }
            i++;
        }

        return board;
    }

    private List<String> extractFields(String input, String pattern) {
        List<String> fields = new ArrayList<>();
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);

        while (matcher.find()) {
            fields.add(matcher.group());
        }

        return fields;
    }


}

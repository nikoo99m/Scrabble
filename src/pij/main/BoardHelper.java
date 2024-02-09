package pij.main;

import pij.main.Exceptions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BoardHelper {
    private static List<String> readLinesFromFile(String filePath) {
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
            System.out.println("Can not find the file");
        }

        return lines;
    }

    public static Board loadBoardFromFile(String filePath) {

        try {
            List<String> lines = readLinesFromFile(filePath);

            int size = getSize(lines);

            Board board = getEmptyBoard(size);

            List<String> boardContentLines = lines.subList(1, lines.size());
            setBoardFields(boardContentLines, board, size);

            return board;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private static int getSize(List<String> lines) throws InvalidValueFormatForBoardSizeException {
        String sizeAsString = lines.get(0);
        int size;
        try {
            size = Integer.parseInt(sizeAsString);
        }
        catch (NumberFormatException ex) {throw new InvalidValueFormatForBoardSizeException(sizeAsString);}
        return size;
    }

    private static void setBoardFields(List<String> boardContentLines, Board board, int size)
            throws BoardContentSizeMismatchException, OutOfRangeBoardValueException, InvalidInputFormatAsPremiumFieldException {

        if (size != boardContentLines.size()) {
            throw new BoardContentSizeMismatchException();
        }

        for (int i = 0; i < boardContentLines.size(); i++) {

            List<String> fields = extractFields(boardContentLines.get(i));

            if (size != fields.size()) {
                throw new BoardContentSizeMismatchException();
            }

            for (int j = 0; j < fields.size(); j++) {
                String field = fields.get(j);
                if (field.contains("{")) {
                    setFieldAsCurlySquare(board, field, i, j);
                } else if (field.contains("(")) {
                    setFieldBracketSquare(board, field, i, j);
                } else {
                    setFieldStandardSquare(board, i, j);
                }
            }
        }
    }

    private static void setFieldStandardSquare(Board board, int i, int j) {
        board.letter[i][j] = new StandardSquare();
    }

    private static void setFieldBracketSquare(Board board, String field, int i, int j)
            throws OutOfRangeBoardValueException, InvalidInputFormatAsPremiumFieldException {
        String regex = "\\((-?\\d+)\\)";
        Pattern patternx = Pattern.compile(regex);

        Matcher matcher = patternx.matcher(field);
        if (matcher.find()) {
            String numberString = matcher.group(1);

            int number;
            try {
                number = Integer.parseInt(numberString);
            }
            catch (NumberFormatException ex) {throw new InvalidInputFormatAsPremiumFieldException(numberString);}

            // Validate if number falls within the range -9 to 99
            if (number >= -9 && number <= 99) {
                board.letter[i][j] = new BracketSquare(number);
            } else {
                throw new OutOfRangeBoardValueException();
            }
        }
    }

    private static void setFieldAsCurlySquare(Board board, String field, int i, int j)
            throws OutOfRangeBoardValueException, InvalidInputFormatAsPremiumFieldException {
        String regex = "\\{(-?\\d+)\\}";
        Pattern patternx = Pattern.compile(regex);

        Matcher matcher = patternx.matcher(field);
        if (matcher.find()) {
            String numberString = matcher.group(1);

            int number;
            try {
                number = Integer.parseInt(numberString);
            }
            catch (NumberFormatException ex) {throw new InvalidInputFormatAsPremiumFieldException(numberString);}

            // Validate if number falls within the range -9 to 99
            if (number >= -9 && number <= 99) {
                board.letter[i][j] = new CurlySquare(number);
            } else {
                throw new OutOfRangeBoardValueException();
            }
        }
    }

    private static Board getEmptyBoard(int size)
            throws InvalidBoardSizeException, InvalidValueFormatForBoardSizeException {

        Board board = null;
        if (size > 11 && size < 26) {
            board = new Board(size);
        } else {
            throw new InvalidBoardSizeException(size);
        }
        return board;
    }

    private static List<String> extractFields(String input) {
        String pattern = "\\.|\\(-?\\d+\\)|\\{-?\\d+\\}";

        List<String> fields = new ArrayList<>();
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);

        while (matcher.find()) {
            fields.add(matcher.group());
        }

        return fields;
    }
}

package pij.main;

import pij.main.Commands.SetBracketSquare;
import pij.main.Commands.SetCurlySquare;
import pij.main.Commands.SetStandardSquare;
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
        } catch (NumberFormatException ex) {
            throw new InvalidValueFormatForBoardSizeException(sizeAsString);
        }
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
                    new SetCurlySquare(board, i, j, field).setFieldSquare();
                } else if (field.contains("(")) {
                    new SetBracketSquare(board, i, j, field).setFieldSquare();

                } else {
                    new SetStandardSquare(board, i, j).setFieldSquare();
                }
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

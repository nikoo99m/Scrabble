package pij.main.utils;

import pij.main.commands.SetBracketSquare;
import pij.main.commands.SetCurlySquare;
import pij.main.commands.SetStandardSquare;
import pij.main.exceptions.*;
import pij.main.services.Board;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * This class provides utility methods to assist with loading and processing a game board from a file.
 */
public class BoardHelper {
    /**
     * Reads lines from a file and returns them as a list of strings.
     *
     * @param filePath The path to the file.
     * @return A list of strings representing lines read from the file.
     */
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

    /**
     * Loads a game board from a file.
     *
     * @param filePath The path to the file containing the board data.
     * @return The loaded Board object.
     * @throws InvalidValueFormatForBoardSizeException When the format of the board size value in the file is invalid.
     * @throws InvalidBoardSizeException               When the size of the board specified in the file is invalid.
     * @throws BoardContentSizeMismatchException       When the size of the board content does not match the specified size.
     * @throws InvalidInputFormatAsPremiumFieldException When the input format for a premium field is invalid.
     * @throws OutOfRangeBoardValueException           When a value on the board is out of the valid range.
     */
    public static Board loadBoardFromFile(String filePath) throws InvalidValueFormatForBoardSizeException,
            InvalidBoardSizeException, BoardContentSizeMismatchException,
            InvalidInputFormatAsPremiumFieldException, OutOfRangeBoardValueException {

            List<String> lines = readLinesFromFile(filePath);

            int size = getSize(lines);

            Board board = getEmptyBoard(size);

            List<String> boardContentLines = lines.subList(1, lines.size());
            setBoardFields(boardContentLines, board, size);

            return board;
    }
    /**
     * Extracts the size of the board from the list of lines read from the file.
     *
     * @param lines The list of lines read from the file.
     * @return The size of the board.
     * @throws InvalidValueFormatForBoardSizeException When the format of the board size value is invalid.
     */
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
    /**
     * Sets the fields of the board based on the content read from the file.
     *
     * @param boardContentLines The content of the board read from the file.
     * @param board             The Board object to set fields on.
     * @param size              The size of the board.
     * @throws BoardContentSizeMismatchException       When the size of the board content does not match the specified size.
     * @throws OutOfRangeBoardValueException           When a value on the board is out of the valid range.
     * @throws InvalidInputFormatAsPremiumFieldException When the input format for a premium field is invalid.
     */
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
    /**
     * Creates an empty Board object with the specified size.
     *
     * @param size The size of the board.
     * @return An empty Board object with the specified size.
     * @throws InvalidBoardSizeException               When the size of the board is invalid.
     * @throws InvalidValueFormatForBoardSizeException When the format of the board size value is invalid.
     */
    private static Board getEmptyBoard(int size)
            throws InvalidBoardSizeException, InvalidValueFormatForBoardSizeException {

        Board board = null;
        if (size > 11 && size <= 26) {
            board = new Board(size);
        } else {
            throw new InvalidBoardSizeException(size);
        }
        return board;
    }
    /**
     * Extracts fields from a string representing a line of the board content.
     *
     * @param input The input string representing a line of the board content.
     * @return A list of strings representing the extracted fields.
     */
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

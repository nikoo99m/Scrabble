package pij.main.commands;

import pij.main.services.Board;
import pij.main.exceptions.InvalidInputFormatAsPremiumFieldException;
import pij.main.exceptions.OutOfRangeBoardValueException;
import pij.main.models.squares.Square;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Abstract class representing a premium square on the game board.
 * Premium squares provide special bonuses or penalties to players.
 */
public abstract class PremiumSquare extends SetFieldSquare
{
    private final String field;
    private final String pattern;
    public PremiumSquare(Board board, int i, int j, String field, String pattern) {
        super(board, i, j);
        this.field = field;
        this.pattern = pattern;
    }

    public abstract Square getPremiumSquare(int number);
    /**
     * Creates the premium square based on the provided field value.
     * This method extracts the premium value from the field using the specified pattern,
     * validates the extracted value, and creates the appropriate premium square.
     *
     * @return The created premium square.
     * @throws InvalidInputFormatAsPremiumFieldException If the input format of the premium field is invalid.
     * @throws OutOfRangeBoardValueException             If the premium value is out of range.
     */
    @Override
    protected Square createSquare()
            throws InvalidInputFormatAsPremiumFieldException, OutOfRangeBoardValueException {
        Pattern patternx = Pattern.compile(pattern);

        Matcher matcher = patternx.matcher(field);

        matcher.find();
        String numberString = matcher.group(1);

        int number;
        try {
            number = Integer.parseInt(numberString);
        } catch (NumberFormatException ex) {
            throw new InvalidInputFormatAsPremiumFieldException(numberString);
        }


        if (number >= -9 && number <= 99) {
            return getPremiumSquare(number);
        } else {
            throw new OutOfRangeBoardValueException();
        }
    }
}

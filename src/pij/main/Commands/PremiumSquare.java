package pij.main.Commands;

import pij.main.Board;
import pij.main.Exceptions.InvalidInputFormatAsPremiumFieldException;
import pij.main.Exceptions.OutOfRangeBoardValueException;
import pij.main.Squares.Square;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PremiumSquare extends  SetFieldSquare
{
    private final String field;
    private final String pattern;
    public PremiumSquare(Board board, int i, int j, String field, String pattern) {
        super(board, i, j);
        this.field = field;
        this.pattern = pattern;
    }
    public abstract Square getPremiumSquare(int number);
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

        // Validate if number falls within the range -9 to 99
        if (number >= -9 && number <= 99) {
            return getPremiumSquare(number);
        } else {
            throw new OutOfRangeBoardValueException();
        }
    }
}

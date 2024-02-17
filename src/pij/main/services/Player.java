package pij.main.services;

import pij.main.models.*;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.MethodReturns.WildCardReturn;
import pij.main.utils.StringHelper;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player extends AbstractPlayer {

    public Player(TileBag tileBag, Board board, Dictionary dictionary, Game game, String name) {
        super(tileBag, board, dictionary, game, name);
    }

    @Override
    public WildCardReturn wildCardExists() {
        for (int i = 0; i < playerRack.Rack.length; i++) {
            if (playerRack.Rack[i] != null && playerRack.Rack[i].character.equals("_")) {
                return new WildCardReturn(i, true);
            }
        }
        return new WildCardReturn(-1, false);
    }

    @Override
    public MoveReturn move() {

        String moveAsString = getUserInput();
        if (moveAsString.equals(",")) {
            return new MoveReturn(MoveReturn.MoveResult.Pass);
        }

        boolean isUserInputValid = validateMoveInput(moveAsString);
        if (!isUserInputValid)
            return new MoveReturn(MoveReturn.MoveResult.Failed);


        Result result = getResult(moveAsString);

        if (!checkMoveIsValid(result, moveAsString, true))
            return new MoveReturn(MoveReturn.MoveResult.Failed);

        String acceptedWord = getAcceptedWord(result);

        setTile(result);

        return new MoveReturn(acceptedWord, result.location(), MoveReturn.MoveResult.Done, result.vertical(), result.tileSelection().length() == 7);
    }

    private Result getResult(String moveAsString) {
        String[] parts = moveAsString.split(",");
        String tileSelection = parts[0];
        String startingPoint = parts[1];

        boolean vertical = isVertical(startingPoint);
        Location location = getLocation(vertical, startingPoint);

        return new Result(tileSelection, vertical, location);
    }

    private static boolean validateMoveInput(String moveAsString) {
        String pattern = "[a-zA-Z]+,((\\d{1,2}[a-zA-Z])|([a-zA-Z]\\d{1,2}))";
        if (!moveAsString.matches(pattern)) {
            System.out.println("Invalid move format. Please enter your move in the format: \"word,square\" (without the quotes)\n" +
                    "For example, for suitable tile rack and board configuration, a downward move\n" +
                    "could be \"HI,f4\" and a rightward move could be \"HI,4f\".\n" +
                    "In the word, upper-case letters are standard tiles\n" +
                    "and lower-case letters are wildcards.\n" +
                    "Entering \",\" passes the turn.");
            return false;
        }
        return true;
    }

    private static String getUserInput() {
        System.out.println("Please enter your move in the format: \"word,square\" (without the quotes)");
        Scanner scan = new Scanner(System.in);
        String moveAsString = scan.nextLine();
        return moveAsString;
    }
}


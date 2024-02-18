package pij.main.services;

import pij.main.models.*;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.MethodReturns.WildCardReturn;
import pij.main.models.MethodReturns.WordChoice;
import pij.main.models.interfaces.Validator;
import pij.main.utils.GameHelper;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Player extends AbstractPlayer {

    public Player(TileBag tileBag, Board board, Game game, String name) {
        super(tileBag, board, game, name);
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

        Validator validator =  new PlayerInputValidator(result, moveAsString, true, playerRack, board, game, dictionary);
        if (!validator.Validate())
            return new MoveReturn(MoveReturn.MoveResult.Failed);

        WordChoice acceptedWord = GameHelper.getAcceptedWord(result, board);

        setTile(result);

        return new MoveReturn(acceptedWord, result.location(), MoveReturn.MoveResult.Done, result.vertical(), result.tileSelection().length() == 7, result.tileSelection());
    }
    private Result getResult(String moveAsString) {
        String[] parts = moveAsString.split(",");
        String tileSelection = parts[0];
        String startingPoint = parts[1];

        boolean vertical = isVertical(startingPoint);
        Location location = getLocation(vertical, startingPoint);

        return new Result(tileSelection, vertical, location);
    }
    private boolean isVertical(String location) {
        if (Character.isDigit(location.charAt(0))) {
            return false;
        }
        return true;
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
    @Override
    public void setWildCardIfExists() {
        WildCardReturn wcr = playerRack.wildCardExists();
        while (wcr.isWildCard) {
            System.out.println("Do you want to use the wildCard?");
            System.out.println("If you want just say true otherwise false.");
            boolean answer = false;
            while (true) {
                try {
                    Scanner scanner = new Scanner(System.in);
                    answer = scanner.nextBoolean();
                    System.out.println("You entered: " + answer);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please enter true/false :");
                }
            }
            Scanner scanner = new Scanner(System.in);

            if (answer) {
                System.out.println("Enter your desired character:" + " ");
                String wildCardValue = scanner.next();
                playerRack.Rack[wcr.index].character = wildCardValue;
                System.out.println(playerRack.toString());
            } else
                break;
            wcr = playerRack.wildCardExists();
        }
    }
}


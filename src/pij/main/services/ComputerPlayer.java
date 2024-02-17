package pij.main.services;

import pij.main.models.Dictionary;
import pij.main.models.Location;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.MethodReturns.WildCardReturn;
import pij.main.models.MethodReturns.WordChoice;
import pij.main.models.TileBag;
import pij.main.services.AbstractPlayer;
import pij.main.services.Board;
import pij.main.services.Game;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComputerPlayer extends AbstractPlayer {
    public ComputerPlayer(TileBag tileBag, Board board, Dictionary dictionary, Game game, String name) {
        super(tileBag, board, dictionary, game, name);
    }

    @Override
    public void setWildCardIfExists(AbstractPlayer player) {
        Random random = new Random();
        WildCardReturn wcr = player.wildCardExists();

        while (wcr.isWildCard) {
            int randomIndex = random.nextInt(26);
            char randomChar = (char) ('a' + randomIndex);
            player.getRack().Rack[wcr.index].character = String.valueOf(randomChar);

            System.out.println("Computer has decided to use its Wild Card!");
            System.out.println("Computer has chosen the character:" + randomChar);
            System.out.println(player.getRack().toString());

            wcr = player.wildCardExists();
        }
    }
    @Override
    public MoveReturn move() {
        String[] array = Arrays.stream(playerRack.Rack)
                .filter(x -> x != null && !x.character.equals("_"))
                .map(x -> x.character).toArray(String[]::new);
        List<String> permutations = generatePermutations(array);

        for (String permutation : permutations) {
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {

                    Location startingPoint = new Location(i, j);

                    for (boolean isVerticalMove : new boolean[]{true, false}) {
                        Result result = new Result(permutation, isVerticalMove, startingPoint);
                        if (checkMoveIsValid(result, permutation, false)) {
                            return performMove(result);
                        }
                    }
                }
            }
        }
        return new MoveReturn(MoveReturn.MoveResult.Pass);
    }
    private MoveReturn performMove(Result result) {
        WordChoice acceptedWord = getAcceptedWord(result);
        setTile(result);

        return new MoveReturn(acceptedWord, result.location(), MoveReturn.MoveResult.Done,
                result.vertical(), result.tileSelection().length() == 7);
    }
    private static List<String> generatePermutations(String[] array) {
        List<String> result = new ArrayList<>();
        for (int len = 1; len <= array.length; len++) {
            generatePermutations(array, new boolean[array.length], new StringBuilder(), result, len);
        }
        return result;
    }
    private static void generatePermutations(String[] array, boolean[] used, StringBuilder current, List<String> result, int len) {
        if (current.length() == len) {
            result.add(current.toString());
            return;
        }
        for (int i = 0; i < array.length; i++) {
            if (!used[i]) {
                used[i] = true;
                current.append(array[i]);
                generatePermutations(array, used, current, result, len);
                current.deleteCharAt(current.length() - 1);
                used[i] = false;
            }
        }
    }
}

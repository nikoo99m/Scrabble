package pij.main;

import pij.main.models.Dictionary;
import pij.main.models.Location;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.TileBag;
import pij.main.services.Board;
import pij.main.services.Game;
import pij.main.services.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ComputerPlayer extends Player {
    public ComputerPlayer(TileBag tileBag, Board board, Dictionary dictionary, Game game, String name) {
        super(tileBag, board, dictionary, game, name);
    }

    @Override
    public MoveReturn move() {
        String[] array = Arrays.stream(playerRack.Rack)
                .filter(x -> !x.character.equals("_"))
                .map(x -> x.character).toArray(String[]::new);

        List<String> permutations = generatePermutations(array);
        for (String permutation : permutations) {
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {

                    Location startingPoint = new Location(i, j);
                    Result result = new Result(permutation, "", true, startingPoint);

                    if (checkMoveIsValid(result, permutation, false)) {
                        String acceptedWord = getAcceptedWord(result);
                        setTile(result);

                        return new MoveReturn(acceptedWord, result.location(), MoveReturn.MoveResult.Done,
                                result.vertical(), result.startingPoint(), result.tileSelection().length() == 7);
                    }

                    result = new Result(permutation, "", false, startingPoint);

                    if (checkMoveIsValid(result, permutation, false)) {
                        String acceptedWord = getAcceptedWord(result);
                        setTile(result);

                        return new MoveReturn(acceptedWord, result.location(), MoveReturn.MoveResult.Done,
                                result.vertical(), result.startingPoint(), result.tileSelection().length() == 7);
                    }
                }
            }
        }
        return new MoveReturn(MoveReturn.MoveResult.Pass);
    }


    public static List<String> generatePermutations(String[] array) {
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

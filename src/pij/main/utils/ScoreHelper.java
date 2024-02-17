package pij.main.utils;

import pij.main.services.AbstractPlayer;
import pij.main.services.Board;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.Squares.BracketSquare;
import pij.main.models.Squares.Square;
import pij.main.models.Squares.StandardSquare;
import pij.main.services.Player;

import java.util.ArrayList;

public class ScoreHelper {

    public static void CalculatingplayerScore(Board board, MoveReturn moveReturn, AbstractPlayer player) {

        int score = 0;
        if (moveReturn.result == MoveReturn.MoveResult.Done) {
            int i = moveReturn.details.location.i;
            int j = moveReturn.details.location.j;
            int premiumWordMultiplier = 1;
            for (int r = 0; r < moveReturn.details.word.length(); r++) {
                Square square = board.letter[i][j];
                if (square instanceof BracketSquare) {
                    int value = square.tile.value * square.multiplier;
                    score += value;
                } else if (square instanceof StandardSquare) {
                    int value = square.tile.value;
                    score += value;
                } else {
                    premiumWordMultiplier *= square.multiplier;
                    int value = square.tile.value;
                    score += value;
                }
                if (moveReturn.details.vertical) {
                    i++;
                } else {
                    j++;
                }
                square.multiplier = 1;
            }
            score *= premiumWordMultiplier;
        }
        if (moveReturn.details != null)
            if (moveReturn.details.sevenTilesPlayed) {
                score += 75;
            }
        player.setScore(score);

    }

    public static void updateScoresAtTheEndOfGame(ArrayList<AbstractPlayer> players) {
        for (AbstractPlayer player : players) {
            int penalty = 0;
            if (!player.playerRack.isEmpty()) {
                for (int i = 0; i < player.playerRack.Rack.length; i++) {
                    if (player.playerRack.Rack[i] != null)
                        penalty += player.playerRack.Rack[i].value;
                }
                player.score -= penalty;
            }
        }
    }

}
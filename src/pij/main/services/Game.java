package pij.main.services;

import pij.main.models.Dictionary;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.TileBag;
import pij.main.utils.BoardHelper;
import pij.main.utils.ScoreHelper;

import java.util.ArrayList;

public class Game {

    ArrayList<Player> players = new ArrayList<Player>();
    ArrayList<MoveReturn.MoveResult> moves = new ArrayList<>();
    Board board;
    public boolean isFirstMove = true;
    private TileBag bag;

    public Game() {
        String filePath = "resources\\defaultBoard.txt";
        board = BoardHelper.loadBoardFromFile(filePath);
        bag = new TileBag();
        Dictionary dictionary = new Dictionary();

        players.add(new Player(bag, board, dictionary, this, "noobak"));
        players.add(new Player(bag, board, dictionary, this, "noob-o-din"));

    }

    public void play() {
        board.prettyPrint();
        while (true) {
            for (Player player : players) {
                System.out.println("It is " + player.name + " turn.");

                fillTileRack(player, bag);
                MoveReturn moveReturn = processPlayerMove(player);
                ScoreHelper.CalculatingplayerScore(board, moveReturn, player);

                System.out.println("player " + player.name + " score is:" + player.getScore());

                board.prettyPrint();

                if(hasGameEnded())
                    return;
            }
        }
    }

    private boolean hasGameEnded() {
        boolean isRackEmptyForAPlayer = false;
        for (Player player : players) {
            if (player.getRack().isEmpty())
                isRackEmptyForAPlayer = true;
        }
        if (bag.isEmpty() && isRackEmptyForAPlayer) {
            System.out.println("Game has ended since tile bag is empty and a player has an empty rack.");
            return true;
        }

//        boolean playersHavePassedTwice = moves.stream()
//            .skip(Math.max(0, moves.size() - 4))
//            .allMatch(element -> element == MoveReturn.MoveResult.Pass);

        boolean playersHavePassedTwice = true;
        if (moves.size() >= 4) {
            for (int i = moves.size() - 4; i < moves.size(); i++) {
                if (!moves.get(i).equals(MoveReturn.MoveResult.Pass)) {
                    playersHavePassedTwice = false;
                    break;
                }
            }
            if (playersHavePassedTwice) {
                System.out.println("Game has ended due to players passing twice.");
                return true;
            }
        }

        return false;
    }

    private void fillTileRack(Player player, TileBag bag) {
        while (player.playerRack.add(bag.randomPop()))
            ;
        //player.playerRack.Rack[0].character = "_";
    }

    private MoveReturn processPlayerMove(Player player) {
        MoveReturn moveReturn = player.move();

        while (moveReturn.result == MoveReturn.MoveResult.Failed) {
            moveReturn = player.move();
        }

        if (moveReturn.result == MoveReturn.MoveResult.Pass) {
            System.out.println("player " + player.name + " passes the turn.");
        }

        moves.add(moveReturn.result);

        return moveReturn;
    }
}

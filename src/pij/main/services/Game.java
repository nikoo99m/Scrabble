package pij.main.services;

import pij.main.models.Dictionary;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.TileBag;
import pij.main.utils.BoardHelper;
import pij.main.utils.ScoreHelper;

import java.util.ArrayList;

public class Game {

    ArrayList<Player> players = new ArrayList<Player>();
    Board board;
    public boolean isFirstMove = true;
    private TileBag bag;
    public Game()
    {
        String filePath = "resources\\defaultBoard.txt";
        board = BoardHelper.loadBoardFromFile(filePath);
        bag = new TileBag();
        Dictionary dictionary = new Dictionary();

        players.add(new Player(bag, board, dictionary, this, "noobak"));
        players.add(new Player(bag, board, dictionary, this, "noob-o-din"));

    }

    public void play()
    {
        board.prettyPrint();
        while (true)
        {
            for (Player player : players) {
                System.out.println("It is " + player.name + " turn.");

                fillTileRack(player, bag);
                MoveReturn moveReturn = processPlayerMove(player);
                ScoreHelper.CalculatingplayerScore(board, moveReturn, player);

                System.out.println("player " + player.name + " score is:" + player.getScore());

                board.prettyPrint();
            }
        }
    }
    public void fillTileRack(Player player, TileBag bag) {
        while (player.playerRack.add(bag.randomPop()))
            ;
        //playerRack.Rack[0].character = "_";
    }
    private static MoveReturn processPlayerMove(Player player) {
        MoveReturn moveReturn = player.move();
        while(moveReturn.result == MoveReturn.MoveResult.Failed){
            moveReturn = player.move();
        }
        if(moveReturn.result == MoveReturn.MoveResult.Pass)
            System.out.println("player "+ player.name + " passes the turn.");
        return moveReturn;
    }
}

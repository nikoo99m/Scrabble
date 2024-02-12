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
    public Game()
    {
        String filePath = "resources\\defaultBoard.txt";
        board = BoardHelper.loadBoardFromFile(filePath);
        TileBag bag = new TileBag();
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
                player.fillTileRack();
                MoveReturn moveReturn = player.move();
                while(moveReturn == null || !moveReturn.move){
                    moveReturn = player.move();
                }

                int score = ScoreHelper.CalculatingplayerScore(board, moveReturn);
                player.setScore(score);
                System.out.println("player " + player.name + " score is:" + player.getScore());

                board.prettyPrint();
            }
        }
    }
}

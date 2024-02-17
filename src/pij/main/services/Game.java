package pij.main.services;

import pij.main.ComputerPlayer;
import pij.main.models.Dictionary;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.models.MethodReturns.WildCardReturn;
import pij.main.models.TileBag;
import pij.main.utils.BoardHelper;
import pij.main.utils.ScoreHelper;
import pij.main.utils.StringHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game {

    static ArrayList<AbstractPlayer> players = new ArrayList<AbstractPlayer>();
    ArrayList<MoveReturn.MoveResult> moves = new ArrayList<>();
    Board board;
    public boolean isFirstMove = true;
    private TileBag bag;


    public Game() {
        String filePath = "resources\\defaultBoard.txt";
        board = BoardHelper.loadBoardFromFile(filePath);
        bag = new TileBag();
        Dictionary dictionary = new Dictionary();

//        players.add(new Player(bag, board, dictionary, this, "HumanPlayer"));
        players.add(new ComputerPlayer(bag, board, dictionary, this, "Noobak"));

        players.add(new ComputerPlayer(bag, board, dictionary, this, "ComputerPlayer"));
//        players.add(new Player(bag, board, dictionary, this, "ComputerPlayer"));

    }

    public void play() {
        //startOfTheGame();
        board.prettyPrint();
        while (true) {
            for (AbstractPlayer player : players) {

                System.out.println("It is " + player.name + " turn Your tiles:");

                fillTileRack(player, bag);
                setWildCardIfExists(player);

                MoveReturn moveReturn = processPlayerMove(player);
                ScoreHelper.CalculatingplayerScore(board, moveReturn, player);

                System.out.println("player " + player.name + " score is:" + player.getScore());

                board.prettyPrint();

                if (hasGameEnded())
                    return;
            }

//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }
    }

    private boolean hasGameEnded() {
        boolean aPlayerHasAnEmptyRack = players.stream()
                .anyMatch(player -> player.getRack().isEmpty());

        if (bag.isEmpty() && aPlayerHasAnEmptyRack) {
            System.out.println("Game has ended since tile bag is empty and a player has an empty rack.");
            ScoreHelper.updateScoresAtTheEndOfGame(players);
            accounceGameResult();
            return true;
        }

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

    private void accounceGameResult() {
        for (AbstractPlayer player : players) {
            System.out.println(player.name + "'s final score is: " + player.getScore());
        }
        boolean allSame = players.stream()
                .map(AbstractPlayer::getScore)
                .allMatch(score -> score == players.get(0).getScore());
        if (allSame)
            System.out.println("Players have tied, it's a draw!");
        else {
            Optional<AbstractPlayer> winner = players.stream().max(Comparator.comparingInt(AbstractPlayer::getScore));
            winner.ifPresent(player -> System.out.println(player.name + " is the winner!"));
        }
    }

    private void fillTileRack(AbstractPlayer player, TileBag bag) {
        while (!bag.isEmpty() && player.playerRack.add(bag.randomPop()))
            ;
        //player.playerRack.Rack[0].character = "_";
        System.out.println(player.getRack().toString());
    }

    private MoveReturn processPlayerMove(AbstractPlayer player) {
        MoveReturn moveReturn = player.move();

        while (moveReturn.result == MoveReturn.MoveResult.Failed) {
            moveReturn = player.move();
        }

        if (moveReturn.result == MoveReturn.MoveResult.Pass) {
            System.out.println("player " + player.name + " passes the turn.");
        } else {
            System.out.println("The move is: Word: " + moveReturn.details.word.word +
                    " at position " + StringHelper.printLocation(moveReturn.details.location, moveReturn.details.vertical));
            if (isFirstMove)
                isFirstMove = false;
        }

        moves.add(moveReturn.result);

        return moveReturn;
    }

    private void setWildCardIfExists(AbstractPlayer player) {
        WildCardReturn wcr = player.wildCardExists();
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
                player.getRack().Rack[wcr.index].character = wildCardValue;
                System.out.println(player.getRack().toString());
            } else
                break;
            wcr = player.wildCardExists();
        }
    }
}

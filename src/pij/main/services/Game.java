package pij.main.services;

import pij.main.exceptions.*;
import pij.main.models.Dictionary;
import pij.main.models.MethodReturns.MoveReturn;
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
    boolean isOpen;

    public Game() throws DefaultBoardNotFoundException {
//        String input = getBoardLoadingChoice();
//        board = loadBoard(input);
//        isOpen = setOpenOrClosedGameStates();

        board = loadBoard("d");
        bag = new TileBag();
        players.add(new Player(bag, board, this, "HumanPlayer"));
        players.add(new ComputerPlayer(bag, board, this, "ComputerPlayer"));

//        players.add(new ComputerPlayer(bag, board, this, "ComputerPlayer1"));
//        players.add(new ComputerPlayer(bag, board, this, "ComputerPlayer2"));

//        players.add(new Player(bag, board, this, "HumanPlayer1"));
//        players.add(new Player(bag, board, this, "HumanPlayer2"));

    }

    private String getBoardLoadingChoice() {
        String userInput = "";
        while (true) {
            try {
                System.out.println("Would you like to _l_oad a board or use the _d_efault board?");
                System.out.println("Please enter your choice (l/d):");
                Scanner scanner = new Scanner(System.in);
                userInput = scanner.nextLine();

                if (Objects.equals(userInput, "d") || Objects.equals(userInput, "l")) {
                    break;
                } else {
                    System.out.println("Invalid input! Please enter 'l' or 'd'.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter 'l' or 'd'.");
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }
        return userInput;
    }
    private Board loadBoard(String input) throws DefaultBoardNotFoundException {
        if (input.equals("d")) {
            try {
                return BoardHelper.loadBoardFromFile("resources\\defaultBoard.txt");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                throw new DefaultBoardNotFoundException();
            }
        } else {
            while (true) {
                System.out.println("Please enter the file name of the board:");
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();
                File file = new File(userInput);
                try {
                    if (!file.exists()) {
                        throw new FileNotFoundException("File not found: " + userInput);
                    }
                    return BoardHelper.loadBoardFromFile(userInput);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }
    private boolean setOpenOrClosedGameStates() {
        while (true) {
            try {
                System.out.println("Would you like to play an _o_pen or a _c_losed game?");
                System.out.println("Please enter your choice (o/c):");
                Scanner scanner = new Scanner(System.in);
                String userInput = scanner.nextLine();

                if (Objects.equals(userInput, "c")) {
                    return false;
                } else if (Objects.equals(userInput, "o")) {
                    return true;
                } else {
                    System.out.println("Invalid input! Please enter 'c' or 'o'.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter 'c' or 'o'.");
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Error reading input. Please try again.");
            }
        }

    }
    public void play() {

        board.prettyPrint();

        players.forEach(x -> fillPlayerTileRack(x, bag));

        while (true) {
            for (AbstractPlayer player : players) {
                if (isOpen && player instanceof Player) {
                    AbstractPlayer computer = players.stream()
                            .filter(x -> x instanceof ComputerPlayer)
                            .findFirst()
                            .get();
                    System.out.println("OPEN GAME: The Computer's tiles:");
                    System.out.println("OPEN GAME:" + computer.getRack().toString());
                    System.out.println("It is " + player.name + " turn Your tiles:");
                    System.out.println(player.getRack().toString());
                } else {
                    System.out.println("It is " + player.name + " turn Your tiles:");
                    System.out.println(player.getRack().toString());
                }
                player.setWildCardIfExists();

                MoveReturn moveReturn = processPlayerMove(player);
                fillPlayerTileRack(player, bag);

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
            announceGameResult();
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
                ScoreHelper.updateScoresAtTheEndOfGame(players);
                announceGameResult();
                return true;
            }
        }

        return false;
    }
    private void announceGameResult() {
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
    private void fillPlayerTileRack(AbstractPlayer player, TileBag bag) {
        while (!bag.isEmpty() && player.playerRack.hasEmpty())
            player.playerRack.add(bag.randomPop());

        //player.playerRack.Rack[0].character = "_";
//        System.out.println(player.getRack().toString());
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
}

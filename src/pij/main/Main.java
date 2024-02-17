package pij.main;


import pij.main.exceptions.DefaultBoardNotFoundException;
import pij.main.models.*;
import pij.main.models.MethodReturns.MoveReturn;
import pij.main.services.Board;
import pij.main.services.Game;
import pij.main.services.Player;
import pij.main.utils.BoardHelper;
import pij.main.utils.ScoreHelper;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Game game = null;
        try {
            game = new Game();
            game.play();
        } catch (DefaultBoardNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
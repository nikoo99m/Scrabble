package pij.main;


import pij.main.exceptions.DefaultBoardNotFoundException;
import pij.main.services.Game;

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
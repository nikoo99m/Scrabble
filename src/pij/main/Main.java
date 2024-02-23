package pij.main;


import pij.main.exceptions.DefaultBoardNotFoundException;
import pij.main.services.Game;

public class Main {

    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.play();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
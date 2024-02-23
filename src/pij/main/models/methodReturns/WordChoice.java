package pij.main.models.methodReturns;

import pij.main.models.Location;
/**
 * Represents the choice of a word made by a player and its starting point on the game board.
 */
public class WordChoice {
    public String word;
    public Location wordStartingPoint;

    public WordChoice(String word, Location wordStartingPoint) {
        this.word = word;
        this.wordStartingPoint = wordStartingPoint;
    }
}

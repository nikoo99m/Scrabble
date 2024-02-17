package pij.main.models.MethodReturns;

import pij.main.models.Location;

public class WordChoice {
    public String word;
    public Location wordStartingPoint;

    public WordChoice(String word, Location wordStartingPoint) {
        this.word = word;
        this.wordStartingPoint = wordStartingPoint;
    }
}

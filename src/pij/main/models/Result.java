package pij.main.models;
/**
 * Represents the result of a player's move in the game.
 * It includes information such as the selected tiles for the move, whether the move is vertical,
 * and the location on the game board where the move starts.
 */
public record Result(String tileSelection, boolean vertical, Location location) {
}

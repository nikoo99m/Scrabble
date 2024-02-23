package pij.main.models.methodReturns;
/**
 * Represents the result of checking for the presence of a wild card in a player's rack and clarify the index of the wildCard in player's rack.
 */
public class WildCardReturn {
    public int index;
    public boolean isWildCard;

    public WildCardReturn(int index, boolean isWildCard){
        this.isWildCard = isWildCard;
        this.index = index;
    }
}

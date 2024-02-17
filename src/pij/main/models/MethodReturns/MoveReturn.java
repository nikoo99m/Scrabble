package pij.main.models.MethodReturns;

import pij.main.models.Location;

public class MoveReturn {

    public MoveResult result;
    public MoveDetails details;

    public MoveReturn(WordChoice word, Location location, MoveResult result, boolean vertical, boolean sevenTilesPlayed) {
        this.details = new MoveDetails(word, location, vertical, sevenTilesPlayed);
        this.result = result;
    }
    public MoveReturn(MoveResult result) {
        this.result = result;
    }

    public enum MoveResult
    {
        Pass,
        Done,
        Failed
    }
    public class MoveDetails {
        public WordChoice word;
        public Location location;
        public boolean vertical;
        public boolean sevenTilesPlayed;

        public MoveDetails(WordChoice word, Location location, boolean vertical, boolean sevenTilesPlayed) {
            this.word = word;
            this.location = location;
            this.vertical = vertical;
            this.sevenTilesPlayed = sevenTilesPlayed;
        }
        public MoveDetails(String word) {
            this.word.word = word;
        }
    }
}

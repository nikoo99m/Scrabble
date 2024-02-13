package pij.main.models.MethodReturns;

import pij.main.models.Location;

public class MoveReturn {

    public MoveResult result;
    public MoveDetails details;

    public MoveReturn(String word, Location location, MoveResult result, boolean vertical, String position) {
        this.details = new MoveDetails(word, location, vertical, position);
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
        public String word;
        public String position;
        public Location location;
        public boolean vertical;

        public MoveDetails(String word, Location location, boolean vertical, String position) {
            this.word = word;
            this.location = location;
            this.vertical = vertical;
            this.position = position;
        }
        public MoveDetails(String word) {
            this.word = word;
        }
    }
}

package pij.main.models.MethodReturns;

public class MoveReturn {

    public MoveResult result;
    public MoveDetails details;

    public MoveReturn(String word, int i, int j, MoveResult result, boolean vertical) {

        this.details = new MoveDetails(word, i, j, vertical);
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
        public int i;
        public int j;
        public boolean vertical;

        public MoveDetails(String word, int i, int j, boolean vertical) {
            this.word = word;
            this.i = i;
            this.j = j;
            this.vertical = vertical;
        }
    }
}

package pij.main.models.MethodReturns;

public class MoveReturn {
    public String word;
    public int i;
    public int j;
    public boolean move = false;
    public boolean vertical;


    public MoveReturn(String word, int i, int j, boolean move, boolean vertical) {
        this.i = i;
        this.word = word;
        this.j = j;
        this.vertical = vertical;
        this.move = move;
    }


}

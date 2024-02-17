package pij.main.models;

public class Location {
    public int i;
    public int j;

    public Location(String numberPart, String charPart) {
        this.j = get(charPart.charAt(0));
        this.i = Integer.parseInt(numberPart) - 1;

    }
    public Location(int i, int j) {
        this.j = j;
        this.i = i;

    }
    private int get(char startChar) {
        if (startChar >= 'a' && startChar <= 'z') {
            return startChar - 'a';
        } else {
            // Handle invalid input
            throw new IllegalArgumentException("Invalid input: " + startChar);
        }
    }

    @Override
    public String toString() {
        char ch = (char) (j + 'a');
        String str = Character.toString(ch);
        return (i + 1) + str;
    }
}


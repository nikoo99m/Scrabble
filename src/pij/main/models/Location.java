package pij.main.models;

/**
 * Represents a location identified by its row and column indices.
 */
public class Location {
    public int i;
    public int j;

    /**
     * Constructs a Location object using the given numerical and character parts.
     *
     * @param numberPart The numerical part of the location (e.g., row number)
     * @param charPart   The character part of the location (e.g., column letter)
     */
    public Location(String numberPart, String charPart) {
        this.j = get(charPart.charAt(0));
        this.i = Integer.parseInt(numberPart) - 1;

    }

    /**
     * Constructs a Location object with the given row and column indices.
     *
     * @param i The row index
     * @param j The column index
     */
    public Location(int i, int j) {
        this.j = j;
        this.i = i;

    }

    /**
     * Converts a character representing into its corresponding index.
     *
     * @param startChar The character representing the start column
     * @return The corresponding column index
     * @throws IllegalArgumentException if the input character is not within the range 'a' to 'z'
     */
    private int get(char startChar) {
        if (startChar >= 'a' && startChar <= 'z') {
            return startChar - 'a';
        } else {
            throw new IllegalArgumentException("Invalid input: " + startChar);
        }
    }

    @Override
    public String toString() {
        char character = (char) (j + 'a');
        String str = Character.toString(character);
        return (i + 1) + str;
    }
}


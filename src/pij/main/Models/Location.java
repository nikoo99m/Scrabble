package pij.main.Models;

public class Location {
    public int numberPart;
    public int charPart;

    public Location(String numberPart, String charPart) {
        this.charPart = get(charPart.charAt(0));
        this.numberPart = Integer.parseInt(numberPart);

    }
    private int get(char startChar) {
        if (startChar >= 'a' && startChar <= 'z') {
            return startChar - 'a';
        } else {
            // Handle invalid input
            throw new IllegalArgumentException("Invalid input: " + startChar);
        }
    }
}


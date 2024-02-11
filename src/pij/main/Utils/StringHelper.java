package pij.main.Utils;

public class StringHelper {
    public static String set5(String inputString)
    {
        int length = 5;
        if (inputString.length() < length) {
            // Calculate the number of spaces needed
            int spacesToAdd = length - inputString.length();

            // Add spaces to the end of the string
            for (int i = 0; i < spacesToAdd; i++) {
                inputString += " ";
            }
        }
        return inputString;
    }
}


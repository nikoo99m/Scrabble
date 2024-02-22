package pij.main.utils;

import pij.main.models.Location;

/**
 * This class provides utility methods for manipulating strings.
 */
public class StringHelper {
    /**
     * Ensures that the input string has a length of at least 5 characters by adding spaces to the end if necessary.
     *
     * @param inputString The input string to be modified.
     * @return The modified string with a length of at least 5 characters.
     */
    public static String set5(String inputString) {
        int length = 5;
        if (inputString.length() < length) {
            int spacesToAdd = length - inputString.length();

            for (int i = 0; i < spacesToAdd; i++) {
                inputString += " ";
            }
        }
        return inputString;
    }

    /**
     * Prints the location string representation.
     *
     * @param location   The location object to be printed.
     * @param isVertical A boolean indicating whether to print in vertical format.
     * @return The string representation of the location.
     */

    public static String printLocation(Location location, boolean isVertical) {
        String locationString = location.toString();
        if (isVertical)
            return locationString.substring(locationString.length() - 1) + locationString.substring(0, locationString.length() - 1);
        else
            return location.toString();
    }
}


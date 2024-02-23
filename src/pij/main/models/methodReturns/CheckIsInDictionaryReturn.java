package pij.main.models.methodReturns;
/**
 * Represents the result of checking if a word is present in the dictionary.
 * <p>
 * An instance contains
 * - The accepted word found in the dictionary.
 * - Presence of the word in the dictionary.
 */
public class CheckIsInDictionaryReturn {
    public String acceptedWord;
    public boolean isInDictionary;

    public CheckIsInDictionaryReturn(String acceptedWord, boolean checkIsInDictionary) {
        this.acceptedWord = acceptedWord;
        this.isInDictionary = checkIsInDictionary;
    }
}

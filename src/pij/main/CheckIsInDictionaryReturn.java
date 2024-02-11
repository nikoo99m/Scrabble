package pij.main;

public class CheckIsInDictionaryReturn {
    String acceptedWord;
    boolean isInDictionary;

    public CheckIsInDictionaryReturn(String acceptedWord, boolean checkIsInDictionary) {
        this.acceptedWord = acceptedWord;
        this.isInDictionary = checkIsInDictionary;
    }
}

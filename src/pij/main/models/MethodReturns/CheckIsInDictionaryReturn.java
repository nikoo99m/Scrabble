package pij.main.models.MethodReturns;

public class CheckIsInDictionaryReturn {
    public String acceptedWord;
    public boolean isInDictionary;

    public CheckIsInDictionaryReturn(String acceptedWord, boolean checkIsInDictionary) {
        this.acceptedWord = acceptedWord;
        this.isInDictionary = checkIsInDictionary;
    }
}
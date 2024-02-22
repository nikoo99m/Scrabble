package pij.main.services.validators;

import pij.main.models.Dictionary;
import pij.main.models.MethodReturns.WordChoice;
import pij.main.models.Result;
import pij.main.models.interfaces.Validator;
import pij.main.services.Board;
import pij.main.utils.GameHelper;

public class IsInDictionaryValidator implements Validator {


    private Dictionary dictionary;
    private Result result;
    private Board board;
    private boolean isHuman;

    public IsInDictionaryValidator(Dictionary dictionary, Result result, Board board, boolean isHuman) {
        this.dictionary = dictionary;
        this.result = result;
        this.board = board;
        this.isHuman = isHuman;
    }

    /**
     * Checks if the chosen word is in the dictionary.
     *
     * @return true if the chosen word is in the dictionary, false otherwise.
     */
    @Override
    public boolean validate() {
        WordChoice acceptedWord = GameHelper.getAcceptedWord(result, board);
       if (dictionary.exists(acceptedWord.word))
           return true;
       else
       {
           if (isHuman)
               System.out.println("Chosen word is not in dictionary.");
           return false;
       }
    }
}

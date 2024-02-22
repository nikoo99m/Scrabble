package pij.main.services.validators;

import pij.main.models.TileRack;
import pij.main.models.interfaces.Validator;

public class TileSelectionValidator implements Validator {
    private String tileSelection;
    private TileRack playerRack;
    private boolean isHuman;
    private String moveAsString;

    public TileSelectionValidator(String tileSelection, TileRack playerRack, boolean isHuman, String moveAsString) {
        this.tileSelection = tileSelection;
        this.playerRack = playerRack;
        this.isHuman = isHuman;
        this.moveAsString = moveAsString;
    }

    /**
     * Checks if the tile selection for the move is valid, all the selected tiles are in tileRack or not.
     *
     * @return true if the tile selection is valid, false otherwise.
     */
    @Override
    public boolean validate()
    {
        for (int j = 0; j < tileSelection.length(); j++) {
            String charr = tileSelection.charAt(j) + "";
            for (int i = 0; i < playerRack.Rack.length; i++) {
                if (playerRack.Rack[i] == null)
                    continue;
                String rackChar = playerRack.Rack[i].character;
                if (charr.equals(rackChar)) {
                    break;
                } else if (i == playerRack.Rack.length - 1) {
                    if (isHuman)
                        System.out.println("With tiles " + playerRack + " you cannot play word " + moveAsString);
                    return false;
                }
            }
        }
        return true;
    }
}

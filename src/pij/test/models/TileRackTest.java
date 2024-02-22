package pij.test.models;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import pij.main.models.methodReturns.WildCardReturn;
import pij.main.models.TileRack;

import static org.junit.jupiter.api.Assertions.*;

public class TileRackTest {
    @Test
    void returnsTrueIfTileRackIsCompletelyEmpty() {
        //arrange
        String rackAsString = "{\"Rack\":[null,null,null,null,null,null,null]}";
        Gson gson = new Gson();
        // Parse the JSON string back into the object
        TileRack rack = gson.fromJson(rackAsString, TileRack.class);
        //action
        boolean isEmpty = rack.isEmpty();
        //assert
        assertTrue(isEmpty);
    }

    @Test
    void returnsFalseIfTileRackIsNotCompletelyEmpty() {
        //arrange
        String rackAsString = "{ \"Rack\": [ { \"character\": \"L\", \"value\": 1 }, { \"character\": \"W\", \"value\": 4 }, { \"character\": \"L\", \"value\": 1 }, null, { \"character\": \"A\", \"value\": 1 }, { \"character\": \"R\", \"value\": 1 }, { \"character\": \"G\", \"value\": 2 } ] }";
        Gson gson = new Gson();
        // Parse the JSON string back into the object
        TileRack rack = gson.fromJson(rackAsString, TileRack.class);
        //action
        boolean isEmpty = rack.isEmpty();
        //assert
        assertFalse(isEmpty);
    }

    @Test
    void checkIsTrueIfTileRackHasEmptyTiles()
    {
        //arrange
        String rackAsString = "{ \"Rack\": [ { \"character\": \"L\", \"value\": 1 }, { \"character\": \"W\", \"value\": 4 }, { \"character\": \"L\", \"value\": 1 }, null, { \"character\": \"A\", \"value\": 1 }, { \"character\": \"R\", \"value\": 1 }, { \"character\": \"G\", \"value\": 2 } ] }";
        Gson gson = new Gson();
        // Parse the JSON string back into the object
        TileRack rack = gson.fromJson(rackAsString, TileRack.class);
        //action
        boolean hasEmpty = rack.hasEmpty();
        //assert
        assertTrue(hasEmpty);
    }

    @Test
    void checkIsFalseIfTileRackHasEmptyTiles()
    {
        //arrange
        String rackAsString = "{ \"Rack\": [ { \"character\": \"L\", \"value\": 1 }, { \"character\": \"W\", \"value\": 4 }, { \"character\": \"L\", \"value\": 1 }, { \"character\": \"D\", \"value\": 3 }, { \"character\": \"A\", \"value\": 1 }, { \"character\": \"R\", \"value\": 1 }, { \"character\": \"G\", \"value\": 2 } ] }";
        Gson gson = new Gson();
        // Parse the JSON string back into the object
        TileRack rack = gson.fromJson(rackAsString, TileRack.class);
        //action
        boolean hasEmpty = rack.hasEmpty();
        //assert
        assertFalse(hasEmpty);
    }

    @Test
    void checkIsTrueIfTileRackHasTilesWithWildCards()
    {
        //arrange
        String rackAsString = "{ \"Rack\": [ { \"character\": \"L\", \"value\": 1 }, { \"character\": \"_\", \"value\": 4 }, { \"character\": \"L\", \"value\": 1 }, { \"character\": \"D\", \"value\": 3 }, { \"character\": \"A\", \"value\": 1 }, { \"character\": \"R\", \"value\": 1 }, { \"character\": \"G\", \"value\": 2 } ] }";
        Gson gson = new Gson();
        // Parse the JSON string back into the object
        TileRack rack = gson.fromJson(rackAsString, TileRack.class);
        //action
        WildCardReturn wildCardReturn = rack.wildCardExists();
        //assert
        assertTrue(wildCardReturn.isWildCard);
    }

    @Test
    void checkIsFalseIfTileRackHasNoTilesWithWildCards()
    {
        //arrange
        String rackAsString = "{ \"Rack\": [ { \"character\": \"L\", \"value\": 1 }, { \"character\": \"W\", \"value\": 4 }, { \"character\": \"L\", \"value\": 1 }, { \"character\": \"D\", \"value\": 3 }, { \"character\": \"A\", \"value\": 1 }, { \"character\": \"R\", \"value\": 1 }, { \"character\": \"G\", \"value\": 2 } ] }";
        Gson gson = new Gson();
        // Parse the JSON string back into the object
        TileRack rack = gson.fromJson(rackAsString, TileRack.class);
        //action
        WildCardReturn wildCardReturn = rack.wildCardExists();
        //assert
        assertFalse(wildCardReturn.isWildCard);
    }
}
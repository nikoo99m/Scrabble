package pij.test.models;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
}
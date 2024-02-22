package pij.test.models;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import pij.main.models.TileBag;
import pij.main.models.TileRack;

import static org.junit.jupiter.api.Assertions.*;

public class TileBagTest {

    @Test
    void checkIsTrueIfTileBagIsEmpty() {
        //arrange
        String rackAsString = "{ \"values\": [1, 3, 3, 2, 1, 4, 2, 4, 1, 9, 6, 1, 3, 1, 1, 3, 12, 1, 1, 1, 1, 4, 4, 9, 5, 11, 5], \"letters\": [\"A\", \"B\", \"C\", \"D\", \"E\", \"F\", \"G\", \"H\", \"I\", \"J\", \"K\", \"L\", \"M\", \"N\", \"O\", \"P\", \"Q\", \"R\", \"S\", \"T\", \"U\", \"V\", \"W\", \"X\", \"Y\", \"Z\", \"_\"], \"Bag\": []}";
        Gson gson = new Gson();
        // Parse the JSON string back into the object
        TileBag tileBag = gson.fromJson(rackAsString, TileBag.class);
        //action
        boolean isEmpty = tileBag.isEmpty();
        //assert
        assertTrue(isEmpty);
    }

    @Test
    void checkIsFalseIfTileBagIsEmpty() {
        //arrange
        String rackAsString = "{ \"values\": [1, 3, 3, 2, 1, 4, 2, 4, 1, 9, 6, 1, 3, 1, 1, 3, 12, 1, 1, 1, 1, 4, 4, 9, 5, 11, 5], \"letters\": [\"A\", \"B\", \"C\", \"D\", \"E\", \"F\", \"G\", \"H\", \"I\", \"J\", \"K\", \"L\", \"M\", \"N\", \"O\", \"P\", \"Q\", \"R\", \"S\", \"T\", \"U\", \"V\", \"W\", \"X\", \"Y\", \"Z\", \"_\"], \"Bag\": [ {\"character\": \"A\", \"value\": 1}, {\"character\": \"B\", \"value\": 3}, {\"character\": \"C\", \"value\": 3}, {\"character\": \"D\", \"value\": 2}, {\"character\": \"E\", \"value\": 1}, {\"character\": \"F\", \"value\": 4}, {\"character\": \"G\", \"value\": 2}, {\"character\": \"H\", \"value\": 4}, {\"character\": \"I\", \"value\": 1}, {\"character\": \"J\", \"value\": 9}, {\"character\": \"K\", \"value\": 6}, {\"character\": \"L\", \"value\": 1}, {\"character\": \"M\", \"value\": 3}, {\"character\": \"N\", \"value\": 1}, {\"character\": \"O\", \"value\": 1}, {\"character\": \"P\", \"value\": 3}, {\"character\": \"Q\", \"value\": 12}, {\"character\": \"R\", \"value\": 1}, {\"character\": \"S\", \"value\": 1}, {\"character\": \"T\", \"value\": 1}, {\"character\": \"U\", \"value\": 1}, {\"character\": \"V\", \"value\": 4}, {\"character\": \"W\", \"value\": 4}, {\"character\": \"X\", \"value\": 9}, {\"character\": \"Y\", \"value\": 5}, {\"character\": \"Z\", \"value\": 11}, {\"character\": \"_\", \"value\": 5} ] }";
        Gson gson = new Gson();
        // Parse the JSON string back into the object
        TileBag tileBag = gson.fromJson(rackAsString, TileBag.class);
        //action
        boolean isEmpty = tileBag.isEmpty();
        //assert
        assertFalse(isEmpty);
    }
}
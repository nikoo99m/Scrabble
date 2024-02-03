package pij.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        // Replace this with the actual path to your input file
        String filePath = "resources\\defaultBoard.txt";

        // Test the functionality
        test(filePath);
    }

    public static void test(String filePath) {
        DefaultBoard defaultBoard = new DefaultBoard();

        // Retrieve the filled Board object from testt method
        Board board = defaultBoard.testt(filePath);

        // Print the filled board matrix directly
        board.prettyPrint();
        TileBag bag = new TileBag();
        Player p =new Player(bag, board);
        p.fillTileRack();

        p.move();
        board.prettyPrint();


    }
}

        //test("resources\\defaultBoard.txt");
       // String currentPath = System.getProperty("user.dir");

        //Dictionary dictionary=new Dictionary();
        //dictionary.load();
        //boolean b=dictionary.exists("aa");
        //boolean c=dictionary.exists("nikoo");
        //Integer[] B={2,3,5};
        //ArrayList<Integer> list= new ArrayList<Integer>(List.of(1, 2, 3, 4, 5));

        //boolean i = list.contains(5);


        //DefaultBoard n=new DefaultBoard();
        //n.read();
        //Board board = new Board();
        //System.out.println("Initial Matrix:");
        //board.prettyPrint();
        //DefaultBoard customBoard = new DefaultBoard();
        //customBoard.prettyPrint();
        //TileBag tileBag = new TileBag();

        //System.out.println("Tiles map:");
        //tileBag.Tiles.forEach((key, value) -> System.out.println(key + "" + value));

        //TileBag tileBag = new TileBag();

        //System.out.println("Tiles in the TileBag:");
        //for (Tile tile : tileBag.Bag) {
            //System.out.println(tile.ToString());

        //}
        //Tile a = tileBag.randomPop();

        //TileRack rack = new TileRack();
        //Tile t = rack.Rack[5];
       // rack.add(a);
       // int i = 0;


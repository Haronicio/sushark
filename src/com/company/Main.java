package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("-- main");


        String file_path;
        if (args.length > 0) {
            file_path = args[0];
        } else {
            file_path = "data/exemple.txt";
        }


        // READ FILE
        String data = "";
        try {
            File myObj = new File(file_path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine() + " ";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // sépare les lignes à partir des offset
        String[] arr = data.split("[ ]*[0-9]{4}");

        StringBuilder builder = new StringBuilder();
        for(String s : arr) {
            builder.append(s);
        }
        String str = builder.toString().trim();

        // CREATE FRAME
        Frame f = new Frame(str);
    }
}

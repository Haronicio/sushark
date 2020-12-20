package com.company;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        System.out.println("-- main");


        String file_path;
        if (args.length > 0) {
            file_path = args[0];
        } else {
            file_path = "data/http.txt";
        }

        // TODO prendre en compte le cas de plusieurs frame dans un fichier
        RawFrame rawFrame = new RawFrame();


        // READ FILE
        String data = "";
        try {
            File myObj = new File(file_path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                analyse(rawFrame, data);
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


        for (String lines : rawFrame.getRawFrame()) {
            //for (String elem : lines) {
                System.out.print(lines);
            //}
        }

        // CREATE FRAME
        Frame f = new Frame(rawFrame);
        System.out.println(f);

        // TODO sauvegarder dans un fichier texte
    }


    private static void analyse(RawFrame rawFrame, String line) {
        System.out.println("line: " + line);
        //String[] arr = line.split("[ ]*");
        String[] split_line = line.split("[ ]+");

        ArrayList<String> lineList = new ArrayList<>();
        Collections.addAll(lineList, split_line);


        // Chaque nouvelle trame commence avec un offset de 0 et l’offset est séparé d’un espace des octets capturés situés à la suite
        String firstElem = lineList.remove(0);
        if (firstElem.matches("[0-9A-Fa-f]{2,}")) {
            System.out.println("C'EST UN OFFSET");

            ArrayList<String> stringList = filterLine(lineList);

            // on calcule la valeur de l'offset en hexa
            int firstElemInt = Integer.decode("0x" + firstElem);
            System.out.println("INT: " + firstElemInt);
            System.out.println("SIZE: " + rawFrame.size());
            if (firstElemInt == rawFrame.size()) {
                System.out.println("MEME TAILLE BON OFFSET");
            } else if (firstElemInt == 0 && rawFrame.size() != 0){
                // TODO excepetion => new RawFrame
                System.out.println("Create new frame");
            } else {
                // TODO exception
                System.out.println("Offset invalide à la ligne x");

                //TODO - Toute ligne incomplète doit être identifiée et soulever une erreur indiquant la position de la ligne en erreur.
            }

            rawFrame.add(stringList); // TODO
        }
    }

    private static ArrayList<String> filterLine(ArrayList<String> str) {
        ArrayList<String> stringList = new ArrayList<>();

        for (String elem : str) {
            if (elem.matches("[0-9A-Fa-f]{2}")) {
                stringList.add(elem);
            }
        }
        return stringList;
    }
}

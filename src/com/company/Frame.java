package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Frame {
    private String destination;
    private String source;
    private String type_str;
    private int type;
    private Datagram data; // du type d'une classe en fonction de la var type

    public Frame(String file) {
        //open file
        System.out.println(file);

        //TODO attention il faut ignorer les parties de texte

        // sépare les octets
        String[] arr = file.split(" ");//"[0-9]{4}");

        ArrayList<String> frame = new ArrayList<>();
        Collections.addAll(frame, arr);

        // Destination
        destination = "";
        for (int x = 0; x < 5; x++) {
            destination += frame.remove(0) + ":";
        }
        destination += frame.remove(0);


        // Source
        source = "";
        for (int x = 0; x < 5; x++) {
            source += frame.remove(0) + ":";
        }
        source += frame.remove(0);


        // Type TODO
        type_str = "0x";
        for (int x = 0; x < 2; x++) {
            type_str += frame.remove(0);
        }
        type = Integer.decode(type_str);


        System.out.println("\n--- Ethernet II:" +
                "\n\tDestination: " + destination +
                "\n\tSource: " + source +
                "\n\tType: X" + type + " (" + type_str + ")" // TODO
        );

        data = new Datagram(frame);
    }

    // getters pour tous les attributs
}

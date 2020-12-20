package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Frame {
    private String destination;
    private String source;
    private String type_str;
    private String type;
    private Datagram data; // du type d'une classe en fonction de la var type

    public Frame(RawFrame frame) {
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


        // Type
        type = "";
        for (int x = 0; x < 2; x++) {
            type += frame.remove(0);
        }
        switch (type) {
            case "0800":
                type_str = "IPv4 (0x0800)";
                break;
            case "0806":
                type_str = "ARP (0x0806)";
                break;
            case "8035":
                type_str = "RARP (0x8035)";
                break;
            case "8098":
                type_str = "Appletalk (0x8098)";
                break;
            case "86dd":
                type_str = "IPv6 (0x86dd)";
                break;
                //TODO erreur
        }
        /*
        0800 DoD Internet (Datagramme IP)
        0805 X.25 niveau 3
        0806 ARP
        8035 RARP
        8098 Appletalk

        0x0800 IP(v4), Internet Protocol version 4
        0x0806 ARP, Address Resolution Protocol
        0x8137 IPX, Internet Packet eXchange (Novell)
        0x86dd IPv6, Internet Protocol version 6
        */

        data = new Datagram(frame);
        //TODO gestion type de datagram : IPv4, ARP ...
    }

    // getters pour tous les attributs

    @Override
    public String toString() {
        return "\n\n--- Ethernet II:" +
                "\n\tDestination: " + destination +
                "\n\tSource: " + source +
                "\n\tType: " + type_str +
                data.toString();
    }
}

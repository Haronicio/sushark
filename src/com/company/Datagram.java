package com.company;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.ArrayList;

public class Datagram {
    private int version;
    private int ihl;
    private String tos;

    private String total_length_str;
    private int total_length;

    private String identification_str;
    private int identification;

    private String flags;
    private String fragment_offset;

    private String ttl_str;
    private int ttl;

    private String protocol;
    private String header_checksum;
    private String source;
    private String destination;

    public Datagram(ArrayList<String> datagram) {
        String version_ihl = datagram.remove(0);
        // Version
        version = Integer.decode("0x" + version_ihl.substring(0, 1));

        // IHL : valeur*4 (car mot de 32 bits)
        ihl = Integer.decode("0x" + version_ihl.substring(1, 2)) * 4;


        // TOS / DFS
        tos = "0x" + datagram.remove(0);

        // Total Length
        String total_length_str = "0x";
        for (int x = 0; x < 2; x++) {
            total_length_str += datagram.remove(0);
        }
        total_length = Integer.decode(total_length_str);

        // Identification
        identification_str = "0x";
        for (int x = 0; x < 2; x++) {
            identification_str += datagram.remove(0);
        }
        identification = Integer.decode(identification_str);

        //TODO
        flags = datagram.remove(0);
        // TODO flags & fragment_offset
        fragment_offset = "";
        for (int x = 0; x < 1; x++) {
            fragment_offset += datagram.remove(0);
        }

        // Time to live
        ttl_str = "0x" + datagram.remove(0);
        ttl = Integer.decode(ttl_str);

        //TODO
        protocol = datagram.remove(0);
        // decode + if else pour trouver le protocol

        // Header checksum
        header_checksum = "0x";
        for (int x = 0; x < 2; x++) {
            header_checksum += datagram.remove(0);
        }

        // Source
        source = "";
        for (int x = 0; x < 4; x++) {
            source += Integer.decode("0x" + datagram.remove(0)).toString();
            if (x < 3) {
                source += ".";
            }
        }

        // Destination
        destination = "";
        for (int x = 0; x < 4; x++) {
            destination += Integer.decode("0x" + datagram.remove(0)).toString();
            if (x < 3) {
                destination += ".";
            }
        }


        // AFFICHAGE
        System.out.println("\n--- Internet Protocol:" +
                "\n\tVersion: " + version +
                "\n\tHeader Length: " + ihl +
                "\n\tDifferentiated Services Field: " + tos +
                "\n\tTotal Length: " + total_length +
                "\n\tIdentification: " + identification_str + " (" + identification + ")" +
                "\n\tFlags: X" +
                "\n\tFragment offset: X" +
                "\n\tTime to live: " + ttl +
                "\n\tProtocol: X" +
                "\n\tHeader checksum: " + header_checksum +
                "\n\tSource: " + source +
                "\n\tDestination: " + destination
        );


        // new ()

    }

    // getters pour tous les attributs
}

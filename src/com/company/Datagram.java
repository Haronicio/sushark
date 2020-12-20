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

    private String flags_fragment_offset;
    private String flags;
    private String flags_str;
    private String fragment_offset;

    private String ttl_str;
    private int ttl;

    private int protocol;
    private String protocol_str;
    private String header_checksum;
    private String source;
    private String destination;

    private Segment data;

    public Datagram(RawFrame datagram) {
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


        // Flags & Fragment offset
        flags_fragment_offset = "0x";
        for (int x = 0; x < 2; x++) {
            flags_fragment_offset += datagram.remove(0);
        }
        flags_fragment_offset = Integer.toBinaryString(Integer.decode(flags_fragment_offset));

        while (flags_fragment_offset.length() < 16) {
            flags_fragment_offset = "0" + flags_fragment_offset;
        }

        // Flags
        flags = flags_fragment_offset.substring(1, 3);
        flags_str = Integer.toHexString(Integer.parseInt(flags, 2));
        while (flags_str.length() < 2) {
            flags_str = "0" + flags_str;
        }
        flags_str = "0x" + flags_str;
        flags_str += "\n\t\t0... .... = Reserved bit: Not set";
        String set_str = "Not set";
        if (flags.charAt(0) == '1') {
            set_str = "Set";
        }
        flags_str += "\n\t\t." + flags.charAt(0) + ".. .... = Don't fragment: " + set_str;
        set_str = "Not set";
        if (flags.charAt(1) == '1') {
            set_str = "Set";
        }
        flags_str += "\n\t\t.." + flags.charAt(1) + ". .... = More fragment: " + set_str;

        // Fragment offset
        fragment_offset = Integer.toString(Integer.parseInt(flags_fragment_offset.substring(3), 2));


        // Time to live
        ttl_str = "0x" + datagram.remove(0);
        ttl = Integer.decode(ttl_str);


        // Protocol
        protocol = Integer.decode("0x" + datagram.remove(0));
        switch (protocol) {
            case 1:
                protocol_str = "ICMP (1)";
                break;
            case 2:
                protocol_str = "IGMP (2)";
                break;
            case 6:
                protocol_str = "TCP (6)";
                break;
            case 8:
                protocol_str = "EGP (8)";
                break;
            case 9:
                protocol_str = "IGP (9)";
                break;
            case 17:
                protocol_str = "UDP (17)";
                break;
            case 36:
                protocol_str = "XTP (36)";
                break;
            case 46:
                protocol_str = "RSVP (46)";
                break;
                //TODO erreur
        }


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

        data = new Segment(datagram);
    }

    // getters pour tous les attributs

    @Override
    public String toString() {
        return "\n\n--- Internet Protocol:" +
                "\n\tVersion: " + version +
                "\n\tHeader Length: " + ihl +
                "\n\tDifferentiated Services Field: " + tos +
                "\n\tTotal Length: " + total_length +
                "\n\tIdentification: " + identification_str + " (" + identification + ")" +
                "\n\tFlags: " + flags_str +
                "\n\tFragment offset: " + fragment_offset +
                "\n\tTime to live: " + ttl +
                "\n\tProtocol: " + protocol_str +
                "\n\tHeader checksum: " + header_checksum +
                "\n\tSource: " + source +
                "\n\tDestination: " + destination +
                data.toString();
    }
}

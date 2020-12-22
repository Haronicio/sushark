package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Frame {
    private String destination;
    private String source;
    private String type;
    private Datagram data;

    public Frame() {}

    public void init(RawFrame frame) throws BadFrameFormatException {
        try {
            // Destination
            destination = "";
            for (int x = 0; x < 5; x++) {
                destination += frame.remove(0) + ":";
            }
            destination += frame.remove(0);
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format de la Frame au niveau du champ 'Destination'");
        }

        try {
            // Source
            source = "";
            for (int x = 0; x < 5; x++) {
                source += frame.remove(0) + ":";
            }
            source += frame.remove(0);
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format de la Frame au niveau du champ 'Source'");
        }


        try {
            // Type
            String type_str = "";
            for (int x = 0; x < 2; x++) {
                type_str += frame.remove(0);
            }
            switch (type_str) {
                case "0800":
                    type = "IPv4 (0x0800)";
                    break;
                case "0806":
                    type = "ARP (0x0806)";
                    break;
                case "8035":
                    type = "RARP (0x8035)";
                    break;
                case "8098":
                    type = "Appletalk (0x8098)";
                    break;
                case "86dd":
                    type = "IPv6 (0x86dd)";
                    break;
                    //TODO erreur
            }
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format de la Frame au niveau du champ 'Type'");
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

        data = new Datagram();
        if (frame.size() > 0) {
            try {
                data.init(frame);
            } catch (BadFrameFormatException e) {
                e.printStackTrace();
            }
        } else {
            data = null;
        }
    }

    public String getDestination() {
        return this.destination;
    }

    public String getSource() {
        return this.source;
    }

    public String getType() {
        return this.type;
    }

    public Datagram getDatagram() {
        return this.data;
    }

    @Override
    public String toString() {
        String data_str = "";
        if (data != null) {
            data_str = data.toString();
        }
        return "--- Ethernet II:" +
                "\n\tDestination: " + destination +
                "\n\tSource: " + source +
                "\n\tType: " + type +
                data_str;
    }
}

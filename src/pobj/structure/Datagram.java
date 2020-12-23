package pobj.structure;

import pobj.exception.BadFrameFormatException;

public class Datagram {
    private String version;
    private String ihl;
    private String tos;
    private String total_length;
    private String identification;
    private String flags_str;
    private String fragment_offset;
    private String ttl;
    private String protocol_str;
    private String header_checksum;
    private String source;
    private String destination;

    private Segment data;

    public Datagram() {}

    public void init(RawFrame datagram) throws BadFrameFormatException {
        try {
            String version_ihl = datagram.remove(0);
            // Version
            version = Integer.decode("0x" + version_ihl.substring(0, 1)).toString();

            // IHL : valeur*4 (car mot de 32 bits)
            ihl = Integer.toString(Integer.decode("0x" + version_ihl.substring(1, 2)) * 4);
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Datagram au niveau du champ 'Version & IHL'");
        }

        try {
            // TOS / DFS
            tos = "0x" + datagram.remove(0);
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Datagram au niveau du champ 'DFS'");
        }


        try {
            // Total Length
            String total_length_str = "0x";
            for (int x = 0; x < 2; x++) {
                total_length_str += datagram.remove(0);
            }
            total_length = Integer.toString(Integer.decode(total_length_str));
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Datagram au niveau du champ 'Total Length'");
        }


        try {
            // Identification
            String identification_str = "0x";
            for (int x = 0; x < 2; x++) {
                identification_str += datagram.remove(0);
            }
            identification = identification_str + " (" + Integer.toString(Integer.decode(identification_str))+ ")";
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Datagram au niveau du champ 'Identification'");
        }


        try {
            // Flags & Fragment offset
            String flags_fragment_offset = "0x";
            for (int x = 0; x < 2; x++) {
                flags_fragment_offset += datagram.remove(0);
            }
            flags_fragment_offset = Integer.toBinaryString(Integer.decode(flags_fragment_offset));

            while (flags_fragment_offset.length() < 16) {
                flags_fragment_offset = "0" + flags_fragment_offset;
            }

            // Flags
            String flags = flags_fragment_offset.substring(1, 3);
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
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Datagram au niveau du champ 'Flags & Fragment offset'");
        }

        try {
            // Time to live
            String ttl_str = "0x" + datagram.remove(0);
            ttl = Integer.toString(Integer.decode(ttl_str));
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Datagram au niveau du champ 'Time to live'");
        }


        try {
            // Protocol
            int protocol = Integer.decode("0x" + datagram.remove(0));
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
                default:
                    protocol_str = "Not supported";
                    break;
            }
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Datagram au niveau du champ 'Protocol'");
        }


        try {
            // Header checksum
            header_checksum = "0x";
            for (int x = 0; x < 2; x++) {
                header_checksum += datagram.remove(0);
            }
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Datagram au niveau du champ 'Header checksum'");
        }


        try {
            // Source
            source = "";
            for (int x = 0; x < 4; x++) {
                source += Integer.decode("0x" + datagram.remove(0)).toString();
                if (x < 3) {
                    source += ".";
                }
            }
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Datagram au niveau du champ 'Source'");
        }

        try {
            // Destination
            destination = "";
            for (int x = 0; x < 4; x++) {
                destination += Integer.decode("0x" + datagram.remove(0)).toString();
                if (x < 3) {
                    destination += ".";
                }
            }
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Datagram au niveau du champ 'Destination'");
        }

        data = new Segment();
        if (datagram.size() > 0) {
            try {
                data.init(datagram);
            } catch (BadFrameFormatException e) {
                e.printStackTrace();
            }
        } else {
            data = null;
        }
    }

    public String getVersion() {
        return this.version;
    }

    public String getIhl() {
        return this.ihl;
    }

    public String getTos() {
        return this.tos;
    }

    public String getTotalLength() {
        return this.total_length;
    }

    public String getIdentification() {
        return this.identification;
    }

    public String getFlags() {
        return this.flags_str;
    }

    public String getFragmentOffset() {
        return this.fragment_offset;
    }

    public String getTtl() {
        return this.ttl;
    }

    public String getProtocol() {
        return this.protocol_str;
    }

    public String getHeaderChecksum() {
        return this.header_checksum;
    }

    public String getSource() {
        return this.source;
    }

    public String getDestination() {
        return this.destination;
    }

    public Segment getSegment() {
        return this.data;
    }

    @Override
    public String toString() {
        String data_str = "";
        if (data != null) {
            data_str = data.toString();
        }
        return "\n\n--- Internet Protocol:" +
                "\n\tVersion: " + version +
                "\n\tHeader Length: " + ihl +
                "\n\tDifferentiated Services Field: " + tos +
                "\n\tTotal Length: " + total_length +
                "\n\tIdentification: " + identification +
                "\n\tFlags: " + flags_str +
                "\n\tFragment offset: " + fragment_offset +
                "\n\tTime to live: " + ttl +
                "\n\tProtocol: " + protocol_str +
                "\n\tHeader checksum: " + header_checksum +
                "\n\tSource: " + source +
                "\n\tDestination: " + destination +
                data_str;
    }
}

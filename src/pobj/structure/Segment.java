package pobj.structure;

import pobj.exception.BadFrameFormatException;

public class Segment {
    private String source;
    private String destination;
    private String seq_number;
    private String ack_number;
    private String header_length_str;
    private String flags_str;
    private String window_size;
    private String checksum;
    private String urgent_pointer;
    private String options;
    private Message data;

    public Segment() {}

    public void init(RawFrame segment) throws BadFrameFormatException {
        try {
            // Source port
            String source_str = "0x";
            for (int x = 0; x < 2; x++) {
                source_str += segment.remove(0);
            }
            source = Integer.toString(Integer.decode(source_str));
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Segment au niveau du champ 'Source port'");
        }

        try {
            // Destination port
            String destination_str = "0x";
            for (int x = 0; x < 2; x++) {
                destination_str += segment.remove(0);
            }
            destination = Integer.toString(Integer.decode(destination_str));
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Segment au niveau du champ 'Destination port'");
        }

        try {
            // Sequence number
            String seq_number_str = "0x";
            for (int x = 0; x < 4; x++) {
                seq_number_str += segment.remove(0);
            }
            seq_number = Long.toString(Long.decode(seq_number_str));
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Segment au niveau du champ 'Sequence number'");
        }

        try {
            // Acknowledgment number
            String ack_number_str = "0x";
            for (int x = 0; x < 4; x++) {
                ack_number_str += segment.remove(0);
            }
            ack_number = Long.toString(Long.decode(ack_number_str));
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Segment au niveau du champ 'Acknowledgment number'");
        }


        int header_length;
        try {
            // Flags & Fragment offset
            String header_flags = "0x";
            for (int x = 0; x < 2; x++) {
                header_flags += segment.remove(0);
            }
            flags_str = header_flags;
            header_flags = Integer.toBinaryString(Integer.decode(header_flags));

            while (header_flags.length() < 16) {
                header_flags = "0" + header_flags;
            }

            // Header length
            header_length_str = header_flags.substring(0, 4);
            header_length = Integer.parseInt(header_length_str, 2);
            header_length_str += " .... = " + header_length * 4 + " bytes (" + header_length + ")";

            // Flags
            String flags = header_flags.substring(7);
            flags_str = Integer.toHexString(Integer.parseInt(flags, 2));
            while (flags_str.length() < 3) {
                flags_str = "0" + flags_str;
            }
            flags_str = "0x" + flags_str;
            flags_str += "\n\t\t000. .... .... = Reserved: Not set";

            char[] flags_char = flags.toCharArray();

            String nonce_txt = "Not set";
            char nonce = flags_char[0];
            if (nonce == '1') {
                nonce_txt = "Set";
            }
            flags_str += "\n\t\t..." + nonce + " .... .... = Nonce: " + nonce_txt;

            String cwr_txt = "Not set";
            char cwr = flags_char[1];
            if (cwr == '1') {
                cwr_txt = "Set";
            }
            flags_str += "\n\t\t.... " + cwr + "... .... = Congestion Window Reduced (CWR): " + cwr_txt;

            String ecn_txt = "Not set";
            char ecn = flags_char[2];
            if (ecn == '1') {
                ecn_txt = "Set";
            }
            flags_str += "\n\t\t.... ." + ecn + ".. .... = ECN-Echo: " + ecn_txt;

            String urgent_txt = "Not set";
            char urgent = flags_char[3];
            if (urgent == '1') {
                urgent_txt = "Set";
            }
            flags_str += "\n\t\t.... .." + urgent + ". .... = Urgent: " + urgent_txt;

            String ack_txt = "Not set";
            char ack = flags_char[4];
            if (ack == '1') {
                ack_txt = "Set";
            }
            flags_str += "\n\t\t.... ..." + ack + " .... = Acknowledgment: " + ack_txt;

            String push_txt = "Not set";
            char push = flags_char[5];
            if (push == '1') {
                push_txt = "Set";
            }
            flags_str += "\n\t\t.... .... " + push + "... = Push: " + push_txt;

            String reset_txt = "Not set";
            char reset = flags_char[6];
            if (reset == '1') {
                reset_txt = "Set";
            }
            flags_str += "\n\t\t.... .... ." + reset + ".. = Reset: " + reset_txt;

            String syn_txt = "Not set";
            char syn = flags_char[7];
            if (syn == '1') {
                syn_txt = "Set";
            }
            flags_str += "\n\t\t.... .... .." + syn + ". = Syn: " + syn_txt;

            String fin_txt = "Not set";
            char fin = flags_char[8];
            if (fin == '1') {
                fin_txt = "Set";
            }
            flags_str += "\n\t\t.... .... ..." + fin + " = Fin: " + fin_txt;
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Segment au niveau des champs 'Flags & Fragment offset'");
        }

        try {
            // Window size value
            String window_size_str = "0x";
            for (int x = 0; x < 2; x++) {
                window_size_str += segment.remove(0);
            }
            window_size = Integer.toString(Integer.decode(window_size_str));
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Segment au niveau du champ 'Window size value'");
        }

        try {
            // Checksum
            checksum = "0x";
            for (int x = 0; x < 2; x++) {
                checksum += segment.remove(0);
            }
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Segment au niveau du champ 'Checksum'");
        }

        try {
            // Urgent pointer
            String urgent_pointer_str = "0x";
            for (int x = 0; x < 2; x++) {
                urgent_pointer_str += segment.remove(0);
            }
            urgent_pointer = Integer.toString(Integer.decode(urgent_pointer_str));
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Segment au niveau du champ 'Urgent pointer'");
        }

        try {
            // Options
            options = "";
            int options_kind;
            int options_length;
            int options_size = (header_length -5) * 4;
            for (int x = 0; x < options_size; x++) {
                // Kind
                options_kind = Integer.decode("0x" + segment.remove(0));

                String options_data = "";
                if (options_kind > 1) {
                    // Length
                    options_length = Integer.decode("0x" + segment.remove(0));
                    x++;

                    for (int y = 0; y < options_length - 2; y++) {
                        options_data += segment.remove(0);
                        x++;
                    }
                }

                switch (options_kind) {
                    case 0:
                        options += "\n\t\t> TCP Option (0) EOL End of Options List";
                        break;
                    case 1:
                        options += "\n\t\t> TCP Option (1) No-Operation";
                        break;
                    case 2:
                        options += "\n\t\t> TCP Option (2) Maximum Segment Size";
                        options += "\n\t\t\tMSS: 0x" + options_data + " (" + Integer.decode("0x" + options_data) + ")";
                        break;
                    case 3:
                        options += "\n\t\t> TCP Option (3) WSOPT - Window Scale";
                        options += "\n\t\t\tD??calage: 0x" + options_data + " (" + Integer.decode("0x" + options_data) + ")";
                        break;
                    case 4:
                        options += "\n\t\t> TCP Option (4) SACK Permitted";
                        break;
                    case 5:
                        options += "\n\t\t> TCP Option (5) Selective ACK";
                        break;
                    case 6:
                        options += "\n\t\t> TCP Option (6) Echo";
                        break;
                    case 7:
                        options += "\n\t\t> TCP Option (7) Echo Reply";
                        break;
                    case 8:
                        options += "\n\t\t> TCP Option (8) Timestamps";
                        options += "\n\t\t\tTimestamp value: " + Integer.decode("0x" + options_data.substring(0,8));
                        options += "\n\t\t\tTimestamp echo reply: " + Integer.decode("0x" + options_data.substring(8, 16));
                        break;
                    case 9:
                        options += "\n\t\t> TCP Option (9) Partial Order Connection Permitted";
                        break;
                    case 10:
                        options += "\n\t\t> TCP Option (10) Partial Order Service Profile";
                        break;
                    case 11:
                        options += "\n\t\t> TCP Option (11) CC";
                        break;
                    case 12:
                        options += "\n\t\t> TCP Option (12) CC.NEW";
                        break;
                    case 13:
                        options += "\n\t\t> TCP Option (13) CC.ECHO";
                        break;
                    case 14:
                        options += "\n\t\t> TCP Option (14) TCP Alternate Checksum Request";
                        break;
                    case 15:
                        options += "\n\t\t> TCP Option (15) Partial Order Service Profile";
                        break;
                    default:
                        options += "\n\t\t> Not supported";
                        break;
                }
            }
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Segment au niveau du champ 'Options'");
        }


        data = new Message();
        if (segment.size() > 0) {
            try {
                data.init(segment);
            } catch (BadFrameFormatException e) {
                e.printStackTrace();
            }
        } else {
            data = null;
        }
    }

    public String getSource() {
        return this.source;
    }

    public String getDestination() {
        return this.destination;
    }

    public String getSeqNumber() {
        return this.seq_number;
    }

    public String getAckNumber() {
        return this.ack_number;
    }

    public String getHeaderLength() {
        return this.header_length_str;
    }

    public String getFlags() {
        return this.flags_str;
    }

    public String getWindowSize() {
        return this.window_size;
    }

    public String getChecksum() {
        return this.checksum;
    }

    public String getUrgentPointer() {
        return this.urgent_pointer;
    }

    public String getOptions() {
        return this.options;
    }

    public Message getMessage() {
        return this.data;
    }

    @Override
    public String toString() {
        String data_str = "";
        if (data != null) {
            data_str = data.toString();
        }
        return "\n\n--- Transmission Control Protocol:" +
                "\n\tSource Port: " + source +
                "\n\tDestination Port: " + destination +
                "\n\tSequence number: " + seq_number +
                "\n\tAcknowledgment number: " + ack_number +
                "\n\tHeader Length: \n\t\t" + header_length_str +
                "\n\tFlags: " + flags_str +
                "\n\tWindow size value: " + window_size +
                "\n\tChecksum: " + checksum +
                "\n\tUrgent Pointer: " + urgent_pointer +
                "\n\tOptions:" + options +
                data_str;
    }
}

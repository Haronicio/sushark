package com.company;

public class Segment {
    private int source;
    private String source_str;

    private int destination;
    private String destination_str;

    private int seq_number;
    private String seq_number_str;

    private int ack_number;
    private String ack_number_str;

    private String header_flags;
    private int header_length;
    private String header_length_str;
    private String flags;
    private String flags_str;
    private char[] flags_char;

    private int window_size;
    private String window_size_str;

    private String checksum;

    private int urgent_pointer;
    private String urgent_pointer_str;

    private String options;

    private Message data;


    public Segment(RawFrame segment) {

        // Source port
        source_str = "0x";
        for (int x = 0; x < 2; x++) {
            source_str += segment.remove(0);
        }
        source = Integer.decode(source_str);


        // Destination port
        destination_str = "0x";
        for (int x = 0; x < 2; x++) {
            destination_str += segment.remove(0);
        }
        destination = Integer.decode(destination_str);


        // Sequence number
        seq_number_str = "0x";
        for (int x = 0; x < 4; x++) {
            seq_number_str += segment.remove(0);
        }
        System.out.println("\n\n----- " + seq_number_str);
        seq_number = Integer.decode(seq_number_str);


        // Acknowledgment number
        ack_number_str = "0x";
        for (int x = 0; x < 4; x++) {
            ack_number_str += segment.remove(0);
        }
        ack_number = Integer.decode(ack_number_str);


        // Flags & Fragment offset
        header_flags = "0x";
        for (int x = 0; x < 2; x++) {
            header_flags += segment.remove(0);
        }
        flags_str = header_flags; // TODO
        header_flags = Integer.toBinaryString(Integer.decode(header_flags));

        while (header_flags.length() < 16) {
            header_flags = "0" + header_flags;
        }

        // Header length
        header_length_str = header_flags.substring(0, 4);
        header_length = Integer.parseInt(header_length_str, 2);
        header_length_str += " .... = " + header_length*4 + " bytes (" + header_length + ")";

        // Flags
        flags = header_flags.substring(7);
        flags_str = Integer.toHexString(Integer.parseInt(flags, 2));
        while (flags_str.length() < 3) {
            flags_str = "0" + flags_str;
        }
        flags_str = "0x" + flags_str;
        flags_str += "\n\t\t000. .... .... = Reserved: Not set";

        flags_char = flags.toCharArray();

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


        // Window size value
        window_size_str = "0x";
        for (int x = 0; x < 2; x++) {
            window_size_str += segment.remove(0);
        }
        window_size = Integer.decode(window_size_str);


        // Checksum
        checksum = "0x";
        for (int x = 0; x < 2; x++) {
            checksum += segment.remove(0);
        }


        // Urgent pointer
        urgent_pointer_str = "0x";
        for (int x = 0; x < 2; x++) {
            urgent_pointer_str += segment.remove(0);
        }
        urgent_pointer = Integer.decode(urgent_pointer_str);


        // TODO options

        // TODO payload => http
        data = new Message(segment);

    }

    @Override
    public String toString() {
        return "\n\n--- Transmission Control Protocol:" +
                "\n\tSource Port: " + source +
                "\n\tDestination Port: " + destination +
                "\n\tSequence number: " + seq_number +
                "\n\tAcknowledgment number: " + ack_number +
                "\n\tHeader Length: \n\t\t" + header_length_str +
                "\n\tFlags: " + flags_str + // TODO
                "\n\tWindow size value: " + window_size +
                "\n\tChecksum: " + checksum +
                "\n\tUrgent Pointer: " + urgent_pointer +
                data.toString();
    }
}

package com.company;

public class Message {
    private String payload;

    public Message(RawFrame message) {
        String temp;
        char temp_char;

        StringBuilder builder = new StringBuilder();
        while (message.size() > 0) {
            temp_char = (char) Integer.parseInt(message.remove(0), 16);
            temp = ((Character) temp_char).toString();

            if (temp.equals("\n")) {
                temp += "\t";
            }
            builder.append(temp);
        }
        this.payload = builder.toString();
    }


    @Override
    public String toString() {
        return "\n\n--- Hypertext Transfer Protocol:" + this.payload;
    }
}

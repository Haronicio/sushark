package pobj.structure;

import pobj.exception.BadFrameFormatException;

public class Message {
    private String payload;

    public Message() {}

    public void init(RawFrame message) throws BadFrameFormatException {
        String temp;
        char temp_char;

        StringBuilder builder = new StringBuilder();
        try {
            while (message.size() > 0) {
                temp_char = (char) Integer.parseInt(message.remove(0), 16);
                temp = ((Character) temp_char).toString();

                if (temp.equals("\n")) {
                    temp += "\t";
                }
                builder.append(temp);
            }
        } catch (Exception e) {
            throw new BadFrameFormatException("Mauvais format du Message HTTP");
        }
        builder.insert(0, "\n\t");
        this.payload = builder.toString();
    }

    public String getPayload() {
        return this.payload;
    }

    @Override
    public String toString() {
        return "\n\n--- Hypertext Transfer Protocol:" + this.payload;
    }
}

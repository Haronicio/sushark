package pobj.structure;

import java.util.ArrayList;

public class RawFrame {
    private ArrayList<String> rawFrame;

    public RawFrame() {
        this.rawFrame = new ArrayList<>();
    }


    public void add(ArrayList<String> line) {
        this.rawFrame.addAll(line);
    }

    public ArrayList<String> getRawFrame() {
        return this.rawFrame;
    }

    public int size() {
        return this.rawFrame.size();
    }

    public String remove(int i) throws IndexOutOfBoundsException {
        return this.rawFrame.remove(i);
    }
}

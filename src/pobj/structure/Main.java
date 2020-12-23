package pobj.structure;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        Analyzer obj = new Analyzer(args);
        obj.display();

        // Sauvegarde dans un fichier
        try {
            obj.saveAsFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
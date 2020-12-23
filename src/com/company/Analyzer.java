package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Analyzer {
    private ArrayList<RawFrame> rawFrameList;
    private RawFrame current_rawFrame;
    private ArrayList<Frame> frameList;
    private int cpt_frame;


    public Analyzer(String[] args) {
        cpt_frame = 1;
        int cpt_line = 0;
        this.rawFrameList = new ArrayList<>();
        this.frameList = new ArrayList<>();

        String file_path = "data/multi2.txt";
        String output_path = "out.txt";
        if (args.length > 0) {
            if (args.length == 1) {
                file_path = args[0];
            } else {
                file_path = args[0];
                output_path = args[1];
            }
        }

        this.current_rawFrame = new RawFrame();
        this.rawFrameList.add(this.current_rawFrame);

        // READ FILE
        String data = "";
        try {
            File myObj = new File(file_path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                cpt_line++;

                analyse(data, cpt_line);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BadOffsetException e) {
            e.printStackTrace();
        }


        if (this.current_rawFrame.size() < 14) {
            try {
                throw new BadFrameFormatException("La trame contient moins de 14 Octets (taille minimale de l'entête)");
            } catch (BadFrameFormatException e) {
                e.printStackTrace();
                return;
            }
        }


        // CREATE FRAME
        for (RawFrame rawFrame : this.rawFrameList) {
            Frame f = new Frame();
            try {
                f.init(rawFrame);
            } catch (BadFrameFormatException e) {
                e.printStackTrace();
            }
            this.frameList.add(f);
        }


        // Sauvegarde dans un fichier
        try {
            saveAsFile(output_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void analyse(String line, int cpt_line) throws BadOffsetException {
        String[] split_line = line.split("[ ]+");

        ArrayList<String> lineList = new ArrayList<>();
        Collections.addAll(lineList, split_line);


        // Chaque nouvelle trame commence avec un offset de 0 et l’offset est séparé d’un espace des octets capturés situés à la suite
        String firstElem = lineList.remove(0);
        if (firstElem.matches("[0-9A-Fa-f]{2,}")) {
            ArrayList<String> stringList = filterLine(lineList);

            // on calcule la valeur de l'offset en hexa
            int firstElemInt = Integer.decode("0x" + firstElem);
            if (firstElemInt == this.current_rawFrame.size()) {
                // System.out.println("MEME TAILLE BON OFFSET");
            } else if (firstElemInt == 0 && this.current_rawFrame.size() != 0) {
                this.current_rawFrame = new RawFrame();
                this.rawFrameList.add(this.current_rawFrame);
                this.cpt_frame++;
            } else if (firstElemInt < this.current_rawFrame.size()) {
                while (firstElemInt < this.current_rawFrame.size()) {
                    this.current_rawFrame.remove(this.current_rawFrame.size() - 1);
                }
            } else {
                throw new BadOffsetException("Offset invalide (" + firstElem + ") à la ligne " + cpt_line + " de la Trame " + this.cpt_frame);
            }

            this.current_rawFrame.add(stringList);
        }
    }

    private ArrayList<String> filterLine(ArrayList<String> str) {
        ArrayList<String> stringList = new ArrayList<>();

        for (String elem : str) {
            if (elem.matches("[0-9A-Fa-f]{2}")) {
                stringList.add(elem);
            }
        }
        return stringList;
    }


    private void saveAsFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        int print_cpt = 1;
        for (Frame frame : this.frameList) {
            printWriter.println("No. " + print_cpt++);
            printWriter.println(frame);
            printWriter.println("\n");
        }
        printWriter.close();
        fileWriter.close();
    }

    public ArrayList<Frame> getFrameList() {
        return this.frameList;
    }
}

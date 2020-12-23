package com.company;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Analyzer obj = new Analyzer(args);

        ArrayList<Frame> frameList = obj.getFrameList();

        // AFFICHAGE
        int print_cpt = 1;
        for (Frame frame : frameList) {
            System.out.println("\n\nNo. " + print_cpt++);
            System.out.println(frame);
        }
    }
}
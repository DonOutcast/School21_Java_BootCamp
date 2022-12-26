package edu.school21.printer.app;


import edu.school21.printer.logic.Image;

import java.io.IOException;
import java.util.Arrays;

public class Program {
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Wrong arguments");
            System.exit(-1);
        }
        if(args[0].length() != 1 || args[1].length() != 1) {
            System.out.println("Wrong arguments");
            System.exit(-1);
        }

        try {
            char white = args[0].charAt(0);
            char black = args[1].charAt(0);
            Image image = new Image(white, black, "/resources/it.bmp");
            image.print();
        } catch (IOException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}


package edu.school21.printer.app;

import edu.school21.printer.logic.Args;
import edu.school21.printer.logic.Image;
import com.beust.jcommander.JCommander;

public class Program {
    public static void main(String[] args) {

        try {
            Args jArgs = new Args();
            JCommander jCommander = new JCommander(jArgs);
            jCommander.parse(args);
            Image image = new Image(jArgs, "target/resources/it.bmp");
            image.print();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
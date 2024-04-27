package com.boisbarganhados.ftc;

import java.io.File;
import java.util.Scanner;

import com.boisbarganhados.ftc.jflap.JflapParser;
import com.boisbarganhados.ftc.jflap.XMLController;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            while (true) {
                menu();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void menu() throws Exception {
        System.out.println("DFA Minimization");
        System.out.println("Enter the path (file name if file is located at program root folder) \n " +
                "to the JFLAP file: (.jff files) ");
        System.out.println("Enter 'exit' to close the program");
        String response = scanner.nextLine();
        if (response == null || response.isEmpty()) {
            test();
        }
        if (response.equals("exit")) {
            System.exit(0);
        }
    }

    public static void test() throws Exception {
        System.out.println("Testing...");
        File fileXml = new File("afd.jff");
        var at = XMLController.reader(fileXml);
        System.out.println("JFLAP Automaton:");
        System.out.println(at);
        var dfa = JflapParser.parse(at);
        System.out.println("DFA Internal structure:");
        System.out.println(dfa);
        System.out.println("Testing writer...");
        XMLController.writer(JflapParser.parse(dfa), new File("test.jff"));
        System.out.println("Test finished");
        runJFLAP("test.jff");
    }

    public static void runJFLAP(String xmlFilePath) {
        // run .jar at root folder
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "bin/JFLAP/JFLAP7.1.jar", xmlFilePath);
            pb.directory(new File("."));
            pb.start();
            // run async
            System.out.println("Running JFLAP with results");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.boisbarganhados.ftc;

import java.io.File;
import java.util.Scanner;

import com.boisbarganhados.ftc.jflap.JFlapParser;
import com.boisbarganhados.ftc.jflap.XMLController;
import com.boisbarganhados.ftc.minimization.RootDFAMinimizer;

public class Main {

    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            while (true) {
                menu();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Main menu of the program
     * 
     * @throws Exception
     */
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

    /**
     * Test method
     * 
     * @throws Exception
     */
    public static void test() throws Exception {
        System.out.println("Testing...");
        var at = XMLController.reader("full.jff");
        System.out.println(at);
        var dfa = JFlapParser.parse(at);
        XMLController.writer(JFlapParser.parse(dfa), "test.jff");
        System.out.println("Testing minimization...");
        var minimizedDFA = RootDFAMinimizer.minimize(dfa);
        System.out.println("Minimized DFA:");
        System.out.println(minimizedDFA);
        System.out.println("Writing minimized DFA to JFLAP file...");
        XMLController.writer(JFlapParser.parse(minimizedDFA), "minimized.jff");
        runJFLAP("minimized.jff");
        System.out.println("Test finished");
    }

    /**
     * Run JFLAP with the results
     * 
     * @param xmlFilePath Path to the XML file
     */
    public static void runJFLAP(String xmlFilePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "bin/JFLAP/JFLAP7.1.jar", xmlFilePath);
            pb.directory(new File("."));
            pb.start();
            System.out.println("Running JFLAP with results");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
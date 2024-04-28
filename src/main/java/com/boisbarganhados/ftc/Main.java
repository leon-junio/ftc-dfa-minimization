package com.boisbarganhados.ftc;

import java.io.File;
import java.util.Scanner;

import com.boisbarganhados.ftc.dfa.DFA;
import com.boisbarganhados.ftc.jflap.JFlapParser;
import com.boisbarganhados.ftc.jflap.XMLController;
import com.boisbarganhados.ftc.minimization.RootDFAMinimizer;

/**
 * DFA Minimization - FTC Assignment/PUC Minas - 2024/1
 * 
 * @author Leon Junio Martins Ferreira [https://github.com/leon-junio]
 * @author Edmar Melandes Junior [https://github.com/Lexizz7]
 */
public class Main {

    private final static Scanner scanner = new Scanner(System.in);
    private final static String JFLAP_PATH = "bin/JFLAP/JFLAP.jar";
    private final static String TEST_AFD_DFA = "test/afd.jff";
    private final static String TEST_MINIMIZED_DFA = "test/minimized.jff";
    private final static String TEST_RESULT_DFA = "test/result.jff";

    public static void main(String[] args) {
        try {
            System.out.println("DFA Minimization - FTC Assignment/PUC Minas - 2024/1");
            while (true) {
                menu();
            }
        } catch (Exception e) {
            System.err.println("Fatal main error:");
            e.printStackTrace();
        }
    }

    /**
     * Main menu of the program
     * 
     * @throws Exception
     */
    public static void menu() throws Exception {
        System.out.println("Enter the path (or file name if file is located at program root folder) \n " +
                "to the JFLAP file: (.jff files) ");
        System.out.println("Enter 'gen' to generate a dfa to test\nenter 'exit' to close the program");
        String response = scanner.nextLine();
        if (response == null || response.isEmpty()) {
            test();
        } else if (response.equals("gen")) {
           generate();
        }
        else if (response.equals("exit")) {
                System.exit(0);
        } else {
            startMinimization(response);
        }
    }

    /**
     * Test method to run the program with the test files
     * 
     * @throws Exception
     */
    private static void test() {
        try {
            System.out.println("Testing...");
            var at = XMLController.reader(TEST_AFD_DFA);
            System.out.println(at);
            var dfa = JFlapParser.parse(at);
            XMLController.writer(JFlapParser.parse(dfa), TEST_RESULT_DFA);
            System.out.println("Testing minimization...");
            var minimizedDFA = RootDFAMinimizer.minimizeDFA(dfa);
            System.out.println("Minimized DFA:");
            System.out.println(minimizedDFA);
            System.out.println("Writing minimized DFA to JFLAP file...");
            XMLController.writer(JFlapParser.parse(minimizedDFA), TEST_MINIMIZED_DFA);
            runJFLAP(TEST_MINIMIZED_DFA);
            System.out.println("Test finished");
        } catch (Exception e) {
            System.err.println("Error while testing:");
            e.printStackTrace();
        }
    }

    /**
     * Start the minimization process with the given XML file
     * 
     * @param xmlFilePath Path to the XML file
     */
    private static void startMinimization(String xmlFilePath) {
        try {
            System.out.println("Starting minimization...");
            int option;
            do {
                option = 0;
                System.out.println("1- Root DFA minimization (N^2 complexity)");
                System.out.println("2- Optimized minimization (N log N complexity)");
                System.out.println("Choose the minimization method:");
                option = scanner.nextInt();
                scanner.nextLine();
            } while (option <= 0 || option > 2);
            System.out.println(xmlFilePath);
            var jflapDFA = XMLController.reader(xmlFilePath);
            var internalDfa = JFlapParser.parse(jflapDFA);
            // TODO: Implement optimized minimization
            var minimizedDFA = option == 1 ? RootDFAMinimizer.minimizeDFA(internalDfa) : internalDfa;
            var minimizedPath = xmlFilePath.replace(".jff", "_minimized.jff");
            XMLController.writer(JFlapParser.parse(minimizedDFA), minimizedPath);
            System.out.println("Minimization finished. Result saved to " + minimizedPath);
            runJFLAP(minimizedPath);
        } catch (Exception e) {
            System.err.println("Error while running minimization");
            e.printStackTrace();
        }
    }

    /**
     * Run JFLAP with the results
     * 
     * @param xmlFilePath Path to the XML file
     */
    private static void runJFLAP(String xmlFilePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", JFLAP_PATH, xmlFilePath);
            pb.directory(new File("."));
            pb.start();
            System.out.println("Running JFLAP with results");
        } catch (Exception e) {
            System.err.println("JFLAP Launcher error:");
            e.printStackTrace();
        }
    }

    /**
     * Generate a test DFA
     */
    private static void generate() {
        try {
            System.out.println("Generating test DFA...");
            System.out.println("Enter the number of states for the test DFA:");
            int nStates = scanner.nextInt();
            scanner.nextLine();
            var dfa = DFA.generateDoubleStateTest(nStates);
            var dfaPath = "test/generated_" + nStates + "_states.jff";
            XMLController.writer(JFlapParser.parse(dfa), dfaPath);
            System.out.println("Test DFA generated. Path: " + dfaPath);
            runJFLAP(dfaPath);
        } catch (Exception e) {
            System.err.println("Error while generating test DFA:");
            e.printStackTrace();
        }
    }
}
package com.boisbarganhados.ftc;

import java.io.File;
import java.util.Scanner;

import com.boisbarganhados.ftc.jflap.JflapParser;
import com.boisbarganhados.ftc.jflap.XMLReader;

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
        var at = XMLReader.reader(fileXml);
        System.out.println("JFLAP Automaton:");
        System.out.println(at);
        var dfa = JflapParser.parse(at);
        System.out.println("DFA Internal structure:");
        System.out.println(dfa);
    }
}
package com.boisbarganhados.ftc;

import java.io.File;

import com.boisbarganhados.ftc.jflap.JflapParser;
import com.boisbarganhados.ftc.jflap.XMLReader;

public class Main {

    public static void main(String[] args) {
        try {
            File fileXml = new File("afd.jff");
            var at = XMLReader.reader(fileXml);
            System.out.println("JFLAP Automaton:");
            System.out.println(at);
            var dfa = JflapParser.parse(at);
            System.out.println("DFA Internal structure:");
            System.out.println(dfa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
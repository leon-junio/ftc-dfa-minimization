package com.boisbarganhados.ftc;

import java.io.File;

import com.boisbarganhados.ftc.jflap.XMLReader;

public class Main {

    public static void main(String[] args) {
        try {
            XMLReader reader = new XMLReader();
            // read a file at resources path of maven project
            var fileXml = new File("C:\\Users\\junio\\Documentos\\PUC\\6-periodo\\ftc\\tp\\tp1\\dfa_minimization\\src\\main\\resources\\afd.jff");
            var at = reader.reader(fileXml);
            System.out.println(at);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.boisbarganhados.ftc.jflap;

import java.io.File;

import com.boisbarganhados.ftc.jflap.utils.Automaton;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class XMLReader {

    public Automaton reader(File xmlFile) throws JAXBException, Exception {
        // Create JAXB context and instantiate unmarshaller
        JAXBContext jaxbContext = JAXBContext.newInstance(Automaton.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        // Unmarshal XML to Java object
        Automaton automaton = (Automaton) jaxbUnmarshaller.unmarshal(xmlFile);

        // Access the object and do whatever you need
        return automaton;
    }

}

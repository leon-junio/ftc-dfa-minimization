package com.boisbarganhados.ftc.jflap;

import java.io.File;

import com.boisbarganhados.ftc.jflap.utils.Automaton;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class XMLReader {

    /**
     * Read a XML file and return a Automaton object
     * 
     * @param File xmlFile File type to be read
     * @return Automaton object with the data from the XML file
     * @throws JAXBException
     * @throws Exception
     */
    public static Automaton reader(File xmlFile) throws JAXBException, Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Automaton.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Automaton automaton = (Automaton) jaxbUnmarshaller.unmarshal(xmlFile);
        return automaton;
    }

}

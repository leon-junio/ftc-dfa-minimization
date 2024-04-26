package com.boisbarganhados.ftc.jflap.utils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "structure")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Automaton {
    private String type;
    private AutomatonType automaton;
}

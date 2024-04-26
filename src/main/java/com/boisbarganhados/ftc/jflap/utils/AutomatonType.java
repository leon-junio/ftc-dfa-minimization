package com.boisbarganhados.ftc.jflap.utils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class AutomatonType {
    private State [] state;
    private Transition [] transition;
}

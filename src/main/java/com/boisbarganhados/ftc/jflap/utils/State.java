package com.boisbarganhados.ftc.jflap.utils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class State {
    @XmlAttribute
    private int id;
    @XmlAttribute
    private String name;
    private double x;
    private double y;
    private boolean initial;
    private boolean _final;
    
}

package com.boisbarganhados.ftc.jflap.utils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
class Transition {
    private int from;
    private int to;
    private int read;
}

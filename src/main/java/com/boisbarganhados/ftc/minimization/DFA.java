package com.boisbarganhados.ftc.minimization;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DFA {
    private List<DFAState> states;

    public DFA(List<DFAState> states) {
        this.states = states;
    }

    public DFA() {
        this.states = new ArrayList<>();
    }
}
package com.boisbarganhados.ftc.minimization;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.boisbarganhados.ftc.jflap.utils.State;

import lombok.Data;

@Data
public class DFAState {
    private int id;
    private String name;
    private boolean initialState, finalState;
    private Map<String, HashSet<DFAState>> transitions;

    public DFAState(int i) {
        id = i;
        transitions = new HashMap<>();
    }

    public void put(String key, DFAState value) {
        HashSet<DFAState> transitionsSet;
        if (transitions.containsKey(key)) {
            transitionsSet = transitions.get(key);
        } else {
            transitionsSet = new HashSet<>();
        }
        transitionsSet.add(value);
        transitions.put(key, transitionsSet);
    }

    public State toState() {
        var state = new State();
        state.setId(this.getId());
        state.setName(this.getName());
        state.setStateInitial(this.isInitialState());
        state.setStateFinal(this.isFinalState());
        return state;
    }
}
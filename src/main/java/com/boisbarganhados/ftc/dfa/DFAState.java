package com.boisbarganhados.ftc.dfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.boisbarganhados.ftc.jflap.utils.State;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class DFAState {
    private int id;
    private List<Integer> ids;
    private String name;
    private boolean initialState, finalState;
    @EqualsAndHashCode.Exclude
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

    /**
     * Returns the state that the DFA transitions to for a given input symbol.
     * (This method needs to be implemented in the DFAState class)
     *
     * @param symbol The input symbol.
     * @return The state reached after transitioning on the given symbol, or null if
     *         no transition exists.
     */
    public DFAState getTransitionState(String symbol) {
        HashSet<DFAState> possibleStates = transitions.get(symbol);
        return possibleStates != null && !possibleStates.isEmpty() ? possibleStates.iterator().next() : null;
    }

    public State toState() {
        var state = new State();
        state.setId(this.getId());
        state.setName(this.getName());
        state.setStateInitial(this.isInitialState());
        state.setStateFinal(this.isFinalState());
        state.setX(this.getId() * 50);
        state.setY(this.getId() * 50);
        return state;
    }

    @Override
    public String toString() {
        return "DFAState{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", initialState=" + initialState +
                ", finalState=" + finalState +
                ", transitions=" + transitions.keySet() + " -- " + printTransitions() +
                '}';
    }

    private String printTransitions() {
        StringBuilder sb = new StringBuilder();
        transitions.forEach((key, value) -> {
            sb.append(key).append(" -> ");
            value.forEach(v -> sb.append(v.getId()).append(", "));
        });
        return sb.toString();
    }
}
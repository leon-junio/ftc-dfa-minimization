package com.boisbarganhados.ftc.minimization;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.boisbarganhados.ftc.dfa.DFA;
import com.boisbarganhados.ftc.dfa.DFAState;

public class RootDFAMinimizer {

    /**
     * Minimize a DFA using the Root algorithm (N^2 complexity)
     * 
     * @param dfa The DFA to be minimized
     * @return The minimized DFA
     */
    public static DFA minimize(DFA dfa) {
        long start = System.currentTimeMillis(), end = 0;
        var states = Set.of(dfa.getStates().toArray(new DFAState[0]));
        Set<Set<DFAState>> partition = new HashSet<>();
        Set<DFAState> finalStates = new HashSet<>();
        Set<DFAState> nonFinalStates = new HashSet<>();
        for (DFAState state : states) {
            if (state.isFinalState()) {
                finalStates.add(state);
            } else {
                nonFinalStates.add(state);
            }
        }
        partition.add(finalStates);
        partition.add(nonFinalStates);
        boolean changed = true;
        while (changed) {
            changed = false;
            Set<Set<DFAState>> newPartition = new HashSet<>();
            for (Set<DFAState> set : partition) {
                newPartition.addAll(partitionSet(set, partition));
            }
            partition = newPartition;
        }
        end = System.currentTimeMillis();
        System.out.println("Minimization time: " + (end - start) + "ms");
        return mergeIntoDFA(new ArrayList<>(partition), dfa);
    }

    /**
     * Partition a set of states based on the current partition
     * 
     * @param stateSet  The set of states to be partitioned
     * @param partition The current partition
     * @return The new partition of the set of states
     */
    private static Set<Set<DFAState>> partitionSet(Set<DFAState> stateSet, Set<Set<DFAState>> partition) {
        Set<Set<DFAState>> newPartition = new HashSet<>();
        for (DFAState state1 : stateSet) {
            Set<Set<DFAState>> currentPartition = new HashSet<>();
            for (DFAState state2 : stateSet) {
                if (state1 == state2) {
                    continue;
                }
                boolean distinguishable = false;
                for (String symbol : state1.getTransitions().keySet()) {
                    Set<DFAState> nextStates1 = state1.getTransitions().get(symbol);
                    Set<DFAState> nextStates2 = state2.getTransitions().get(symbol);
                    if (nextStates1 == null || !distinguishableStates(nextStates1, partition)) {
                        if (nextStates2 == null || !distinguishableStates(nextStates2, partition)) {
                            distinguishable = true;
                            break;
                        }
                    }
                }
                if (distinguishable) {
                    Set<DFAState> newStateSet;
                    if (currentPartition.isEmpty()) {
                        newStateSet = new HashSet<>();
                    } else {
                        newStateSet = currentPartition.iterator().next();
                    }
                    newStateSet.add(state2);
                    currentPartition.add(newStateSet);
                } else {
                    currentPartition.add(new HashSet<DFAState>() {
                        {
                            add(state1);
                            add(state2);
                        }
                    });
                }
            }
            newPartition.addAll(currentPartition);
        }
        return newPartition;
    }

    /**
     * Check if a set of states is distinguishable
     * 
     * @param states    The set of states to be checked
     * @param partition The current partition
     * @return True if the states are distinguishable, false otherwise
     */
    private static boolean distinguishableStates(Set<DFAState> states, Set<Set<DFAState>> partition) {
        for (Set<DFAState> set : partition) {
            boolean foundInSameSet = false;
            boolean foundInDifferentSet = false;
            for (DFAState state : states) {
                if (set.contains(state)) {
                    if (foundInDifferentSet) {
                        return true;
                    }
                    foundInSameSet = true;
                } else {
                    foundInDifferentSet = true;
                }
            }
            if (foundInSameSet && !foundInDifferentSet) {
                return false;
            }
        }
        return true;
    }

    /**
     * Merge the result states into a DFA
     * 
     * @param resultStates The result states
     * @param dfa          The original DFA
     * @return The minimized DFA
     */
    private static DFA mergeIntoDFA(List<Set<DFAState>> resultStates, DFA dfa) {
        var minimizedDFA = new DFA();
        resultStates.forEach(set -> {
            var newState = new DFAState(minimizedDFA.getStates().size() + 1);
            newState.setIds(new ArrayList<>());
            set.forEach(state -> {
                newState.setName((newState.getName() != null ? newState.getName() : "") + state.getName());
                if (state.isInitialState()) {
                    newState.setInitialState(true);
                }
                if (state.isFinalState()) {
                    newState.setFinalState(true);
                }
                newState.getIds().add(state.getId());
            });
            minimizedDFA.getStates().add(newState);
        });
        minimizedDFA.getStates().forEach(state -> {
            state.getIds().forEach(id -> {
                var originalState = dfa.getStates().stream().filter(s -> s.getId() == id).findFirst().get();
                originalState.getTransitions().entrySet().forEach(entry -> {
                    var key = entry.getKey();
                    var value = entry.getValue();
                    var newState = minimizedDFA.getStates().stream()
                            .filter(s -> s.getIds().contains(value.iterator().next().getId())).findFirst().get();
                    state.put(key, newState);
                });
            });
        });
        return minimizedDFA;
    }

}

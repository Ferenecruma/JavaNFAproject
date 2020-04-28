package com.company;
//Class that implements NFA
import com.sun.source.tree.WhileLoopTree;
import org.jetbrains.annotations.NotNull;

import java.io.ObjectInputStream;
import java.net.Inet4Address;
import java.util.*;
import java.util.stream.Collectors;

public class NFA {
    private int alphabet_size = 0;
    private int states_num = 0;//Кількість станів
    private int s0;//Початковий стан
    private Set<String> characters = new HashSet<String>();
    private Set<Integer> FinalStates = new HashSet<Integer>();//Множина кінцевих станів
    private Set<Integer> States = new HashSet<Integer>();
    private ArrayList<Transition> transitions = new ArrayList<Transition>();//Фенції переходів
    private HashMap<Integer, List<Integer>> StatesToCheckMap = new HashMap<Integer, List<Integer>>();

    public NFA(int s0) {
        this.alphabet_size = 0;
        this.states_num = 1;
        this.s0 = (new ArrayList<Integer>(){{add(s0);}}).hashCode();
        this.States.add(this.s0);
        this.addStatesToCheck(this.s0, new ArrayList<Integer>(){{add(s0);}});
    }

    //Конструктор класу,що зчитує автомат з файла
    public NFA(@NotNull Scanner x) {
        this.alphabet_size = x.nextInt();
        this.states_num = x.nextInt();
        this.s0 = x.nextInt();
        //adding final states to set of NF
        x.nextLine();
        String[] States = (x.nextLine()).split(" ");
        for (String s : States) {
            FinalStates.add(Integer.parseInt(s));
        }
        //adding transition functions as instance of Transition class to ArrayList
        while (x.hasNextLine()) {
            String[] temp = x.nextLine().split(" ");
            String[] FinalStates = Arrays.copyOfRange(temp, 2, temp.length);
            Integer FStates[] = new Integer[FinalStates.length];
            for (int i = 0; i < FStates.length; i++) {
                int Fstate = Integer.parseInt(FinalStates[i]);
                this.States.add(Fstate);
                FStates[i] = Fstate;
            }
            this.States.add(Integer.parseInt(temp[0]));
            this.characters.add(temp[1]);//adding characters to alphabet
            Transition tr = new Transition(Integer.parseInt(temp[0]), temp[1], FStates);
            transitions.add(tr);
        }
    }

    public Set<Integer> getStates() {
        return this.States;
    }

    public HashMap<Integer, List<Integer>> getStatesToCheckMap() {
        return this.StatesToCheckMap;
    }

    public ArrayList<Transition> getTransitions() {
        return this.transitions;
    }

    public Set<Integer> getFinalStates() {
        return this.FinalStates;
    }

    public void addState(Integer s) {
        this.States.add(s);
        this.states_num++;
    }

    public void addStatesToCheck(Integer state, List<Integer> toCheck) {
        this.StatesToCheckMap.put(state, toCheck);
    }

    public void addFinalStates(Integer i) {
        this.FinalStates.add(i);
    }

    public void addTransition(Transition tr) {
        this.transitions.add(tr);
    }

    public void Display() {
        System.out.println("Кількість симовлів алфавіту:" + this.alphabet_size);
        System.out.println("Кількість станів:" + this.states_num);
        System.out.println("Стани:" + this.States);
        System.out.println("Початковий стан:" + this.s0);
        System.out.println("Кінцеві стани:" + this.FinalStates);
        for (Transition tr : transitions) {
            tr.Display();
        }
        System.out.println("\n");
    }

    //NFA to DFA algorithm implementation
    public NFA NFAtoDFA() {
        NFA dfa = new NFA(this.s0);
        Set<Integer> SetOfKeysToCheck = new HashSet<Integer>(dfa.getStates());//States that hadn't not been checked yet
        while (!SetOfKeysToCheck.isEmpty()) {  //while we are adding new states
            Set<Integer> TempSetOfKeysToCheck = new HashSet<>(SetOfKeysToCheck);
            for (Integer key : TempSetOfKeysToCheck) {  // Finding new transitions for states that are already at dfa
                for (Integer state : dfa.getStatesToCheckMap().get(key)) {
                    HashMap<String, List<Integer>> charToStates = new HashMap<String, List<Integer>>(); //Mapping from symbols of language to States
                        for (Transition tr : this.getTransitions()) {
                            if (state.equals(tr.getFromState())) { // comparing Integers through equals() method
                                List<Integer> tempList = charToStates.get(tr.getSymbol());
                                if (tempList == null) {
                                    tempList = new ArrayList<Integer>();
                                    tempList.addAll(Arrays.asList(tr.getToState()));
                                    charToStates.put(tr.getSymbol(), tempList);
                                } else {
                                    for (Integer x : tr.getToState()){
                                        if (!tempList.contains(x))
                                            tempList.add(x);
                                    }
                                }
                            }
                        }
                    for (Map.Entry<String, List<Integer>> entry: charToStates.entrySet()){ // merging gotostates for current character and state
                        List<Integer> copy = List.copyOf(entry.getValue()).stream().sorted().collect(Collectors.toList());// sorting list for correct hashing
                        int hash = copy.hashCode();
                        Transition NewTransition = new Transition(dfa.getStatesToCheckMap().get(key).hashCode(),entry.getKey(),new Integer[]{hash});
                        if (!dfa.getTransitions().contains(NewTransition)){
                            if(!dfa.getStates().contains(hash)) {
                                dfa.addState(hash);
                                SetOfKeysToCheck.add(hash);
                            }
                            dfa.addStatesToCheck(hash,copy);
                            dfa.addTransition(NewTransition);
                            for (Integer f: this.getFinalStates()){ //checking if merged state contains final state of nfa
                                if (copy.contains(f)){
                                    dfa.addFinalStates(hash);
                                    break;
                                }
                            }
                        }
                    }

                }
                SetOfKeysToCheck.remove(key);
            }

        }
        return dfa;
    }

    public void ReenumerateDFA(){
        Integer counter = 0;
        Integer stateToRename = this.s0;
        HashSet<Integer> renamedStates = new HashSet<>();
        while (renamedStates.size() != this.getStates().size()){
            if (!renamedStates.contains(stateToRename)){
                Integer currState = stateToRename;
                for(Transition tr: this.getTransitions()){
                    if (tr.getFromState() == currState){
                        if(tr.getFromState() != tr.getToState()[0]){
                            stateToRename = tr.getToState()[0];
                        }
                        tr.setFromState(counter);
                    }
                    if (Integer.compare(tr.getToState()[0], currState) == 0){
                        tr.setToStates(new Integer[]{counter});
                    }
                }

                if (this.getFinalStates().contains(currState)){ //re enumerating final states
                    this.FinalStates.remove(currState);
                    this.FinalStates.add(counter);
                }

                counter += 1;
                renamedStates.add(currState);
            }
        }
    }
    public void DeleteStumped(){
        HashSet<Integer> notStumped = new HashSet<>(this.getFinalStates());
        boolean addingNew = true;
        while(addingNew) {
            addingNew = false;
            HashSet<Integer> tempSet = new HashSet<>(notStumped);
            for (Integer state : tempSet) {
                for (Transition tr : this.getTransitions()) {
                    if (Integer.compare(tr.getToState()[0],state) == 0 && !notStumped.contains(tr.getFromState())) {
                        notStumped.add(tr.getFromState());
                        addingNew = true;
                    }
                }
            }
        }
        System.out.println("Not stumped states:  "+notStumped);
    }
}

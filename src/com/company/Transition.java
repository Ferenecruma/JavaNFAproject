package com.company;

import java.util.Arrays;

//Class for transitional functions implementation
public class Transition {
    private int FromState;
    private Integer[] ToStates;
    private String symbol;
    public Transition(int From,String symbol,Integer[] To){
        this.FromState=From;
        this.symbol=symbol;
        this.ToStates=To;
    }

    public int getFromState() {
        return FromState;
    }

    public String getSymbol() {
        return symbol;
    }

    public Integer[] getToState() {
        return ToStates;
    }

    public void setFromState(int fromState) {
        FromState = fromState;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setToStates(Integer[] toStates) {
        ToStates = toStates;
    }

    public void Display(){
        System.out.println("("+this.FromState+","+this.symbol+","+Arrays.toString(ToStates)+")");
    }

    //Під час роботи алгоритму треба буде перевіряти чи маємо ми вже цей перехід
    // Overriding equals() to compare two Transition objects

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Transition or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Transition)) {
            return false;
        }

        // typecast o to Transition so that we can compare data members
        Transition tr = (Transition) o;

        // Compare the data members and return accordingly
        return this.getFromState() == tr.getFromState()
                && Arrays.equals(this.ToStates, tr.ToStates)
                    && this.symbol.equals(tr.symbol);
    }
}

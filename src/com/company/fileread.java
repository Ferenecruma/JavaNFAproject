package com.company;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class fileread {
    private Scanner x;
    private Scanner y;

    public void openFile(){
        try{
            x=new Scanner(new File("C:\\Users\\Feren\\IdeaProjects\\Laba2Windows\\src\\com\\company\\some.txt"));
            y=new Scanner(new File("C:\\Users\\Feren\\IdeaProjects\\Laba2Windows\\src\\com\\company\\some1.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void readFile (){
        NFA myNFA = new NFA(x);
        NFA myNFA1 = new NFA(y);
        NFA dfa = myNFA.NFAtoDFA();
        NFA dfa1 = myNFA1.NFAtoDFA();
        dfa.ReenumerateDFA();
        dfa1.ReenumerateDFA();

        if (dfa.equals(dfa1)){
            System.out.println("Недетерміновані скінченні автомати еквівалентні.");
        }
        else {
            System.out.println("Недетерміновані скінченні автомати не є еквівалентними.");
        }
    }
    public void closeFile(){
        x.close();
    }

}

package com.company;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class fileread {
    private Scanner x;

    public void openFile(){
        try{
            x=new Scanner(new File("C:\\Users\\Feren\\IdeaProjects\\Laba2Windows\\src\\com\\company\\some.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void readFile (){
        NFA myNFA = new NFA(x);
        NFA dfa = myNFA.NFAtoDFA();
        dfa.ReenumerateDFA();
        dfa.Display();
        dfa.DeleteStumped();
    }
    public void closeFile(){
        x.close();
    }

}

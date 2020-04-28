package com.company;

public class Main {

    public static void main(String[] args) {
        fileread c = new fileread();
        c.openFile();
        c.readFile();
        c.closeFile();
    }
}

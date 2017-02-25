package model;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * Created by jsondoo on 2017-02-17.
 */
public class BFInterpreter {
    private final int memSize = 50000;
    private byte[] data;

    private Scanner scanner = new Scanner(System.in);
    private String input = null;
    private String output = null;

    private Deque<Integer> bracketStack = new ArrayDeque<>(); // keeps track of loops by stacking indexes of opening brackets

    public BFInterpreter(){
        data = new byte[memSize];
    }

    // TODO exception handling for invalid inputs

    public void interpret(){
        char[] command = this.input.toCharArray();
        StringBuilder sb = new StringBuilder(); // for building the output string

        int dp = 0; // data pointer
        for(int i = 0; i < command.length; i++){
            char c = command[i]; // current instruction
            switch(c){
                case '>':
                    dp++;
                    break;
                case '<':
                    dp--;
                    break;
                case '+':
                    data[dp]++;
                    break;
                case '-':
                    data[dp]--;
                    break;
                case '.':
                    sb.append((char)data[dp]);
                    break;
                case ',':
                    data[dp] = scanner.nextByte();
                    break;
                case '[':
                    if(data[dp]==0) // current cell is zero, move instruction pointer to matching ']'
                        i = findMatchingClosingBracket(i);
                    else // move to next instruction
                        bracketStack.push(i);
                    break;
                case ']':
                    if(data[dp]==0) // move to next instruction
                        bracketStack.pop();
                    else // move back to matching '['
                        i = bracketStack.getLast();
                    break;
                // TODO loops
            }
        }

        this.output = sb.toString().trim();
    }


    public void setString(String str) throws IOException {
        str = str.replaceAll(" ","").trim(); // first remove whitespaces

        if(!isValid(str)) {
            throw new IOException("String is empty or contains invalid characters.");
        }

        // check for matching opening/closing bracket
        int opening = str.length() - str.replace("[","").length();
        int closing = str.length() - str.replace("]","").length();
        if(opening != closing){
            throw new IOException("Unmatched bracket(s).");
        }

        this.input = str;
        this.output = ""; // also clear the fields
        data = new byte[memSize];
    }

    public String getInput(){ return input; }

    public String getOutput(){
        return output;
    }

    private boolean isValid(String s){
        if(s.isEmpty()) return false;

        for(char c : s.toCharArray()){
            if(!(c == '+' || c == '-' || c == '<' ||  c == '>' ||  c == '.' ||  c == ',' ||  c == '[' ||  c == ']')){
                return false;
            }
        }
        return true;
    }

    private int findMatchingClosingBracket(int curr){
        return input.indexOf('[',curr);
    }
}


package model;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

/**
 * Created by jsondoo on 2017-02-17.
 */
public class BFInterpreter {
    private int memSize = 50000;
    private byte[] data;

    private Scanner scanner = new Scanner(System.in);
    private String input = null;
    private String output = null;

    // stack for keeping track of opening brackets (for handling nested loops)
    private Deque<Integer> bracketStack = new ArrayDeque<>();

    public BFInterpreter(){
        data = new byte[memSize];
    }

    // TODO infinite loop handling
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
                    else // current cell is not zero, move to next instruction
                        bracketStack.push(i);
                    break;
                case ']':
                    if(data[dp]==0) // current cell is zero, move to next instruction
                        bracketStack.pop();
                    else // else go back to start of loop
                        i = bracketStack.getLast();
                    break;
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
        int countOpeningBracket = str.length() - str.replace("[","").length();
        int countClosingBracket = str.length() - str.replace("]","").length();
        if(countOpeningBracket != countClosingBracket){
            throw new IOException("Unmatched bracket(s).");
        }

        this.input = str;
        this.output = ""; // also clear the fields
        data = new byte[memSize];
    }

    public String getInput(){
        return input;
    }

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


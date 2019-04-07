package com.availity;

import java.io.*;
import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class SyntaxChecker {
  /**
   * Class that validates parentheses of LISP code.
   * Takes a string as input and returns true if all parentheses are properly closed and nested.
   * Initially, LISP code will be written for validation in CLI. I plan to add the ability to 
   * define a path containing LISP files in the CLI, then the ability to read a string or
   * LISP file passed as a command line argument.
   */
    public static void main(String[] args) throws IOException {
      boolean isValid = false;
      SyntaxChecker sc = new SyntaxChecker();

      if(args.length == 1) {
        System.out.println("Please wait while LISP parentheses syntax is checked.");
        isValid = sc.parenthesesMatcher(args[0]);
        System.out.println("Parenetheses are properly closed and nested : " + isValid);
      }
      else {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter 1 to check test files, enter anything else to enter your own string.");
        String input = br.readLine();
        switch(input){
          case "1":
            br.close();
            sc.checkTestCases();
            break;
          default:
            System.out.println("Please enter a string of LISP code to have validated.");
            System.out.println("Please wait while LISP parentheses syntax is checked.");

            isValid = sc.parenthesesMatcher(input);
            System.out.println("Parenetheses are properly closed and nested : " + isValid);
            br.close();
            break;
        }
      }
    }

    private boolean parenthesesMatcher(String input) {
      /*
       * Takes an input string of LISP syntax and returns true or false based on whether or not
       * all parentheses are properly closed and nested. For small strings we iterate over it
       * as a char array.
       */
      Stack<String> stack = new Stack<String>();
      boolean atLeastOneElementAdded = false;

      for(int i = 0; i < input.length(); i++) {
        if(input.charAt(i) == '('){
          stack.push(input.substring(i, i));
          if(!atLeastOneElementAdded) {
            atLeastOneElementAdded = true;
          }
        }
        else if(input.charAt(i) == ')') {
          try {
            stack.pop();
          }catch(EmptyStackException e) {
            // If stack is empty, we can't match this closing parenthesis.
            return false;
          }
        }
        else if(input.charAt(i) == ';') {
          // Everything that follows until a new line is part of a LISP comment.
          break;
          //while(input.charAt(i) != '\n')
        }
      }

      // If stack is empty now, then all parentheses have been matched.
      return atLeastOneElementAdded && stack.empty();
    }

    private void checkTestCases() {
      File[] fileListing = new com.availity.FileLoader().getFiles("LISP");
      for(File f : fileListing) {
          try {
              // Initialize a stack, read in file as a stream, match parentheses on multiple lines.
              Stack<String> stack = new Stack<String>();
              boolean isValid = false;
              Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(f)));
              // We will read up to any comment character, skipping to next line if one is encountered.
              // Match 1 or more comment characters, 0 or more characters, excluding line terminators, and any newline sequence.
              scanner.useDelimiter(";+.*\\R");

              while(scanner.hasNextLine()){
                  String currentLine = scanner.next(); // up to ; character
                  // Parse line with stack
                  isValid = validateLISP(stack, currentLine, isValid);
              }

              scanner.close();
              System.out.println("File : " + f.getName() + "\t Valid : " + isValid);
          } catch(Exception e) {
              e.printStackTrace();
          }
      }
    }

    private boolean validateLISP(Stack<String> stack, String input, boolean stackModified) {
        /*
         * Takes multiple lines of input strings and returns stack after operations finish.
         */

        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == '('){
                stack.push(input.substring(i, i));
                if(!stackModified) {
                    stackModified = true;
                }
            }
            else if(input.charAt(i) == ')') {
                try {
                    stack.pop();
                    if(!stackModified) {
                        stackModified = true;
                    }
                }catch(EmptyStackException e) {
                    // If stack is empty, we can't match this closing parenthesis.
                    //System.out.println(("empty stack"));
                    return false;
                }
            }
        }

        return stackModified && stack.empty();
    }
}
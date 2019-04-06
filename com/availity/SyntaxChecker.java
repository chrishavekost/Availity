package com.availity;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.EmptyStackException;
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
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      System.out.println("Please enter a single line of LISP code to have validated.");
      String input = br.readLine();
      System.out.println("Please wait while LISP parentheses syntax is checked.");

      boolean isValid = parenthesesMatcher(input);
      System.out.println("Parenetheses are properly closed and nested : " + isValid);
      br.close();
    }

    private static boolean parenthesesMatcher(String input) {
      /**
       * Takes an input string of LISP syntax and returns true or false based on whether or not
       * all parentheses are properly closed and nested. For small strings we iterate over it as a char array.
       */
      Stack<String> stack = new Stack<String>();
      for(int i = 0; i < input.length(); i++){
        if(input.charAt(i) == '('){
          stack.push(input.substring(i, i));
        }
        else if(input.charAt(i) == ')'){
          try{
            stack.pop();
          }catch(EmptyStackException e){
            // If stack is empty, we can't match this closing parenthesis.
            return false;
          }
        }
      }
      
      try{
        stack.peek();
      }catch(EmptyStackException e){
        // If stack is empty, then all parentheses have been matched.
        return true;
      }
      return false;
    }
}
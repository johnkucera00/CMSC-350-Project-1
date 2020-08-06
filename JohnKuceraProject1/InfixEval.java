/*
* File: InFixEval.java
* Author: John Kucera
* Date: January 21, 2020
* Purpose: This Java program is meant to accompany P1GUI.java. It handles the
* calculation and validity checking of infix expressions that are input in the
* GUI.
*/

// import of necessary java classes
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.EmptyStackException;
import java.util.regex.*;

// Class creation
public class InfixEval {
    
    // Creating Stacks as instance variables
    private Stack<String> operand = new Stack<>();
    private Stack<String> operator = new Stack<>();
    
    // Method to convert infix expression characters into tokens
    private List<String> convertToken(String expression) {
        List<String> tokens = new ArrayList<>();
        tokens.add(Character.toString(expression.charAt(0)));
        // Expression iteration
        for (int i = 1; i < expression.length(); i++) {
            char token = expression.charAt(i);
            char endToken = expression.charAt(i - 1);
            // Bypass spaces
            if (token == ' ') {
                continue;
            }
            // Accounting for double digit numbers
            if (Character.isDigit(token) && Character.isDigit(endToken)) {
                tokens.set((tokens.size() - 1), tokens.get((tokens.size() - 1)) + token);
            }
            else {
                tokens.add(Character.toString(token));
            } // end of else
        } // end of for loop
        return tokens;
    } // end of convertToken method
    
    // Method for deciding operator precedence
    private int precedence(String operator) {
        int prec = 0;
        switch (operator) {
            case "+": // Addition and subtraction
            case "-":
                prec = 1;
                break;
            case "*": // Multiplication and division
            case "/":
                prec = 2;
        } // end of switch
        return prec;
    } // end of precedence method
    
    // Calculate two operands with operator
    private String calculate(String n2, String operator, String n1) throws DivideByZero {
        int firstNum = Integer.parseInt(n1);
        int secondNum = Integer.parseInt(n2);
        int result = 0;
        // Execute operation
        switch (operator) {
            case "+":
                result = firstNum + secondNum;
                break;
            case "-":
                result = firstNum - secondNum;
                break;
            case "*":
                result = firstNum * secondNum;
                break;
            case "/":
                if (secondNum != 0) {
                    result = firstNum / secondNum;
                }
                else { // handling Division by zero
                    throw new DivideByZero();
                }
        } // end of switch
        return Integer.toString(result);
    } // end of calculate method
     
    // Evaluate button method
    public String evaluate(String expression) throws EmptyStackException,
            DivideByZero, IllegalToken {
        List<String> tokens = convertToken(expression);
        // Check tokens
        for (String token : tokens) {
            Pattern operandChar = Pattern.compile("[0-9]+");
            Pattern operatorChar = Pattern.compile("[*/+-]");
            
            // Check for Illegal tokens
            if (!token.equals(")") && !token.equals("(")
                && !token.matches(String.valueOf(operandChar))
                && !token.matches(String.valueOf(operatorChar))) {
                throw new IllegalToken();
            }
            
            // Check what kind of token
            if (token.matches(String.valueOf(operandChar))) { // Operand
                operand.push(token);
            }
            else if (token.equals("(")) { // Left Parentheses
                operator.push(token);
            }
            else if (token.equals(")")) { // Right Parentheses
                while (!operator.peek().equals("(")) {
                    operand.push(calculate(operand.pop(), operator.pop(), operand.pop()));
                }
                operator.pop();
            }
            else if (token.matches(String.valueOf(operatorChar))) { // Operator
                while (!operator.empty() && precedence(token) <= precedence(operator.peek())) {
                operand.push(calculate(operand.pop(), operator.pop(), operand.pop()));
                }
            operator.push(token);
            } // end of last else if
        } // end of for loop
        
        // Continues calculating until no operations remain
        while (!operator.empty()) {
            operand.push(calculate(operand.pop(), operator.pop(), operand.pop()));
        }
        return operand.pop(); // final result
    } // end of evaluate method
} // end of class

/*
* File: P1GUI.java
* Author: John Kucera
* Date: January 21, 2020
* Purpose: This Java program creates a GUI for evaluating infix expressions of
* unsigned integers using two stacks. This GUI allows the user to input an infix
* expression and display the result. It then prints all evaluated expressions
* with their results to a file titled Results.txt.
*/

// import of necessary java classes
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.EmptyStackException;

// P1GUI Class
public class P1GUI extends JFrame {
    // Instance Variables
    private String expression;
    private String result;
    private InfixEval infixEval = new InfixEval();
    
    // Window Components
    private static JLabel inputLbl = new JLabel("Enter Infix Expression");
    private static JLabel resultLbl = new JLabel("Result");
    private static JTextField inputTxt = new JTextField(null, 20);
    private static JTextField resultTxt = new JTextField(null, 25);
    private static JButton evalBtn = new JButton("Evaluate");
    
    // Evaluate Button Listener
    class EvaluateBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Text Field -> Listener
            expression = inputTxt.getText();
            try {
                if (expression.isEmpty()) {
                    throw new NullPointerException();
                }
                else {
                    result = (infixEval.evaluate(expression));
                    resultTxt.setText(result);
                    // Writing expression and result to Results.txt
                    try {
                        BufferedWriter bwriter = new BufferedWriter(new FileWriter("Results.txt", true));
                        bwriter.append(expression + " = " + result + "\n");
                        bwriter.close();
                    } // End of inner try
                    catch (IOException ex) { // Handling IO Exception
                        System.out.println("File IO exception" + ex.getMessage());
                    } // End of inner catch
                } // end of else
            } // end of outer try
            
            // Handling invalid input
            catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Please enter an Infix Expression.");
            }
            catch (EmptyStackException ex) {
                JOptionPane.showMessageDialog(null,
                    "Your Infix Expression is invalid due to misplaced Operators, Operands, or Parentheses. Please try again.");
            }
            catch (DivideByZero ex) {
                JOptionPane.showMessageDialog(null,
                    "Division by zero is not allowed. Please try again.");
            }
            catch (IllegalToken ex) {
                JOptionPane.showMessageDialog(null,
                    "Only characters including 0 to 9, +, -, *, /, (, ) are allowed. Please try again.");
            } // end of catch
        } // end of method
    } // end of listener
    
    // GUI Creation
    public P1GUI() {
        // Frame Creation
        super("John's Infix Expression Evaluator"); // Titling frame
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Panels
        JPanel inputPanel = new JPanel(); // Input
        inputPanel.add(inputLbl);
        inputPanel.add(inputTxt);
        
        JPanel evalPanel = new JPanel(); // Evaluate Button
        evalPanel.add(evalBtn);
        evalBtn.addActionListener(new EvaluateBtnListener());
        
        JPanel resultPanel = new JPanel(); // Result
        resultPanel.add(resultLbl);
        resultPanel.add(resultTxt);
        resultTxt.setEditable(false);
                
        // Layout and main panel
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.add(inputPanel);
        main.add(evalPanel);
        main.add(resultPanel);
        add(main);
    } // end of GUI Creation
    
    // Main method
    public static void main(String[] args) {
        P1GUI gui = new P1GUI();
    } // end of main method
} // end of class

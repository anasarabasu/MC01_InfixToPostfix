import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Engine {


    public Engine() {

    }


    private JButton button;
    private JTextField text;
    private JLabel temp = new JLabel();


    // GUI - not sure if I should go through with this
    private void gui() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets.top = 8;
        
        c.gridy = 0;
        JLabel title = new JLabel("Infix to Postfix");
        panel.add(title, c);
        
        c.gridy = 1;
        text = new JTextField(c.test1);
        text.setPreferredSize(new Dimension(400, 30));
        panel.add(text, c);

        c.gridx = 1;
        button = new JButton("Convert");
        button.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                validate(text.getText());
            }
        });
        panel.add(button, c);
        
        c.gridx = 0;
        c.gridy = 2;
        temp = new JLabel("khfeskdhafkhkafhjk");
        temp.setOpaque(true);
        temp.setBackground(Color.WHITE);
        temp.setPreferredSize(new Dimension(400, 400));
        panel.add(temp, c);
        
        
        JFrame frame = new JFrame("Infix To Postfix Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 1000);
        frame.setVisible(true);
        frame.add(panel);
        
    }

    // public void convertExpression(String input) {
        
    //     String infix = validation(input);

    //     if(infix != null) {
    //         System.out.println("\n" + "-".repeat(24) + "\n");
            

    //         Queue<String> postfix = conversion(infix.split(" "));
    //         System.out.println("\n\n" + c.DEF + "-".repeat(24) + "\n");


    //         // System.out.println(evaluate(postfix));
    //     }

    // }


    // checks for errors before proceeding
    public String validate(String expression) {

        // String expression = Helpers.normalise(dta);
        
        boolean isValid = true;


        long start = System.nanoTime();
        

        if(!ErrorHandler.tokenSize(expression)) {
            System.out.println((System.nanoTime() - start) / 1000000.0 + " ms");
            System.out.println(c.RED + "ERROR : Invalid token amount!" + c.DEF);

            isValid = false;
        }
        else {
            int pos = ErrorHandler.invalidChar(expression);

            if(pos > -1) {
                System.out.println((System.nanoTime() - start) / 1000000.0 + " ms");
                System.out.println(
                    c.RED + "ERROR : Invalid character!\n" +
                    " ".repeat(pos) + "v\n"  + c.DEF +
                    expression + "\n"
                );
    
                isValid = false;
            }
            else {
                pos = ErrorHandler.maformedExp(expression);

                if(pos > -1) {
                    System.out.println((System.nanoTime() - start) / 1000000.0 + " ms");
                    System.out.println(
                        c.RED + "ERROR : Malformed expression!\n" 
                        + " ".repeat(pos) + "v\n"  + c.DEF +
                        expression + "\n"
                    );
    
                    isValid = false;
                }
                else {
                    pos = ErrorHandler.zeroDiv(expression);

                    if(pos > -1) {
                        System.out.println((System.nanoTime() - start) / 1000000.0 + " ms");
                        System.out.println(
                            c.RED + "ERROR : Division by zero!\n" +
                            " ".repeat(pos) + "v\n"  + c.DEF +
                            expression + "\n"
                        );

                        isValid = false;
                    }
                    else {
                        String error = ErrorHandler.mismatchedPar(expression);
                        
                        if(error == null) {
                            System.out.println((System.nanoTime() - start) / 1000000.0 + " ms");

                        }
                        else {
                            System.out.println((System.nanoTime() - start) / 1000000.0 + " ms");
                            System.out.println(
                                c.RED + "ERROR : Mismatched parenthesis!\n" +
                                error + "\n" + c.DEF +
                                expression + "\n"
                            );

                            isValid = false;
                        }
                    }
                }
            } 
        } 


        // PROCEED
        if(isValid) {
            System.out.println(c.YEL + expression + c.DEF);
            return expression;
        } 
        else return null;

    }


    // conversion functtion
    public Queue<String> convert(String[] token) {

        Stack<String> stack = new Stack<>(String.class, token.length);
        Queue<String> postfix = new Queue<>(String.class, token.length);
        
        int stackPrecedence = -1, currentPrecedence = 0;
        int index = 0;

        
        long start = System.nanoTime();
        do {

            // INSERT OPERANDS INTO QUEUE
            if(token[index].matches("\\d+|[A-Z]")) 
                postfix.enqueue(token[index]);
                

            else {                 
                currentPrecedence = Helpers.fetchPrecedence(token[index]);
                
                
                // PUSH OPERATORS INTO STACK
                if(stack.isEmpty() || stackPrecedence < currentPrecedence || token[index].equals("(")) {
                    stack.push(token[index]);
                    stackPrecedence = currentPrecedence;
                }

                    
                // POP OPERATORS FROM STACK
                else {

                    // parenthesis group found - pop operators until opening parenthesis
                    if(token[index].contains(")")) {
                        do postfix.enqueue(stack.pop());   
                        while(!stack.getTop().equals("("));

                        stack.pop();
                        stackPrecedence = Helpers.fetchPrecedence(stack.getTop());
                    }

                    else if(stackPrecedence >= currentPrecedence) {

                        // exponent
                        if(stackPrecedence == currentPrecedence && stackPrecedence == 2) {
                            stack.push(token[index]);
                            stackPrecedence = currentPrecedence;
                        }

                        // higher or equal level
                        else {
                            do {
                                postfix.enqueue(stack.pop());   
                                stackPrecedence = Helpers.fetchPrecedence(stack.getTop());
                            }
                            while(stackPrecedence >= currentPrecedence && !stack.isEmpty());
                            
                            stack.push(token[index]);
                            stackPrecedence = Helpers.fetchPrecedence(stack.getTop());
                        }
                    }
                    
                }
            }


            // System.out.println(c.YEL + ">>     " + token[index] + c.DEF);
            
            // System.out.print("ST  :  ");
            // stack.iterate();
            
            // System.out.print("\nPF  :  ");
            // postfix.iterate();
            
            // System.out.println("\n\n");
            
            index++;
        }
        while(index < token.length);


        while(!stack.isEmpty())
            postfix.enqueue(stack.pop());   
        // System.out.print(c.DEF + "ST  :  ");
        // stack.iterate();
        
        // System.out.print("\nPF  :  ");
        // postfix.iterate();
        
        // System.out.println("\n\n" + c.YEL);


        System.out.println((System.nanoTime() - start) / 1000000.0  + " ms" + c.YEL);
        postfix.iterate();

        System.out.println(c.DEF);

        return postfix;
    }


    // evaluation postfix solution
    public double evaluate(Queue<String> queue) {

        String error = "";
        Stack<Double> solution = new Stack<>(Double.class, queue.getCapacity());

        boolean isValid = true;
        
        long start = System.nanoTime();

        do {
            String token = queue.dequeue();
            error = error.concat(token + " ");

            if(Helpers.isVar(token)) {
                solution.push(-1.0);
                isValid = false;
            }
            else {

                // OPERATORS
                if("+-*/%^".contains(token)) {
                    double R = solution.pop();
                    double L = solution.pop();

                    if(token.equals("/") && R == 0) {
                        

                        solution.push(0.0);
                        isValid = false; // zero div checker
                    }
                    else solution.push(Helpers.compute(L, R, token));
                }

                // OPERANDS
                else solution.push(Double.parseDouble(token));
            }

        }
        while(!queue.isEmpty() && isValid);


        System.out.println((System.nanoTime() - start) / 1000000.0 +  " ms" + c.YEL);

                    
        if(isValid) return solution.getTop();
        else {
            if(solution.getTop() < 0)
                System.out.println(
                    c.RED + "ERROR : Can't evaluate an expression with varibles\n" + c.DEF+
                    " ".repeat(error.length()-2) + "v\n"  + c.DEF +
                    error
                );
            else 
                System.out.println(
                    c.RED + "ERROR : Division by zero!\n" + c.DEF  +
                    " ".repeat(error.length()-2) + "v\n"  + c.DEF +
                    error
                );

            return -1;
        } 

    }

}
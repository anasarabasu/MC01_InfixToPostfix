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
                checkInput(text.getText());
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

    public void convertExpression(String input) {
        
        String expression = checkInput(input);

        if(expression != null) {
            System.out.println("\n" + "-".repeat(24) + "\n");
            
            convert(expression.split(" "));
            System.out.println("\n\n" + c.DEF + "-".repeat(24) + "\n");



        }

    }

    // cleans and checks for errors before proceeding
    private String checkInput(String rawData) {

        boolean valid = false; 


        // string clean up
        String expression = rawData
            .replaceAll("([\\^*\\/%+\\-\\(\\)])", " $0 ") // adds spaces on operators
            .replaceAll("\\s+", " ") // removes extra spaces
            .replaceAll("(\\()\\s", "(") // "( " -> "("
            .replaceAll("\\s\\)", ")") // " )" -> ")"
            .trim()
            .toUpperCase(); 

        
        // ERROR HANDLING : LESS THAN 5 TOKENS
        if(expression.split(" ").length < 5 && expression.split(" ").length > 100) 
            System.out.println(c.RED + "ERROR : Invalid token amount!" + c.DEF);
        

        // ERROR HANDLING : INVALID CHARACTERS
        else {            
            Matcher m = Pattern.compile("[^\\s\\(\\)^*\\/%+\\-\\dA-Z]").matcher(expression);
            if(m.find()) 
                System.out.println(
                    c.RED + "ERROR : Invalid character!\n" +
                    " ".repeat(m.start()) + "v\n"  + c.DEF +
                    expression + "\n"
                );
            
            
            // ERROR HANDLING : ZERO DIVISION - initial
            else {
                m = Pattern.compile("\\/\\s0").matcher(expression);
                if(m.find()) {
                    System.out.println(
                        c.RED + "ERROR : Division by zero!\n" +
                        " ".repeat(m.start()) + "v\n"  + c.DEF +
                        expression + "\n"
                    );
                }

                
                // ERROR HANDLING : MALFORMED EXPRESSIONS
                else {
                    m = Pattern.compile(
                        "[\\^*\\/%+-]\\s[\\^*\\/%+-]|" + // a + - b c
                        "^[\\^*\\/%+-]\\s[\\dA-Z]|"+ // (+ a b) 
                        "[\\dA-Z]\\s[\\^*\\/%+-]$|"+ // (a b +)
                        "[\\dA-Z]\\s[\\dA-Z]|" + // (a b)
                        "[\\^*\\/%+-]\\)|" + // +)
                        "\\([\\^*\\/%+-]|" + // (+
                        "\\)\\s[\\dA-Z]|" +  // ) a
                        "[\\dA-Z]\\s\\(|" +  // b (
                        "\\)\\s\\(|" + // ) (
                        "\\(\\)" // ()
                    ).matcher(expression); 
                    if(m.find() ) {
                        System.out.println(
                            c.RED + "ERROR : Malformed expression!\n" +
                            " ".repeat(m.start()) + "v\n"  + c.DEF +
                            expression + "\n"
                        );
                    }


                    // ERROR HANDLING : MISMATCHED PARENTHESES
                    else {
                        valid = true;

                        m = Pattern.compile("[\\(\\)]").matcher(expression);
                        if(m.find()) {
                            String error = "";
                            char[] e = expression.toCharArray();
                            
                            int pair = 0, index = 0;
                            do {
                                switch(e[index]) {
                                    case ')':
                                        pair--;
                                        
                                        if(pair < 0) error = error.concat("v");
                                        else {
                                            StringBuilder s = new StringBuilder(error);
                                            error = s.deleteCharAt(s.lastIndexOf("v")).append(' ').toString();
                                        }

                                    default:
                                        error = error.concat(" ");
                                        break;

                                    case '(':
                                        pair++;
                                        error = error.concat("v");
                                        break;
                                }
                                
                                index++;
                            }
                            while(pair >= 0  && index < e.length) ;
                            

                            if(pair != 0) {
                                valid = false;

                                System.out.println(
                                    c.RED + "ERROR : Mismatched parenthesis!\n" +
                                    error + "\n" + c.DEF +
                                    expression + "\n"
                                );
                            }
                        }
                    }
                }
            }
        }


        // PROCEED
        if(valid) {
            System.out.println(c.YEL + expression + c.DEF);
            expression = expression.replaceAll("(\\()", " $0 ").replaceAll("(\\))", " $0 ").replaceAll("\\s+", " ").trim();
        } 
        else expression = null;

        return expression;
    }

    
    // find the operator's level
    private int fetchPrecedence(String token) {
        int level = -1;

        if(token != null) {
            if("+-".contains(token)) level = 0; 
            else if("*/%".contains(token)) level = 1; 
            else if("^".contains(token)) level = 2; 
        }

        return level;
    }


    // conversion functtion
    private void convert(String[] token) {

        Stack<String> stack = new Stack<>(String.class, token.length);
        Queue<String> postfix = new Queue<>(String.class, token.length);
        
        int stackPrecedence = -1;
        int currentPrecedence = 0;

        int index = 0;
        while(index < token.length) {


            // INSERT OPERANDS INTO QUEUE
            if(token[index].matches("\\d+|[A-Z]")) 
                postfix.enqueue(token[index]);
                

            else {                 
                currentPrecedence = fetchPrecedence(token[index]);
                
                
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
                        stackPrecedence = fetchPrecedence(stack.getTop());
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
                                stackPrecedence = fetchPrecedence(stack.getTop());
                            }
                            while(stackPrecedence >= currentPrecedence && !stack.isEmpty());
                            
                            stack.push(token[index]);
                            stackPrecedence = fetchPrecedence(stack.getTop());
                        }
                    }
                    
                }
            }


            System.out.println(c.YEL + ">>     " + token[index] + c.DEF);
            
            System.out.print("ST  :  ");
            stack.iterate();
            
            System.out.print("\nPF  :  ");
            postfix.iterate();
            
            System.out.println("\n\n");
            
            index++;
        }


        while(!stack.isEmpty()) {
            postfix.enqueue(stack.pop());   

            System.out.print(c.DEF + "ST  :  ");
            stack.iterate();
            
            System.out.print("\nPF  :  ");
            postfix.iterate();
            
            System.out.println("\n\n" + c.YEL);

        }

        postfix.iterate();
    }


    // evaluation postfix solution
    private void evaluate(Queue postfix) {
        
    }


}
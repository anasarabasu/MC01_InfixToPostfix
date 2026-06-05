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
        // init();
    }


    private JButton button;
    private JTextField text;
    private JLabel temp = new JLabel();


    // GUI - not sure if I should go through with this
    private void init() {

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
    

    // checks for errors before proceeding
    public void checkInput(String rawData) {

        // string clean up
        String expression = rawData
            .replaceAll("([\\^*\\/%+-])", " $0 ") // +/ -> + /
            .replaceAll("([\\da-zA-Z])()([\\^*\\/%+-])", "$1 $2 $3") // a+ -> a +
            .replaceAll("([\\^*\\/%+-])()([\\da-zA-Z])", "$1 $2 $3") // +b -> + b
            .replaceAll("(\\(|\\))", " $0 ")
            .replaceAll("\\s+", " ") // "    " -> " "
            .replaceAll("(\\()\\s", "(") // "( " -> "("
            .replaceAll("\\s\\)", ")") // " )" -> ")"
            .trim();

        
        // ERROR HANDLING : LESS THAN 5 TOKENS
        if(expression.split(" ").length < 5) 
            System.out.println(c.RED + "ERROR : Insufficent token amount!" + c.DEF);
        

        else {
            // ERROR HANDLING : INVALID CHARACTERS
            Matcher m = Pattern.compile("[^\\s()^*\\/%+\\-\\da-zA-Z]").matcher(expression);
            if(m.find())
                System.out.println(
                    c.RED + "ERROR : Invalid character!\n" +
                    " ".repeat(m.start()) + "v\n"  + c.DEF +
                    expression + "\n"
                );
            
            
            // ERROR HANDLING : ZERO DIVISION - initial
            else {
                m = Pattern.compile("\\/\\s0").matcher(expression);
                if(m.find()) 
                    System.out.println(
                        c.RED + "ERROR : Division by zero!\n" +
                        " ".repeat(m.start()) + "v\n"  + c.DEF +
                        expression + "\n"
                    );

                
                // ERROR HANDLING : MALFORMED EXPRESSIONS
                else {
                    m = Pattern.compile(
                        "[\\^*\\/%+-]\\s[\\^*\\/%+-]|" + // (a + - b c)
                        "^[\\^*\\/%+-]\\s[\\da-zA-Z]|"+ // (+ a b) 
                        "[\\da-zA-Z]\\s[\\^*\\/%+-]$|"+ // (a b +)
                        "[\\da-zA-Z]\\s[\\da-zA-Z]|" + // (a b)
                        "[\\^*\\/%+-]\\)|" + // +)
                        "\\([\\^*\\/%+-]|" + // (+
                        "\\)\\s[\\da-zA-Z]+|" +  // ) a
                        "[\\da-zA-Z]\\s\\(|" +  // b (
                        "\\)\\s\\(|" + // ) (
                        "\\(\\)" // ()
                    ).matcher(expression); 
                    if(m.find() ) 
                        System.out.println(
                            c.RED + "ERROR : Malformed expression!\n" +
                            " ".repeat(m.start()) + "v\n"  + c.DEF +
                            expression + "\n"
                        );


                    // ERROR HANDLING : MISMATCHED PARENTHESES
                    else {
                        m = Pattern.compile("[\\(\\)]").matcher(expression);
                        if(m.find()) {
                            String error = "";

                            int index = 0;
                            int pair = 0;
                            
                            char[] e = expression.toCharArray();

                            while(pair >= 0 && index < e.length) {
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
                        
                            if(pair != 0)
                                System.out.println(
                                    c.RED + "ERROR : Mismatched parenthesis!\n" +
                                    error + "\n" + c.DEF +
                                    expression + "\n"
                                );
                        }

                        // PROCEED
                        else {
                            System.out.println("ok\n" + expression);
                            convertToPostfix(expression.split(" "));
                        } 
                    }
                }
            }
        }

    }


    // conversion functtion
    private void convertToPostfix(String[] token) {

        Stack<String> stack = new Stack<>(String.class, token.length);
        Queue<String> postfix = new Queue<>(String.class, token.length);
        


        int index = 0;
        boolean canProceed = true;

        while(index < token.length && canProceed) {

            // insert number or variable into postfix queue
            if(Pattern.compile("[\\da-zA-Z]").matcher(token[index]).find()) {
                
                // PAR
            //     if(token[index].length() < 3) { // single parenthesis (a + b)
            //             if(token[index].contains("(")) mismatch++;
            //             if(token[index].contains(")")) mismatch--;
            //         }

            //     else { // multiple parenthesis ((a + b))
            //         for (char t : token[index].toCharArray()) {
            //             if(t == '(') mismatch++;
            //             if(t == ')') mismatch--;
            //         }
            //     }


            //     postfix.enqueue(token[index]);
            }

            // // parse operators
            // else { 
            //     if(index == token.length -  1)
            //         postfix.enqueue(stack.pop());
                
            //     // ADD SUB

            //     stack.push(token[index]);
            // } 

            index++;
        }

        // postfix.iterate();

    }


}
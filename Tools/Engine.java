import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Engine {

    public Engine() {
        initDisplay();
        checkInput();
    }

    private JButton button;
    private JTextField text;
    private JLabel temp;

    private void initDisplay() {

        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.insets.top = 8;
        
        c.gridy = 0;
        JLabel title = new JLabel("Infix to Postfix");
        panel.add(title, c);
        
        c.gridy = 1;
        text = new JTextField(Constants.test1);
        text.setPreferredSize(new Dimension(400, 30));
        panel.add(text, c);

        c.gridx = 1;
        button = new JButton("Convert");
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
    

    private void checkInput() {

        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String raw = text.getText();
                
                // ERROR HANDLING #1 : INVALID CHARACTERS
                if(Pattern.compile("[^\\s()^*\\/%+\\-\\da-zA-Z]").matcher(raw).find()) 
                    temp.setText("ERROR! Invalid Characters!");
                
                else {
                    boolean canProceed = true;
                    String tokens[] = raw.split("\\s");

                    
                    // ERROR HANDLING #2 : MISMATCHED PARENTHESES
                    if(Pattern.compile("[()]").matcher(raw).find()) {
                        int mismatch = 0;
                        
                        for (String token : tokens) {
                            if(token.length() < 3) { // single parenthesis (a + b)
                                if(token.contains("(")) mismatch++;
                                if(token.contains(")")) mismatch--;
                            }

                            else { // multiple parenthesis ((a + b))
                                for (char t : token.toCharArray()) {
                                    if(t == '(') mismatch++;
                                    if(t == ')') mismatch--;
                                }
                            }
                        }

                        if(mismatch != 0) {
                            canProceed = false;
                            temp.setText("ERROR! Mismatched parentheses!");
                        }
                    }
                    

                    // ERROR HANDLING #3 : MALFORMED EXPRESSIONS
                    if(
                        Pattern.compile("[\\^*\\/%+-]\\s[\\^*\\/%+-]").matcher(raw).find() // order (a + - b c)
                        ||
                        tokens.length % 2 ==  0 // length (a + b -)
                    ) {
                        canProceed = false;
                        temp.setText("ERROR! Malformed expressions!");
                    }


                    if(canProceed) {
                        temp.setText("");
                        convertToPostfix(tokens);
                    }

                }
            }
        });
    }


    
    
    private void convertToPostfix(String[] tokens) {

        Stack<String> stack = new Stack<>(String.class, tokens.length);
        Queue<String> postfix = new Queue<>(String.class, tokens.length);
        
        // int index = 0;
        // boolean canProceed = true;
        // while(canProceed && index < tokens.length) {
            
        // // }

        // postfix.iterate();

    }
}

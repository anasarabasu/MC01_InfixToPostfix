import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;

public class GUI {
    
    public GUI(Engine engine) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.lightGray);
        panel.setBounds(20, 25, 750, 900);
        
        GridBagConstraints c = new GridBagConstraints();

        // -----------------------------------------


        JLabel title = new JLabel("Infix to Postfix Converter");
        title.setFont(new Font("Courier", Font.PLAIN, 16));
        c.insets = new Insets(0, 0, 32, 0);
        c.gridwidth = 3;
        c.gridx = GridBagConstraints.RELATIVE;
        c.weightx = 1;
        panel.add(title, c);
        
        // -----------------------------------------

        JTextArea text = new JTextArea(6, 64);
        text.setText("Enter expression here");
        text.setFont(new Font("Courier", Font.PLAIN, 12));
        text.setMargin(new Insets(10, 16, 10, 16));
        text.setLineWrap(true);
        text.addFocusListener(new FocusListener() {
            
            @Override
            public void focusGained(FocusEvent arg0) {
                if(text.getText().equals("Enter expression here")) text.setText("");
            }
            
            @Override
            public void focusLost(FocusEvent arg0) {
                if(text.getText().isBlank()) text.setText("Enter expression here");
            }
            
        });
        
        JScrollPane inputPane = new JScrollPane(text);
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 1;
        panel.add(inputPane, c);
        

        // -----------------------------------------


        JPanel randomiserExtra = new JPanel(new GridBagLayout());
        randomiserExtra.setVisible(false);
        c.gridwidth = 1;
        c.gridy = 2;
        panel.add(randomiserExtra, c);

        SpinnerNumberModel t = new SpinnerNumberModel();
        t.setValue(5);

        JSpinner tokens = new JSpinner(t);
        c.insets = new Insets(0, 0, 0, 4);
        c.gridy = 0;

        randomiserExtra.add(tokens, c);

        SpinnerNumberModel d = new SpinnerNumberModel();
        d.setValue(1);
        JSpinner digits = new JSpinner(d);
        randomiserExtra.add(digits, c);

        JButton enter = new JButton("Random");
        enter.setPreferredSize(new Dimension(76, 0));
        c.insets = new Insets(0, 0, 0, 0);
        randomiserExtra.add(enter, c);
        enter.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tokens.commitEdit();
                    digits.commitEdit();
                } 
                catch (ParseException e1) {
                    e1.printStackTrace();
                }

                if((Integer) t.getValue() < 5) t.setValue(5);
                if((Integer) t.getValue() > 10000) t.setValue(10000);

                if((Integer) d.getValue() < 1) d.setValue(1);
                if((Integer) d.getValue() > 9) d.setValue(9);

                text.setText(Helpers.generateExpression((Integer) t.getValue(), (Integer) d.getValue()));
            }
            
        });
        
        
        JButton generateRandom = new JButton("Generate random");
        c.insets = new Insets(4, 4, 4, 4);
        c.gridwidth = 1;
        c.gridy = 2;
        panel.add(generateRandom, c);
        generateRandom.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandom.setVisible(false);
                randomiserExtra.setVisible(true);
                text.setText("Input the max number of tokens (min 5) and digits (max 9)");
            }
            
        });
        
        // -----------------------------------------

        JTextPane output = new JTextPane();
        output.setContentType("text/html");
        // output.setFocusable(false);
        output.setMargin(new Insets(10, 16, 10, 32));
        output.setPreferredSize(new Dimension(0, 600));

        JScrollPane outputPane = new JScrollPane(output);
        c.insets = new Insets(32, 4, 0, 4);
        c.gridwidth = 3;
        c.gridy = 3;
        panel.add(outputPane, c);


        JButton convert = new JButton("Convert to postfix");
        c.insets = new Insets(4, 4, 4, 4);
        c.gridwidth = 1;
        c.gridy = 2;
        panel.add(convert, c);
        convert.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String t = "<font face=\"Courier\" size=6px> ";

                String norm = engine.normalise(text.getText());

                engine.validate(norm); // warm up
                Queue<String> val = engine.validate(norm);

                t = t.concat(engine.message);
                if(val != null) {
                    Queue<String> q = engine.convert(val);
                    t = t.concat(engine.message);
                    t = t.concat(engine.pf);

                    double sol = engine.evaluate(q);
                    t = t.concat(engine.message);
                    t = t.concat("<br><h1 style=\"font-family: Courier\"> = " + sol + "</h1>");
                }
                
                output.setText(t);
                output.setCaretPosition(0);
            }
            
        });


        // -----------------------------------------

        JButton clearText = new JButton("Clear text area");
        c.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(clearText, c);
        clearText.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText("Enter expression here");
            }
            
        });

        // -----------------------------------------

        

        // -----------------------------------------


        JFrame frame = new JFrame("Converter");
        frame.setLayout(null);
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 1000);
        frame.setVisible(true);
        frame.requestFocusInWindow();

    }




}

package src;

import java.util.ArrayList;
import java.util.Collections;
 
import javax.swing.*;        
import java.awt.*;
import java.awt.event.*;

// TODO: decimal point

public class Jcalc {
    private static ArrayList<Object> stack = new ArrayList<>();
    private static String display = "";
    static JLabel label;

    private enum OP{
        ADD,
        SUB,
        MUL,
        DIV,
        MOD,
    }

    static void pushToDisplay(char c) {
        display += c;
        label.setText(display);
    }

    static void calculate() {
        if (!display.isEmpty()) {
            double d = Double.parseDouble(display);
            stack.add(d);
        }

        Collections.reverse(stack);
        System.out.println(stack);
        final double b = (double)stack.remove(stack.size() - 1);
        final OP op = (OP)stack.remove(stack.size() - 1);
        final double a = (double)stack.remove(stack.size() - 1);

        switch (op) {
            case OP.ADD:
                stack.add(String.valueOf(a + b));
                break;
            case OP.SUB:
                stack.add(String.valueOf(b - a));
                break;
            case OP.MUL:
                stack.add(String.valueOf(b * a));
                break;
            case OP.DIV:
                stack.add(String.valueOf(b / a));
                break;
            case OP.MOD:
                stack.add(String.valueOf(b % a));
                break;
        }

        display = (String)stack.remove(stack.size() - 1);
        label.setText(display);
    }

    static void addButton(JPanel pane, String s) {
        JButton btn = new JButton(s);
        btn.setActionCommand(s);
        btn.addActionListener(e -> pushToDisplay(e.getActionCommand().charAt(0)));
        pane.add(btn);
    }

    static void addSymBtn(JPanel pane, String s) {
        JButton btn = new JButton(s);
        btn.setActionCommand(s);
        btn.addActionListener(e -> {
            double d = Double.parseDouble(display);
            stack.add(d);
            display = " ";
            label.setText(display);

            switch (s) {
                case "+": stack.add(OP.ADD); break;
                case "-": stack.add(OP.SUB); break;
                case "*": stack.add(OP.MUL); break;
                case "/": stack.add(OP.DIV); break;
                case "%": stack.add(OP.MOD); break;
            }
        });

        pane.add(btn);
    }

    static void addClearBtn(JPanel equalsRow, Dimension keySize) {
        JButton clear = new JButton("C");
        clear.setActionCommand("C");
        Dimension clearPref = clear.getPreferredSize();
        int clearWidth = Math.max(keySize.width, clearPref.width);
        clear.setPreferredSize(new Dimension(clearWidth, keySize.height));
        clear.addActionListener(e -> {
            stack.clear();
            display = "";
            label.setText(" ");
        });
        equalsRow.add(clear, BorderLayout.EAST);
    }

    private static void addEqualsButton(JFrame frame, JPanel pane) {
        JButton equals = new JButton("=");
        equals.setActionCommand("=");
        equals.addActionListener(e -> calculate());

        JButton sample = (JButton) pane.getComponent(0);
        Dimension keySize = sample.getPreferredSize();
        equals.setPreferredSize(new Dimension(keySize.width * 3, keySize.height));

        JPanel equalsRow = new JPanel(new BorderLayout(0, 0));
        equalsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        equalsRow.setBorder(BorderFactory.createEmptyBorder(0, 30, 10, 30));
        equalsRow.add(equals, BorderLayout.CENTER);
        addClearBtn(equalsRow, keySize);
        frame.getContentPane().add(equalsRow);
    }

    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Couldn't use the cross-platform look and feel: " + e);
        }

        //Create and set up the window.
        JFrame frame = new JFrame("Jcalc");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(
            new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS)
        );

        label = new JLabel(" ");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        label.setFont(new Font(
            label.getFont().getName(),
            label.getFont().getStyle(),
            20
        ));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height));
        frame.getContentPane().add(label);

        JPanel pane = new JPanel();
        pane.setAlignmentX(Component.LEFT_ALIGNMENT);
        pane.setBorder(BorderFactory.createEmptyBorder(25, 30, 10, 30));
        pane.setLayout(new GridLayout(4, 3));

        addButton(pane, "1");
        addButton(pane, "2");
        addButton(pane, "3");
        addSymBtn(pane, "+");
        addButton(pane, "4");
        addButton(pane, "5");
        addButton(pane, "6");
        addSymBtn(pane, "-");
        addButton(pane, "7");
        addButton(pane, "8");
        addButton(pane, "9");
        addSymBtn(pane, "*");
        addButton(pane, ".");
        addButton(pane, "0");
        addSymBtn(pane, "%");
        addSymBtn(pane, "/");

        ActionListener listener = e -> pushToDisplay(e.getActionCommand().charAt(0));
        frame.getContentPane().add(pane);

        addEqualsButton(frame, pane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

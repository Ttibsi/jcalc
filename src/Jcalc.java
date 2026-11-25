package src;
 
import javax.swing.*;        
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
 
public class Jcalc {
    private static String display = "";
    static JLabel label;

    static void pushToDisplay(char c) {
        display += c;
        System.out.println("display: " + display);
        label.setText(display);
    }

    static void addButton(JPanel pane, String s) {
        JButton btn = new JButton(s);
        btn.setActionCommand(s);
        btn.addActionListener(e -> pushToDisplay(e.getActionCommand().charAt(0)));
        pane.add(btn);
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

        //Add the ubiquitous "Hello World" label.
        label = new JLabel(" ");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font(
            label.getFont().getName(),
            label.getFont().getStyle(),
            20
        ));
        frame.getContentPane().add(label);

        JPanel pane = new JPanel();
        pane.setBorder(BorderFactory.createEmptyBorder(50, 30, 10, 30));
        pane.setLayout(new GridLayout(4, 3));

        addButton(pane, "1");
        addButton(pane, "2");
        addButton(pane, "3");
        addButton(pane, "4");
        addButton(pane, "5");
        addButton(pane, "6");
        addButton(pane, "7");
        addButton(pane, "8");
        addButton(pane, "9");
        addButton(pane, "0");

        ActionListener listener = e -> pushToDisplay(e.getActionCommand().charAt(0));
        frame.getContentPane().add(pane);

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

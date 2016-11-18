package com.bearcave.automaty;

import javax.swing.*;

/**
 * Created by miwas on 07.11.16.
 */
public class AutomatonGUI {

    private JPanel mainFrame;
    private JButton startButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("AutomatonGUI");
        frame.setContentPane(new AutomatonGUI().mainFrame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}

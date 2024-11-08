package io.github.javaGame.game;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("frame");
        frame.setLayout(null);
        JPanel panel = new JPanel();
        frame.setSize(800,600);
        frame.add(panel);
        panel.setBackground(Color.BLACK);
        panel.setSize(2,2);
        panel.setLocation(0,0);
        panel.setVisible(true);
        frame.setVisible(true);

    }
}

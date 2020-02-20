package com.conan.snake;

import javax.swing.*;

public class StartGames {
    public static void main(String[] args) {
        JFrame frame = new JFrame("snake game");
        frame.setBounds(10,10,900,720);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new GamePanel());
        frame.setVisible(true);

    }
}

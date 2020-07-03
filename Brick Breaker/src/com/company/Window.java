package com.company;

import javax.swing.*;

public class Window {
    public Window(String name,Game game){
        JFrame frame=new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.add(game);
        frame.pack();
    }
}

package com.packages.gui;

import com.packages.web.Request;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame {
    public MainWindow(String title, int w, int h){
        //M: creating main window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(w, h);
            setTitle(title);

        //M: A layout that lets us position multiple widgets
        JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        //M: Search input
        JTextField web_input = new JTextField(1);
            panel.add(web_input);
        //M: button that calls web visualise
        JButton web_btn = new JButton("Fetch");
            web_btn.addActionListener(e -> {
                try {
                    Request.Fetch(web_input.getText());
                }catch(IOException ioException){
                    System.out.println("Request fetch caught an error: " + ioException.getMessage());
                }
            });
            panel.add(web_btn);

        add(panel, BorderLayout.CENTER);
        //M: Making the window visible
        setVisible(true);
    }
}

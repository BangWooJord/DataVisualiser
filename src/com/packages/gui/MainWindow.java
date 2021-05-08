package com.packages.gui;

import com.packages.web.Request;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame {
    public MainWindow(String title, int w, int h){
        //M: creating main window
        setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(w, h);
            setTitle(title);

        //M: button that calls web visualise
        JButton web_btn = new JButton("Fetch");

            web_btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Request.Fetch("");
                            }catch(IOException ioException){
                                System.out.println("Request fetch caught an error: " + ioException.getMessage());
                            }
                        }
                    });
                }
            });
            add(web_btn);

        //M: Making the window visible
        setVisible(true);
    }
}

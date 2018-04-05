/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superawesomechicken.searchalgorithm;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Saud
 */
public class Plotter2D {
    
    private TextArea[][] buffer;
    
    public Plotter2D(int width, int height) {
        buffer = new TextArea[height][width];
        for(int i=0; i<buffer.length; i++) {
            for(int j=0; j<buffer[0].length; j++) {
                buffer[i][j] = new TextArea();
                buffer[i][j].setFocusable(false);
            }
        }
    }
    
    public void setColour(int i, int j, Color c, boolean repaint) {
        buffer[j][i].setBackground(c);
        if(repaint) {
            buffer[j][i].repaint();
            buffer[j][i].validate();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Plotter2D.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setText(int i, int j, String text, boolean repaint) {
        buffer[j][i].setText(text);
        if(repaint) {
            buffer[j][i].repaint();
            buffer[j][i].validate();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Plotter2D.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //////////////////////////////////////////////
    private JFrame frame;
    
    public void show(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(getGridLayout());
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
    public void show() {
        show("Plotter2D");
    }
    
    private JPanel getGridLayout() {
        GridLayout layout = new GridLayout(buffer.length, buffer[0].length);
        JPanel out = new JPanel(layout);
        out.setBackground(Color.BLACK);
        for(int i=0; i<buffer.length; i++) {
            for(int j=0; j<buffer[0].length; j++) {
                TextField tf = new TextField();
                tf.setFocusable(false);
                out.add(tf);
            }
        }
                
        return out;
    }
    
    public void repaintAll() {
        for(int i=0; i<buffer.length; i++) {
            for(int j=0; j<buffer[0].length; j++) {
                buffer[i][j].repaint();
            }
        }
    }
}

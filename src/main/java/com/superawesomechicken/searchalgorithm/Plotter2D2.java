/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superawesomechicken.searchalgorithm;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 *
 * @author Saud
 */
public class Plotter2D2 extends JComponent {
    
    private Color[][] buffer;
    private String[][] text;
    
    public Plotter2D2(int width, int height) {
        buffer = new Color[width][height];
        text = new String[width][height];
    }
    
    public void setColour(int i, int j, Color c) {
        buffer[i][j] = c;
    }
    
    public void setText(int i, int j, String str) {
        text[i][j] = str;
    }
    
    public int getGridWidth() {
        return buffer.length;
    }
    public int getGridHeight() {
        return buffer[0].length;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int totalWidth = g2.getClipBounds().width;
        int totalHeight = g2.getClipBounds().height;
        int w = totalWidth / buffer.length;
        int h = totalHeight / buffer[0].length;
        
        for(int i=0; i<buffer.length; i++) {
            for(int j=0; j<buffer[0].length; j++) {
                if(buffer[i][j] != null) {
                    g2.setColor(buffer[i][j]);
                    g2.fillRect(i*w, j*h, w, h);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(i*w, j*h, w, h);
                }
                if(text[i][j] != null) {
                    g2.setColor(Color.BLACK);
                    drawStringCenter(g2, text[i][j], i*w+w/2, j*h+h/2);
                }
            }
        }
        
    }
    
    private void drawStringCenter(Graphics2D g2, String str, int x, int y) {
        FontMetrics metrics = g2.getFontMetrics(g2.getFont());
        x = x - metrics.stringWidth(str) / 2;
        y = (int)(y + metrics.getHeight() * 0.4);
        g2.drawString(str, x, y);
    }
    
}

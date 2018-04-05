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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;

/**
 *
 * @author Saud
 */
public class Plotter2D3 extends JComponent implements MouseListener, MouseMotionListener {
    
    private Color[][] buffer;
    private String[][] text;
    private int boxW;
    private int boxH;
    
    public Plotter2D3(int width, int height) {
        buffer = new Color[width][height];
        text = new String[width][height];
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void setColour(int i, int j, Color c) {
        buffer[i][j] = c;
    }
    
    public Color getColour(int i, int j) {
        return buffer[i][j];
    }
    
    public void setText(int i, int j, String str) {
        text[i][j] = str;
    }
    
    public void clear(Color defaultC, Color... excludeColours) {
        for(int i=0; i<buffer.length; i++) {
            for(int j=0; j<buffer[0].length; j++) {
                setText(i, j, null);
                if(!arrContains(excludeColours, getColour(i, j)))
                    setColour(i, j, defaultC);
            }
        }
    }
    private boolean arrContains(Object[] arr, Object o) {
        for(Object ob : arr) {
            if(ob.equals(o)) return true;
        }
        return false;
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
        boxW = totalWidth / buffer.length;
        boxH = totalHeight / buffer[0].length;
        
        for(int i=0; i<buffer.length; i++) {
            for(int j=0; j<buffer[0].length; j++) {
                if(buffer[i][j] != null) {
                    g2.setColor(buffer[i][j]);
                    g2.fillRect(i*boxW, j*boxH, boxW, boxH);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(i*boxW, j*boxH, boxW, boxH);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.fillRect(i*boxW, j*boxH, boxW, boxH);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(i*boxW, j*boxH, boxW, boxH);
                }
                if(text[i][j] != null) {
                    g2.setColor(Color.BLACK);
                    drawStringCenter(g2, text[i][j], i*boxW+boxW/2, j*boxH+boxH/2);
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
    
    
    private int lastSelectedX = -2;
    private int lastSelectedY = -2;
    @Override
    public void mouseClicked(MouseEvent e) {
        run(getAcross(e.getX()), getDown(e.getY()));
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if(getAcross(e.getX()) != lastSelectedX || getDown(e.getY()) != lastSelectedY) {
            run(getAcross(e.getX()), getDown(e.getY()));
            lastSelectedX = getAcross(e.getX());
            lastSelectedY = getDown(e.getY());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
    
    private int getAcross(int x) {
        int across = x/boxW;
        if(across == buffer.length) across = -1;
        return across;
    }
    
    private int getDown(int y) {
        int down = y/boxH;
        if(down == buffer[0].length) down = -1;
        return down;
    }
    
    public void run(int across, int down) {}

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superawesomechicken.searchalgorithm;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Saud
 */
public class Viewer {
    
    private static Grid g = null;
    
    public static void main(String[] args) {
        
        final Plotter2D3 plotter = new Plotter2D3(8, 6) {
            @Override
            public void run(int across, int down) {
                System.out.println(across+" "+down);
                if(g!=null) g.setBlocked(across, down, !g.getBlocked(across, down));
            }
        };
        g = new Grid(plotter, plotter.getGridWidth()-1, plotter.getGridHeight()/2);
        
        /*g.setBlocked(1,6,3,6, true);
        g.setBlocked(1,12,3,12, true);
        g.setBlocked(3,7,3,11, true);
        
        g.setBlocked(3,1,6,1, true);
        g.setBlocked(3,17,6,17, true);
        g.setBlocked(6,2,6,16, true);
        //g.setBlocked(3,0, true);*/
        
        
        JFrame frame = new JFrame("Plotter2D2");
        JPanel panel = new JPanel(new BorderLayout());
            panel.add(plotter, BorderLayout.CENTER);
            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    g.clearAll();
                }
            });
            panel.add(clearButton, BorderLayout.NORTH);
            JButton goButton = new JButton("GO");
            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            g.reset();
                            g.searchPath(1, plotter.getGridHeight()/2);
                        }
                    });
                    t.start();
                }
            });
            panel.add(goButton, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        
        //g.searchPath(1,9);
        
    }
    
}

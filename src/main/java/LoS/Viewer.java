/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoS;

import com.superawesomechicken.searchalgorithm.Plotter2D3;
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
    
    private static Grid g;
    
    public static void main(String[] args) {
        Plotter2D3 plotter = new Plotter2D3(9, 9) {
            @Override
            public void run(int across, int down) {
                System.out.println(across+" "+down);
                if(g!=null) g.setBlocked(across, down, !g.getBlocked(across, down));
            }
        };
        g = new Grid(plotter);
        
        JFrame frame = new JFrame("Plotter2D3");
        JPanel panel = new JPanel(new BorderLayout());
            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    g.clearAll();
                }
            });
            panel.add(clearButton, BorderLayout.NORTH);
            panel.add(plotter, BorderLayout.CENTER);
            JButton goButton = new JButton("GO");
            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            g.reset();
                            g.searchPath(0,0,8,8);
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
    }
    
}

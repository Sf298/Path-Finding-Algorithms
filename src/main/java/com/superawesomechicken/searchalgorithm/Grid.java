/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superawesomechicken.searchalgorithm;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Saud
 */
public class Grid {
    
    private Node[][] grid;
    private final int targetI;
    private final int targetJ;
    private Plotter2D3 plotter;
    
    private final Color BLANK_COLOUR = Color.WHITE;
    private final Color END_COLOUR = Color.RED;
    private final Color START_COLOUR = Color.GREEN;
    private final Color SCAN_COLOUR = Color.LIGHT_GRAY;
    private final Color PATH_COLOUR = Color.YELLOW;
    private final Color BLOCK_COLOUR = Color.DARK_GRAY;
    
    public Grid(Plotter2D3 plotter, int width, int height, int targetI, int targetJ) {
        grid = new Node[width][height];
        this.targetI = targetI;
        this.targetJ = targetJ;
        this.plotter = plotter;
        
        if(plotter!=null) {
            plotter.setColour(targetI, targetJ, END_COLOUR);
            plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
        }
        
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                double dist = Math.sqrt(Math.pow(targetI-i, 2) + Math.pow(targetJ-j, 2));
                grid[i][j] = new Node(dist);
            }
        }
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                if(i<grid.length-1)
                    grid[i][j].setNorth(grid[i+1][j]);
                if(i>0)
                    grid[i][j].setSouth(grid[i-1][j]);
                if(j<grid[0].length-1)
                    grid[i][j].setEast(grid[i][j+1]);
                if(j>0)
                    grid[i][j].setWest(grid[i][j-1]);
            }
        }
    }
    public Grid(int width, int height, int targetI, int targetJ) {
        this(null, width, height, targetI, targetJ);
    }
    public Grid(Plotter2D3 plotter, int targetI, int targetJ) {
        this(plotter, plotter.getGridWidth(), plotter.getGridHeight(), targetI, targetJ);
    }
    
    public boolean getBlocked(int posI, int posJ) {
        return grid[posI][posJ].isBlocked();
    }
    
    public Node[] searchPath(int sI, int sJ) {
        int noOfScans = 0;
        if(plotter!=null) {
            plotter.setColour(sI, sJ, START_COLOUR);
            plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
        }
        
        Queue<Node> q = new Queue<>();
        q.push(grid[sI][sJ]/*, grid[sI][sJ].getDistToGoal()*/);
        grid[sI][sJ].setScanned(true);
        grid[sI][sJ].setStepsFromSource(0);
        while(true) {
            Node currN = q.pop();
            noOfScans++;
            
            //System.out.println(" "+Arrays.toString(getPos(currN)));
            if(plotter!=null) {
                int[] arr = getPos(currN);
                plotter.setColour(arr[0], arr[1], SCAN_COLOUR);
                plotter.setText(arr[0], arr[1], currN.getStepsFromSource()+"");
                plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
            }
            
            if(currN == null || currN.equals(grid[targetI][targetJ])) break;
            
            for(Node n : currN.nodeSet()) {
                if(n!=null && !n.isBlocked()) {
                    if(!n.isScanned()/* || currN.getStepsFromSource()+1 < n.getStepsFromSource()*/) {
                        Node temp = n.nodeClosestToSource();
                        n.setStepsFromSource((temp==null)?0:temp.getStepsFromSource()+1);
                        q.push(n/*, Math.pow(n.getDistToGoal(), 8)*n.getStepsFromSource()*/);
                        
                        int[] posArr = getPos(n);
                        if(plotter!=null) plotter.setText(posArr[0], posArr[1], n.getStepsFromSource()+"");
                    }
                    n.setScanned(true);
                }
            }
        }
        Node n = grid[targetI][targetJ];
        n.setStepsFromSource(Integer.MAX_VALUE);
        ArrayList<Node> arr = new ArrayList<>();
        while(true) {
            arr.add(n);
            n = n.nodeClosestToSource();
            if(n.getStepsFromSource() == 0) break;
            
            if(plotter!=null) {
                int[] posArr = getPos(n);
                plotter.setColour(posArr[0], posArr[1], PATH_COLOUR);
                plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
            }
        }
        Node[] out = new Node[arr.size()];
        for(int i=0; i<out.length; i++) {
            out[i] = arr.get(arr.size()-i-1);
        }
        
        if(plotter!=null) {
            plotter.setColour(targetI, targetJ, END_COLOUR);
            plotter.setColour(sI, sJ, START_COLOUR);
            plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
        }
        System.out.println("No of Scans = "+noOfScans);
        return out;
    }
    
    public int[] getPos(Node n) {
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                if(n.equals(grid[i][j])) return new int[] {i, j};
            }
        }
        return null;
    }
    
    public void setBlocked(int posI, int posJ, boolean isBlocked) {
        grid[posI][posJ].setBlocked(isBlocked);
        if(plotter!=null) {
            plotter.setColour(posI, posJ, (isBlocked) ? BLOCK_COLOUR : BLANK_COLOUR);
            plotter.setText(posI, posJ, null);
            plotter.repaint(); try{Thread.sleep(100);}catch(Exception e){}
        }
    }
    
    public void setBlocked(int posI1, int posJ1,  int posI2,int posJ2, boolean isBlocked) {
        for(int i=posI1; i<=posI2; i++) {
            for(int j=posJ1; j<=posJ2; j++) {
                setBlocked(i, j, isBlocked);
            }
        }
    }
    
    public void reset() {
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                grid[i][j].setScanned(false);
                grid[i][j].setStepsFromSource(-1);
                plotter.clear(BLANK_COLOUR, BLOCK_COLOUR);
            }
        }
        plotter.repaint();
    }
    
    public void clearAll() {
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[0].length; j++) {
                grid[i][j].setScanned(false);
                grid[i][j].setBlocked(false);
                grid[i][j].setStepsFromSource(-1);
                plotter.clear(BLANK_COLOUR);
            }
        }
        plotter.repaint();
    }
    
}

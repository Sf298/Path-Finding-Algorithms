/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superawesomechicken.searchalgorithm;

import java.util.ArrayList;

/**
 *
 * @author Saud
 */
public final class Node {
    
    private Node north;
    private Node south;
    private Node east;
    private Node west;
    private double distToGoal;
    private int stepsFromSource = 0;
    private boolean isBlocked = false;
    private boolean isScanned = false;
    
    public Node(Node north, Node south, Node east, Node west, double distToGoal) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.distToGoal = distToGoal;
        this.stepsFromSource = -1;
    }
    
    public Node(double distToGoal) {
        this(null, null, null, null, distToGoal);
    }
    
    public Node nodeClosestToSource() {
        Node out = null;
        for(Node n : nodeSet()) {
            if(out == null || n.getStepsFromSource() < out.getStepsFromSource()) {
                if(n.getStepsFromSource()!=-1) out = n;
            }
        }
        return out;
    }

    public Node getNorth() {
        return north;
    }

    public void setNorth(Node north) {
        this.north = north;
    }

    public Node getSouth() {
        return south;
    }

    public void setSouth(Node south) {
        this.south = south;
    }

    public Node getEast() {
        return east;
    }

    public void setEast(Node east) {
        this.east = east;
    }

    public Node getWest() {
        return west;
    }

    public void setWest(Node west) {
        this.west = west;
    }
    
    public double getDistToGoal() {
        return distToGoal;
    }
    
    public int getStepsFromSource() {
        return stepsFromSource;
    }
    
    public void setStepsFromSource(int steps) {
        stepsFromSource = steps;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean b) {
        this.isBlocked = b;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean b) {
        this.isScanned = b;
    }
    
    public ArrayList<Node> getNodes() {
        ArrayList<Node> out = new ArrayList<>();
        out.add(north);
        out.add(south);
        out.add(east);
        out.add(west);
        return out;
    }
    
    public ArrayList<Node> nodeSet() {
        ArrayList<Node> out = new ArrayList<>();
        if(north!=null) out.add(north);
        if(east!=null) out.add(east);
        if(south!=null) out.add(south);
        if(west!=null) out.add(west);
        return out;
    }
    
    @Override
    public boolean equals(Object o) {
        return this == o;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoS;

import java.util.ArrayList;

/**
 *
 * @author Saud
 */
public final class Node {
    
    private double distToGoal = -1;
    private int stepsFromSource = -1;
    private boolean isBlocked = false;
    private boolean isScanned = false;
    private int xPos;
    private int yPos;
    
    public Node(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getx() {
        return xPos;
    }

    public int gety() {
        return yPos;
    }
    
    public double getDistToGoal() {
        return distToGoal;
    }
    
    public void setDistToGoal(double dist) {
        distToGoal = dist;
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
    
    @Override
    public boolean equals(Object o) {
        return this == o;
    }
    
}

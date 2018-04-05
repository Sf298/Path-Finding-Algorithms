/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoS;

/**
 *
 * @author Saud
 */
public class Tester {
    
    public static void main(String[] args) {
        Grid g = new Grid(3,3);
        g.setBlocked(2,0,2,1, true);
        System.out.println(g.isOnCorner(g.grid[1][2]));
    }
    
}

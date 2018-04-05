/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superawesomechicken.searchalgorithm;

/**
 *
 * @author Saud
 */
public class SortedQueueTester {
    
    public static void main(String[] args) {
        SortedQueue<String> q = new SortedQueue<>();
        q.push("2", 2);
        q.push("1", 1);
        System.out.println(q.pop());
        q.push("3", 3);
        System.out.println(q.pop());
        System.out.println(q.pop());
        System.out.println(q.pop());
    }
    
}

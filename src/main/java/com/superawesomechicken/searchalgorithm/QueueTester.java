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
public class QueueTester {
    
    public static void main(String[] args) {
        Queue<String> q = new Queue<>();
        q.push("1");
        q.push("2");
        System.out.println(q.pop());
        q.push("3");
        System.out.println(q.pop());
        System.out.println(q.pop());
        System.out.println(q.pop());
    }
    
}

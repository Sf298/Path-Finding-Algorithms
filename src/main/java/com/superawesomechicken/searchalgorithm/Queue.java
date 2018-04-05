/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superawesomechicken.searchalgorithm;

/**
 *
 * @author eeu485 (Saud Fatayerji)
 */
public class Queue<T> {
    
    private Node firstNode=null;
    private Node lastNode=null;
    
    public void push(T t) {
        Node n = new Node(t, lastNode, null);
        if(lastNode == null) {
            firstNode = n;
            lastNode = n;
        } else {
            lastNode.next = n;
            n.prev = lastNode;
            lastNode = lastNode.next;
        }
    }
    
    public T pop() {
        Node node1 = firstNode;
        if(firstNode != null) {
            Node node2 = firstNode.next;
            firstNode = node2;
            if(node2 != null) {
                node2.prev = null;
            } else {
                lastNode = null;
            }
            node1.next = null;
            return node1.value;
        }
        return null;
    }
    public T getFirstElement() {
        if(firstNode == null) return null;
        return firstNode.value;
    }
    
    public boolean isEmpty() {
        return lastNode == null;
    }
    
    /*public T[] toArray() {
        Object[] out = new Object[elementCount];
        for(int i=0; i<elementCount; i++) {
            out[i] = arr[(i+firstElement)%arr.length];
        }
        return (T[]) out;
    }*/
    
    public void printArray() {
        Node n = firstNode;
        while(n!=null) {
            System.out.println(n);
            n = n.next;
        }
    }
    
    /*@Override
    public boolean equals(Object o) {
        if(!(o instanceof Queue)) return false;
        Queue other = (Queue) o;
        
        Object[] otherArray = other.toArray();
        T[] thisArray = this.toArray();
        
        if(thisArray.length != otherArray.length)
            return false;
        
        for(int i=0; i < thisArray.length; i++) {
            if(!thisArray[i].equals(otherArray[i]))
                return false;
        }
        
        return true;
    }*/
    
    private class Node {
        public Node prev;
        public Node next;
        public T value;
        public Node(T value, Node prev, Node next) {
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
        public Node(T value) {
            this(value, null, null);
        }
    }
    
}

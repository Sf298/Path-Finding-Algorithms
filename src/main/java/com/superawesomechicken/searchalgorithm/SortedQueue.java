/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superawesomechicken.searchalgorithm;

/**
 *
 * @author eeu485 (Saud Fatayerji)
 * @param <T>
 */
public class SortedQueue<T> {
    
    private Node firstNode=null;
    private Node lastNode=null;
    
    public void push(T t, double weight) {
        Node n = new Node(t, lastNode, null, weight);
        if(lastNode == null) {
            firstNode = n;
            lastNode = n;
        } else {
            Node itera = firstNode;
            while(itera != null && itera.weight < weight) {
                itera = itera.next;
            }
            if(itera != null) {
                n.next = itera;
                n.prev = itera.prev;
                if(n.next != null) n.next.prev = n;
                if(n.prev != null) n.prev.next = n;
                else firstNode = n;
                
            } else {
                lastNode.next = n;
                n.prev = lastNode;
                lastNode = n;
            }
        }
    }
    public void pushToFront(T t) {
        push(t, -1);
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
        public double weight;
        public Node(T value, Node prev, Node next, double weight) {
            this.next = next;
            this.prev = prev;
            this.value = value;
            this.weight = weight;
        }
        public Node(T value) {
            this(value, null, null, -1);
        }
    }
    
}

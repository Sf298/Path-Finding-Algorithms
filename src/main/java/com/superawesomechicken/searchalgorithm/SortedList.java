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
public class SortedList<T> {
    ArrayList<T> list = new ArrayList<>();
    ArrayList<Double> values = new ArrayList<>();
    
    public void add(T t, double value) {
        for(int i=0; i<list.size(); i++) {
            if(value>=values.get(i)) {
                list.add(i+1, t);
                values.add(i+1, value);
                return;
            }
        }
        list.add(t);
        values.add(value);
    }
    
    public ArrayList<T> getArray() {
        return list;
    }
    
    public T get(int index) {
        return list.get(index);
    }
    
    public int size() {
        return list.size();
    }
    
    /*private class Container<T> {
        public double value;
        public T data;
        
        public Container(T data, double value) {
            this.data = data;
            this.value = value;
        }
    }*/
}

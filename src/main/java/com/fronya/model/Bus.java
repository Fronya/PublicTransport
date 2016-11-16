package com.fronya.model;


public class Bus {
    private int id;
    private int capacity;
    private volatile int currentCountPassenger;

    public Bus(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        currentCountPassenger = 0;
    }

    public void addPassenger(){
        currentCountPassenger++;
    }

    public void removePassenger(){
        currentCountPassenger--;
    }

    public boolean isFill(){
        return currentCountPassenger >= capacity;
    }

    public int getId() {
        return id;
    }
}

package com.fronya.model;


import com.fronya.constants.Constant;

import java.util.Random;

public class Passenger {
    private int id;
    private int countStop;
    private int numStartStop;
    private int numFinalStop;
    private boolean inBus = false;

    public Passenger(int id, int numStartStop) {
        this.id = id;
        this.numStartStop = numStartStop;
        countStop = generateCountStop();

        numFinalStop = numStartStop + countStop;
        if(numFinalStop >= Constant.COUNT_STOP){
            numFinalStop -= Constant.COUNT_STOP;
        }
    }

    private int generateCountStop(){
        Random random = new Random();
        return random.nextInt(Constant.COUNT_STOP - 2) + 1;
    }

    public int getId() {
        return id;
    }

    public int getNumStartStop() {
        return numStartStop;
    }

    public int getNumFinalStop() {
        return numFinalStop;
    }

    public void goOneStop(){
        countStop--;
    }

    public boolean isEnd(){
        return countStop <= 0;
    }

    public void inBus(){
        inBus = true;
    }

    public boolean isInBus(){
        return inBus;
    }
}

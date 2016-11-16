package com.fronya.runner;


import com.fronya.constants.Constant;
import com.fronya.threads.BusThread;
import com.fronya.threads.PassengerThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Runner {
    public static void main(String[] args) {
        List<PassengerThread> passengers = new ArrayList<>();

        Random random = new Random();
        for (int numPassenger = 0; numPassenger < Constant.COUNT_PASSENGER; numPassenger++) {
            int numStop = random.nextInt(Constant.COUNT_STOP - 1);
            passengers.add(new PassengerThread( numPassenger, numStop));
        }

        for (PassengerThread p: passengers) {
            p.start();
        }

        for (int numBus = 0; numBus < Constant.COUNT_BUS; numBus++) {
            new BusThread(numBus, Constant.CAPACITY_BUS).start();
            try {
                Thread.sleep(Constant.INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (PassengerThread p: passengers) {
            try {
                p.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

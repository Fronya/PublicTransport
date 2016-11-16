package com.fronya.threads;


import com.fronya.constants.Constant;
import com.fronya.model.Bus;
import com.fronya.model.Road;
import com.fronya.model.Stop;
import org.apache.log4j.Logger;

public class BusThread extends Thread {
    private static final Logger log = Logger.getLogger(BusThread.class);

    private Bus bus;
    private Object passengerLock = new Object();

    public BusThread(int id, int capacity) {
        setDaemon(true);
        bus = new Bus(id, capacity);
    }

    public Object addPassenger(){
        log.info("В автобус " + bus.getId() + " зашел пассажир");
        bus.addPassenger();
        return passengerLock;
    }

    public void removePassenger(){
        log.info("Из автобуса " + bus.getId() + " вышел пассажир");
        bus.removePassenger();
    }

    /**
     * Оповещения пассажиров об остановке
     */
    public void notifyPassengers(){
        synchronized (passengerLock){
            passengerLock.notifyAll();
        }
    }

    public boolean isFill(){
        return bus.isFill();
    }

    @Override
    public void run() {
        int idStop = 0;
        while (true){
            if(idStop >= Constant.COUNT_STOP){
                idStop = 0;
            }

            Stop currentStop = Road.getStop(idStop);
            Object lockObject = currentStop.registerBus();
            //Ожидание освобождения остановки
            synchronized (lockObject) {
                while (!currentStop.isCurrentBus(this)) {
                    try {
                        lockObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            log.info("Автобус " + bus.getId() + " подъехал к остановке " + currentStop.getId());
            notifyPassengers();
            currentStop.notifyPassenger();
            synchronized (lockObject) {
                try {
                    lockObject.wait(Constant.TIME_SLEEP);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            currentStop.removeBus();
            currentStop.notifyBus();
            idStop++;
        }
    }
}

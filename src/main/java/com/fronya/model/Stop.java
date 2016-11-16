package com.fronya.model;


import com.fronya.model.state.StopState;
import com.fronya.threads.BusThread;
import org.apache.log4j.Logger;

public class Stop {
    private static final Logger log = Logger.getLogger(Stop.class);

    private int id;
    private volatile StopState state = StopState.EMPTY;

    private final Object busLock = new Object();
    private final Object passengerLock = new Object();

    private volatile int countDispatchedPassengers = 0;
    private volatile int countArrivedPassengers = 0;

    private BusThread currentBus;

    public Stop(int id) {
        this.id = id;
    }

    /**
     * Автобус пытается подехать на остановку
     * @return
     */
    public Object registerBus(){
        return busLock;
    }

    /**
     * Пассажир пришел на остановку
     * @return объект синхронизации для пассажиров
     */
    public Object registerPassenger(){
        countDispatchedPassengers++;
        return passengerLock;
    }

    /**
     * Пассадить пассажира в автобус
     * @return объект синхронизации автобуса для пассажиров
     */
    public Object goToBus(){
        synchronized (passengerLock) {
            if (currentBus != null && !currentBus.isFill()) {
                Object lockObject;
                countDispatchedPassengers--;
                lockObject = currentBus.addPassenger();
                //notifyBus();
                return lockObject;
            }
            return null;
        }
    }

    /**
     * Пассажир вышел на остановке
     */
    public void passengerEnd(){
        if(currentBus != null){
           // log.info("Пассажир вышел на остановке");
            countArrivedPassengers++;
            currentBus.removePassenger();
        }else{
            log.info("Попытка выйти из несуществующего автобуса");
        }
    }

    /**
     * Оповестить пассажиров о приезде автобуса
     */
    public void notifyPassenger(){
        synchronized (passengerLock){
            passengerLock.notifyAll();
        }
    }

    /**
     * Оповестить автобусы об отъезде текущего автобуса
     */
    public void notifyBus(){
        synchronized (busLock){
            busLock.notifyAll();
        }
    }

    /**
     * Является ли текущий автобус единственным на остановке
     * @param bus
     * @return
     */
    public boolean isCurrentBus(BusThread bus){
        if(state == StopState.EMPTY){
            state = StopState.BUS_ARRIVED;
            currentBus = bus;
            return true;
        }
        return false;
    }

    public void removeBus(){
        notifyBus();
        state = StopState.EMPTY;
    }

    public StopState getState() {
        return state;
    }

    public int getId() {
        return id;
    }
}

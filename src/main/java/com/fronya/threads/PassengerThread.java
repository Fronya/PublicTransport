package com.fronya.threads;


import com.fronya.model.Passenger;
import com.fronya.model.Road;
import com.fronya.model.Stop;
import com.fronya.model.state.StopState;
import org.apache.log4j.Logger;

public class PassengerThread extends Thread {
    private static final Logger log = Logger.getLogger(PassengerThread.class);

    private Passenger passenger;

    private Object lockStop;

    public PassengerThread(int id, int numStartStop) {
        passenger = new Passenger(id, numStartStop);
        lockStop = Road.getStop(numStartStop).registerPassenger();
    }

    @Override
    public void run() {
        Object busLock = null;

        while (!passenger.isInBus()) {
            synchronized (lockStop) {
                int numStartStop = passenger.getNumStartStop();
                Stop startStop = Road.getStop(numStartStop);
                while (startStop.getState() == StopState.EMPTY) {
                    try {
                        lockStop.wait();
                        //log.info("Пассажир " + passenger.getId() + " ожидает");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                busLock = Road.getStop(numStartStop).goToBus();
                if(busLock != null){
                    passenger.inBus();
                    log.info("Пассажир " + passenger.getId() + " сел в автобус");
                    startStop.removeBus();
                }
            }
        }

        while(!passenger.isEnd()){
            synchronized (busLock){
                try {
                    passenger.goOneStop();
                    busLock.wait();
                    //log.info("Пассажир " + passenger.getId() + " проехал остановку");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        int numFinishStop = passenger.getNumFinalStop();
        Road.getStop(numFinishStop).passengerEnd();
        log.info("Пассажир " + passenger.getId() + " закончил путь");
    }
}

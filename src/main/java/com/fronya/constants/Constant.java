package com.fronya.constants;


import java.util.ResourceBundle;

public class Constant {
    private static final String PATH = "application";
    private static final int TIME = 50;

    public static int COUNT_BUS;
    public static int COUNT_PASSENGER;
    public static int COUNT_STOP;
    public static int TIME_SLEEP;
    public static int CAPACITY_BUS;
    public static int INTERVAL;

    static {
        ResourceBundle resource = ResourceBundle.getBundle(PATH);
        COUNT_BUS = Integer.valueOf(resource.getString("count_bus"));
        COUNT_PASSENGER = Integer.valueOf(resource.getString("count_passenger"));
        TIME_SLEEP = Integer.valueOf(resource.getString("speed")) * TIME;
        CAPACITY_BUS = Integer.valueOf(resource.getString("capacity_bus"));
        INTERVAL = Integer.valueOf(resource.getString("interval"));
        //умножение остановок на 2, тк остановки парные
        COUNT_STOP = Integer.valueOf(resource.getString("count_stop")) * 2;
    }
}

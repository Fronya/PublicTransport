package com.fronya.model;


import com.fronya.constants.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс дороги
 */
public class Road {
    private static List<Stop> stopList = new ArrayList<>();

    static {
        for (int numStop = 0; numStop < Constant.COUNT_STOP; numStop++) {
            stopList.add(new Stop(numStop));
        }
    }

    /**
     * Получение остановки по ее номеру
     * @param id номер остановки
     * @return остановка
     */
    public static Stop getStop(int id){
        return stopList.get(id);
    }
}

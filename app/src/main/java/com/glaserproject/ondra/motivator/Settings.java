package com.glaserproject.ondra.motivator;

import java.io.Serializable;

/**
 * Created by ondra on 2/27/2016.
 */
public class Settings extends MainActivity implements Serializable{
    public static long afterAlarmTime = 20000;
    public static boolean alarmSwitch, randomSwitch;
    public static long randomLow = 10800000;  //3h
    public static long randomHigh = 18000000; //5h


}

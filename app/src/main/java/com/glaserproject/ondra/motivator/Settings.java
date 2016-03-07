package com.glaserproject.ondra.motivator;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * Created by ondra on 2/27/2016.
 */
public class Settings extends MainActivity implements Serializable{
    public static long afterAlarmTime = 20000;
    public static int afterAlarmTimeInt = 1;
    public static boolean mainSwitch, alarmSwitch, pictureSwitch, randomSwitch;
    public static long randomLow = 10800000;  //3h
    public static long randomHigh = 18000000; //5h
    public static int randomInt = 2;
    public static String pictureUrl;
    public static int pictureUrlInt;


    SharedPreferences sharedPreferences;

    public void loadFromSaved(Context context){
        sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        afterAlarmTime = sharedPreferences.getLong("AfterAlarmTime", 20000);
        afterAlarmTimeInt = sharedPreferences.getInt("AfterAlarmTimeInt", 1);

        randomLow = sharedPreferences.getLong("RandomLow", 10800000);
        randomHigh = sharedPreferences.getLong("RandomHigh", 18000000);
        randomInt = sharedPreferences.getInt("RandomInt", 2);

        mainSwitch = sharedPreferences.getBoolean("MainSwitch", false);
        alarmSwitch = sharedPreferences.getBoolean("AlarmSwitch", false);
        pictureSwitch = sharedPreferences.getBoolean("PictureSwitch", false);
        randomSwitch = sharedPreferences.getBoolean("RandomSwitch", false);

        pictureUrl = sharedPreferences.getString("PictureUrl", "");
        pictureUrlInt = sharedPreferences.getInt("PictureUrlInt", 0);
    }


}

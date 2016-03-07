package com.glaserproject.ondra.motivator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmCheck extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        AlarmManager manager;
        Settings settings;
        settings = new Settings();
        settings.loadFromSaved(context);
        AlarmManager.AlarmClockInfo info =
                ((AlarmManager)context.getSystemService(Context.ALARM_SERVICE)).getNextAlarmClock();
        if (info != null) {
            long alarmTime = info.getTriggerTime();
            PendingIntent pendingIntent = null;
            //set up alarm and waiting intent
            Intent alarmIntent;

            if (settings.pictureSwitch) {
                alarmIntent = new Intent(context.getApplicationContext(), ShowNotifPicture.class);
            } else {
                alarmIntent = new Intent(context.getApplicationContext(), ShowNotif.class);
            }
            pendingIntent = pendingIntent.getBroadcast(context.getApplicationContext(), 0, alarmIntent, 0);

            manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, alarmTime+settings.afterAlarmTime, pendingIntent); //15 secs after alarm
        }
    }
}

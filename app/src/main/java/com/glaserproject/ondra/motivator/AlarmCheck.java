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

        AlarmManager.AlarmClockInfo info =
                ((AlarmManager)context.getSystemService(Context.ALARM_SERVICE)).getNextAlarmClock();
        if (info != null) {
            long alarmTime = info.getTriggerTime();
            PendingIntent pendingIntent = null;
            //set up alarm and waiting intent
            Intent alarmIntent = new Intent(context.getApplicationContext(), ShowNotif.class);
            pendingIntent = pendingIntent.getBroadcast(context.getApplicationContext(), 0, alarmIntent, 0);

            manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, alarmTime+15000, pendingIntent); //15 secs after alarm
        }
    }
}

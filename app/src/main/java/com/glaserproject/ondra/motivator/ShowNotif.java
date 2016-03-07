package com.glaserproject.ondra.motivator;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by ondra on 2/18/2016.
 */
public class ShowNotif extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String quote = selectNewQuote(context);
        final String EXTRAq = "extraQ";


        //Intent to send user to MainActivity
        Intent resultIntent = new Intent(context, NotificationReceivedActivity.class);
        resultIntent.putExtra(EXTRAq, quote);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Notification.FLAG_AUTO_CANCEL);


        //pending intent sending to MainActivity.java
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        //Build nofification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder
                .setSmallIcon(R.drawable.ic_stat_notif)
                .setContentTitle(context.getString(R.string.notificationTitle))
                .setContentText(context.getString(R.string.notificationText))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle(mBuilder).bigText(quote).setBigContentTitle(context.getString(R.string.notificationTitle)).setSummaryText(context.getString(R.string.NotificationSummary)))
        ;
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(resultPendingIntent);

        int mNotificationId = 001;

        //send notification
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());

        boolean sendRandom = intent.getBooleanExtra("RND", false);

        if (sendRandom){
            Random generator = new Random();
            Settings settings = new Settings();
            settings.loadFromSaved(context);
            long LOW = settings.randomLow;
            long HIGH = settings.randomHigh;

            long rndr =  LOW + (long)(generator.nextDouble()*(HIGH - LOW));

            sendRandom = true;
            PendingIntent pendingIntent = null;
            //set up alarm and waiting intent
            Intent alarmIntent = new Intent(context.getApplicationContext(), ShowNotif.class);
            alarmIntent.putExtra("RND", sendRandom);
            pendingIntent = pendingIntent.getBroadcast(context.getApplicationContext(), 1, alarmIntent, 0);

            Calendar calendar = Calendar.getInstance();

            AlarmManager manager;
            manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + rndr, pendingIntent);


        }


    }


    public String selectNewQuote (Context context){

        String[] quoteString = context.getResources().getStringArray(R.array.quotes);
        int quotePosition = new Random().nextInt(quoteString.length);

        String challengeStringRnd;

        challengeStringRnd = quoteString[quotePosition];

    return challengeStringRnd;
    }


}

package com.glaserproject.ondra.motivator;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Switch mainSwitch;
    Switch alarmSwitch;
    Switch pictureSwitch;
    Switch randomSwitch;
    Button pictureMore;
    Button alarmMore;
    Button randomMore;

    boolean mainSwitchState, alarmSwitchState, pictureSwitchState, randomSwitchState;

    FloatingActionButton fab;

    int pendingIntentAlarm = 0;
    int pendingIntentRnd = 1;

    boolean sendRandom;

    private PendingIntent pendingIntent;
    private AlarmManager manager;

    SharedPreferences sharedPreferences;

    LinearLayout cardPictureLayoutSettings;
    LinearLayout cardAlarmLayoutSettings;
    LinearLayout cardRandomLayoutSettings;
    boolean pictureCardClicked, alarmCardClicked, randomCardClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainSwitch = (Switch) findViewById(R.id.mainSwitch);
        alarmSwitch = (Switch) findViewById(R.id.alarmSwitch);
        pictureSwitch = (Switch) findViewById(R.id.pictureSwitch);
        randomSwitch = (Switch) findViewById(R.id.randomSwitch);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        pictureMore = (Button) findViewById(R.id.pictureMore);
        alarmMore = (Button) findViewById(R.id.alarmMore);
        randomMore = (Button) findViewById(R.id.randomMore);

        cardPictureLayoutSettings = (LinearLayout) findViewById(R.id.cardPictureLayoutSettings);
        cardPictureLayoutSettings.setVisibility(View.GONE);
        cardAlarmLayoutSettings = (LinearLayout) findViewById(R.id.cardAlarmLayoutSettings);
        cardAlarmLayoutSettings.setVisibility(View.GONE);
        cardRandomLayoutSettings = (LinearLayout) findViewById(R.id.cardRandomLayoutSettings);
        cardRandomLayoutSettings.setVisibility(View.GONE);

        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("MAINstate")) {
                mainSwitch.setChecked(true);
                mainSwitchState = true;
            }
            if (savedInstanceState.getBoolean("ALARMstate")) {
                alarmSwitch.setChecked(true);
                alarmSwitchState = true;
            }
            if (savedInstanceState.getBoolean("PICTUREstate")) {
                pictureSwitch.setChecked(true);
                pictureSwitchState = true;
            }
            if (savedInstanceState.getBoolean("RANDOMstate")) {
                randomSwitch.setChecked(true);
                randomSwitchState = true;
            }
        }

        sharedPreferences = getSharedPreferences("SwitchStates", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean("MAINstate", false)) {
            mainSwitch.setChecked(true);
            mainSwitchState = true;
        }
        if (sharedPreferences.getBoolean("ALARMstate", false)) {
            alarmSwitch.setChecked(true);
            alarmSwitchState = true;
        }
        if (sharedPreferences.getBoolean("PICTUREstate", false)) {
            pictureSwitch.setChecked(true);
            pictureSwitchState = true;
        }
        if (sharedPreferences.getBoolean("RANDOMstate", false)) {
            randomSwitch.setChecked(true);
            randomSwitchState = true;
        }


        mainSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mainSwitchState = isChecked;
                if (isChecked) {
                    turnOnMain();
                } else {
                    turnOffMain();
                }
                triggerChange();
                editor.putBoolean("MAINstate", isChecked);
                editor.commit();
            }
        });
        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alarmSwitchState = isChecked;
                triggerChange();
                if (mainSwitchState) {
                    if (isChecked) {
                        showNotifOnAlarm();
                    } else {
                        cancelAlarmNotif();
                    }
                }
                editor.putBoolean("ALARMstate", isChecked);
                editor.commit();
            }
        });

        pictureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pictureSwitchState = isChecked;
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Not available at this time", Toast.LENGTH_SHORT).show();
                    pictureSwitch.performClick();
                    pictureSwitchState = false;
                }
            }
        });

        randomSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                randomSwitchState = isChecked;
                triggerChange();
                if (mainSwitchState) {
                    if (isChecked) {
                        showNotifRnd();
                    } else {
                        cancelRndNotif();
                    }
                }
                editor.putBoolean("RANDOMstate", isChecked);
                editor.commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotifNow();
            }
        });


        pictureMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View cardView = (View) findViewById(R.id.sceneRoot);
                if (!pictureCardClicked){
                    ClickButton.pictureButtonSettings(cardView);
                    pictureCardClicked = true;
                } else{
                    ClickButton.pictureButtonDefault(cardView);
                    pictureCardClicked = false;
                }
            }
        });

        alarmMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View cardView = (View) findViewById(R.id.cardAlarm);
                if (!alarmCardClicked){
                    ClickButton.alarmButtonSettings(cardView);
                    alarmCardClicked = true;
                } else{
                    ClickButton.alarmButtonDefault(cardView);
                    alarmCardClicked = false;
                }
            }
        });
        randomMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View cardView = (View) findViewById(R.id.cardRandom);
                if (!randomCardClicked){
                    ClickButton.randomButtonSettings(cardView);
                    randomCardClicked = true;
                } else{
                    ClickButton.randomButtonDefault(cardView);
                    randomCardClicked = false;
                }
            }
        });
    }

    public void showNotifOnAlarm(){
        Intent alarmIntent = new Intent(this, AlarmCheck.class);
        pendingIntent = pendingIntent.getBroadcast(this, pendingIntentAlarm, alarmIntent, 0);
        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, AlarmManager.INTERVAL_HOUR, AlarmManager.INTERVAL_HOUR, pendingIntent);


        //set waiting intent if alarm is set now

        AlarmManager.AlarmClockInfo info =
                ((AlarmManager)getSystemService(ALARM_SERVICE)).getNextAlarmClock();
        if (info != null) {
            long alarmTime = info.getTriggerTime();
            PendingIntent pendingIntentNew;
            //set up alarm and waiting intent
            Intent alarmIntentNow = new Intent(this, ShowNotif.class);
            pendingIntentNew = pendingIntent.getBroadcast(this, pendingIntentAlarm, alarmIntentNow, 0);

            manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, alarmTime+15000, pendingIntentNew); //15 secs after alarm
        }
    }

    public void cancelAlarmNotif(){
        Intent intentStop = new Intent(this, AlarmCheck.class);
        PendingIntent senderStop = pendingIntent.getBroadcast(this, pendingIntentAlarm, intentStop, 0);
        AlarmManager alarmManagerStop = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManagerStop.cancel(senderStop);
    }

    public void showNotifRnd(){
        Random generator = new Random();
                            //10800000 = 3h, 21600000 = 6h
        long LOW = 10800000;
        long HIGH = 21600000;

        long rndr =  LOW + (long)(generator.nextDouble()*(HIGH - LOW));

        sendRandom = true;
        PendingIntent pendingIntent = null;
        //set up alarm and waiting intent
        Intent alarmIntent = new Intent(this, ShowNotif.class);
        alarmIntent.putExtra("RND", sendRandom);
        pendingIntent = pendingIntent.getBroadcast(this, pendingIntentRnd, alarmIntent, 0);

        Calendar calendar = Calendar.getInstance();

        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + rndr, pendingIntent);


    }

    public void cancelRndNotif(){
        Intent intentStop = new Intent(this, ShowNotif.class);
        PendingIntent senderStop = PendingIntent.getBroadcast(this, pendingIntentRnd, intentStop, 0);
        AlarmManager alarmManagerStop = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManagerStop.cancel(senderStop);
    }

    public void turnOnMain(){
        if (alarmSwitchState){
            showNotifOnAlarm();
        }
        if (randomSwitchState){
            showNotifRnd();
        }
    }

    public void turnOffMain(){
        cancelAlarmNotif();
        cancelRndNotif();
    }


    public void showNotifNow(){
        //Intent to send user to MainActivity
        String quote = selectNewQuote(this);
        final String EXTRAq = "extraQ";

        Intent resultIntent = new Intent(this, NotificationReceivedActivity.class);
        resultIntent.putExtra(EXTRAq, quote);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Notification.FLAG_AUTO_CANCEL);


        //pending intent sending to MainActivity.java
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        //Build nofification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder
                .setSmallIcon(R.drawable.ic_stat_notif)
                .setContentTitle("Motivator")
                .setContentText("You received a new quote")
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle(mBuilder).bigText(quote).setBigContentTitle("Motivator").setSummaryText("Get motivated!"))
        ;
        mBuilder.setContentIntent(resultPendingIntent);

        int mNotificationId = 001;

        //send notification
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
    public String selectNewQuote (Context context){
        String[] quoteString = context.getResources().getStringArray(R.array.quotes);
        int quotePosition = new Random().nextInt(quoteString.length);
        String challengeStringRnd;
        challengeStringRnd = quoteString[quotePosition];
        return challengeStringRnd;
    }



    public void triggerChange (){
        if (mainSwitchState) {

            if (alarmSwitchState && !pictureSwitchState && !randomSwitchState){
                //summaryText.setText("Notifications only after alarm");
                showNotifOnAlarm();
            } else if (alarmSwitchState && pictureSwitchState && !randomSwitchState){
                //summaryText.setText("Notifications in pics only after alarm");
            } else if (alarmSwitchState && pictureSwitchState && randomSwitchState){
                //summaryText.setText("Notifications in pics after alarm and at random times");
            } else if (alarmSwitchState && !pictureSwitchState && randomSwitchState){
                //summaryText.setText("Notifications after alarm and at random times");
            } else if (!alarmSwitchState && !pictureSwitchState && !randomSwitchState){
                //summaryText.setText("No notifications");
            } else if (!alarmSwitchState && pictureSwitchState && !randomSwitchState){
                //summaryText.setText("No notifications (picture selected)");
            } else if (!alarmSwitchState && pictureSwitchState && randomSwitchState){
                //summaryText.setText("Notifications in pics at random times");
            } else if (!alarmSwitchState && !pictureSwitchState && randomSwitchState) {
                //summaryText.setText("Notifications at random times");
            }
        } else {
            //summaryText.setText("Motivator is not turned on!");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("MAINstate", mainSwitchState);
        outState.putBoolean("ALARMstate", alarmSwitchState);
        outState.putBoolean("PICTUREstate", pictureSwitchState);
        outState.putBoolean("RANDOMstate", randomSwitchState);
    }
}

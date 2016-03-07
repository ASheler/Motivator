package com.glaserproject.ondra.motivator;

import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Switch mainSwitch;
    static Switch alarmSwitch;
    Switch pictureSwitch;
    Switch randomSwitch;


    boolean animEnter;



    static boolean mainSwitchState, alarmSwitchState, pictureSwitchState, randomSwitchState;

    FloatingActionButton fab;

    int pendingIntentAlarm = 0;
    int pendingIntentRnd = 1;

    boolean sendRandom;

    private PendingIntent pendingIntent;
    private AlarmManager manager;

    SharedPreferences settingsPreferences;

    RelativeLayout alarmSettingsButton;
    RelativeLayout pictureSettingsButton;
    RelativeLayout randomSettingsButton;
    FrameLayout alarmMoreLayout;
    FrameLayout pictureMoreLayout;
    FrameLayout randomMoreLayout;
    Button pictureMore;
    Button alarmMore;
    Button randomMore;

    LinearLayout cardPictureLayoutSettings;
    LinearLayout cardAlarmLayoutSettings;
    LinearLayout cardRandomLayoutSettings;
    boolean pictureCardClicked, alarmCardClicked, randomCardClicked;

    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        settings = new Settings();

        mainSwitch = (Switch) findViewById(R.id.mainSwitch);
        alarmSwitch = (Switch) findViewById(R.id.alarmSwitch);
        pictureSwitch = (Switch) findViewById(R.id.pictureSwitch);
        randomSwitch = (Switch) findViewById(R.id.randomSwitch);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        cardPictureLayoutSettings = (LinearLayout) findViewById(R.id.cardPictureLayoutSettings);
        cardPictureLayoutSettings.setVisibility(View.GONE);
        cardAlarmLayoutSettings = (LinearLayout) findViewById(R.id.cardAlarmLayoutSettings);
        cardAlarmLayoutSettings.setVisibility(View.GONE);
        cardRandomLayoutSettings = (LinearLayout) findViewById(R.id.cardRandomLayoutSettings);
        cardRandomLayoutSettings.setVisibility(View.GONE);

        alarmSettingsButton = (RelativeLayout) findViewById(R.id.alarmSettingsClickable);
        pictureSettingsButton = (RelativeLayout) findViewById(R.id.pictureSettingsClickable);
        randomSettingsButton = (RelativeLayout) findViewById(R.id.randomSettingsClickable);

        alarmMoreLayout = (FrameLayout) findViewById(R.id.alarmMoreLayout);
        pictureMoreLayout = (FrameLayout) findViewById(R.id.pictureMoreLayout);
        randomMoreLayout = (FrameLayout) findViewById(R.id.randomMoreLayout);

        alarmMore = (Button) findViewById(R.id.alarmMore);
        pictureMore = (Button) findViewById(R.id.pictureMore);
        randomMore = (Button) findViewById(R.id.randomMore);

        //load data
        settingsPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor settingsEditor = settingsPreferences.edit();
        //settings.loadFromSaved(getApplicationContext());
        buildUI();
//
//        sharedPreferences = getSharedPreferences("SwitchStates", Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = sharedPreferences.edit();

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
                settingsEditor.putBoolean("MainSwitch", isChecked);
                settingsEditor.commit();

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
                settingsEditor.putBoolean("AlarmSwitch", isChecked);
                settingsEditor.commit();
            }
        });

        pictureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.pictureSwitch = isChecked;
                if (isChecked) {

                    if (mainSwitchState) {
                        if (randomSwitchState){showNotifRnd();}
                        if (alarmSwitchState) {showNotifOnAlarm();}
                    }

                }
                settingsEditor.putBoolean("PictureSwitch", isChecked);
                settingsEditor.commit();
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
                settingsEditor.putBoolean("RandomSwitch", isChecked);
                settingsEditor.commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settings.pictureSwitch){
                    showNotifNowPicture();
                } else {
                    showNotifNow();
                }
            }
        });


        pictureMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View cardView = (View) findViewById(R.id.cardPicture);
                if (!pictureCardClicked){
                    ClickButton.pictureButtonSettings(cardView);
                    pictureCardClicked = true;
                } else{
                    ClickButton.pictureButtonDefault(cardView);
                    pictureCardClicked = false;
                }
            }
        });
        pictureMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View cardView = (View) findViewById(R.id.cardPicture);
                if (!pictureCardClicked){
                    ClickButton.pictureButtonSettings(cardView);
                    pictureCardClicked = true;
                } else{
                    ClickButton.pictureButtonDefault(cardView);
                    pictureCardClicked = false;
                }
            }
        });

        alarmMoreLayout.setOnClickListener(new View.OnClickListener() {
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

        randomMoreLayout.setOnClickListener(new View.OnClickListener() {
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

        alarmSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmSettings(findViewById(R.id.cardAlarm));
            }
        });
        pictureSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSettings(findViewById(R.id.cardPicture));
            }
        });
        randomSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RandomSettings(findViewById(R.id.cardRandom));
            }
        });


    }


    public void buildUI(){
        settings.loadFromSaved(getApplicationContext());
        mainSwitch.setChecked(settings.mainSwitch);
        alarmSwitch.setChecked(settings.alarmSwitch);
        pictureSwitch.setChecked(settings.pictureSwitch);
        randomSwitch.setChecked(settings.randomSwitch);
    }







    public void AlarmSettings(View v){
        Intent intent = new Intent(this, AlarmSettingsActivity.class);
        Pair<View, String> pair1 = Pair.create(v.findViewById(R.id.alarmSwitch), "AlarmSwitch");
        Pair<View, String> pair2 = Pair.create(v.findViewById(R.id.alarmText), "AlarmText");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2);
        startActivity(intent, options.toBundle());
    }

    public void RandomSettings(View v){
        Intent intent = new Intent(this, RandomSettingsActivity.class);
        Pair<View, String> pair1 = Pair.create(v.findViewById(R.id.randomSwitch), "RandomSwitch");
        Pair<View, String> pair2 = Pair.create(v.findViewById(R.id.randomText), "RandomText");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2);
        startActivity(intent, options.toBundle());
    }

    public void PictureSettings(View v){

        Intent intent = new Intent(this, PictureSettingsActivity.class);
        Pair<View, String> pair1 = Pair.create(v.findViewById(R.id.pictureSwitch), "PictureSwitch");
        Pair<View, String> pair2 = Pair.create(v.findViewById(R.id.pictureText), "PictureText");
        Pair<View, String> pair3 = Pair.create(v.findViewById(R.id.pictureText2), "PictureText2");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2, pair3);
        startActivity(intent, options.toBundle());
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
            alarmIntentNow.putExtra("PICTURE", pictureSwitchState);
            pendingIntentNew = pendingIntent.getBroadcast(this, pendingIntentAlarm, alarmIntentNow, 0);
            manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP, alarmTime+settings.afterAlarmTime, pendingIntentNew);
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

        long LOW = settings.randomLow;
        long HIGH = settings.randomHigh;

        long rndr =  LOW + (long)(generator.nextDouble()*(HIGH - LOW));

        sendRandom = true;
        PendingIntent pendingIntent = null;
        //set up alarm and waiting intent
        Intent alarmIntent = new Intent(this, ShowNotif.class);
        alarmIntent.putExtra("RND", sendRandom);
        alarmIntent.putExtra("PICTURE", pictureSwitchState);
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
                .setContentTitle(getString(R.string.notificationTitle))
                .setContentText(getString(R.string.notificationText))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle(mBuilder).bigText(quote).setBigContentTitle(getString(R.string.notificationTitle)).setSummaryText(getString(R.string.NotificationSummary)))
        ;
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(resultPendingIntent);

        int mNotificationId = 001;

        //send notification
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public void showNotifNowPicture(){
        //Intent to send user to MainActivity
        Intent resultIntent = new Intent(this, RSSparser.class);
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
                .setContentTitle(getString(R.string.notificationTitle))
                .setContentText(getString(R.string.notificationTextImage))
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                ;
        mBuilder.setAutoCancel(true);
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        buildUI();
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        settingsPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        animEnter = settingsPreferences.getBoolean("showAnim", true);

        if (animEnter) {

            ViewGroup root = (ViewGroup) findViewById(R.id.scrollView);
            int count = root.getChildCount();
            float offset = getResources().getDimensionPixelSize(R.dimen.offset_y);
            Interpolator interpolator =
                    AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);

            // loop over the children setting an increasing translation y but the same animation
            // duration + interpolation
            for (int i = 0; i < count; i++) {
                View view = root.getChildAt(i);
                view.setVisibility(View.VISIBLE);
                view.setTranslationY(offset);
                view.setAlpha(0.85f);
                // then animate back to natural position
                view.animate()
                        .translationY(0f)
                        .alpha(1f)
                        .setInterpolator(interpolator)
                        .setDuration(1000L)
                        .start();
                // increase the offset distance for the next view
                offset *= 1.5f;
            }

        }
        final SharedPreferences.Editor settingsEditor = settingsPreferences.edit();
        settingsEditor.putBoolean("showAnim", true);
        settingsEditor.commit();

    }
}

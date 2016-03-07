package com.glaserproject.ondra.motivator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AlarmSettingsActivity extends AppCompatActivity {

    Settings settings = new Settings();
    TextView set20s;
    TextView set30s;
    TextView set1m;
    TextView set2m;
    TextView set5m;

    SharedPreferences settingsPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_settings);

        set20s = (TextView) findViewById(R.id.after20s);
        set30s = (TextView) findViewById(R.id.after30s);
        set1m = (TextView) findViewById(R.id.after1m);
        set2m = (TextView) findViewById(R.id.after2m);
        set5m = (TextView) findViewById(R.id.after5m);
        Switch mainSwitch = (Switch) findViewById(R.id.alarmSwitch);


        settingsPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor settingsEditor = settingsPreferences.edit();
        settings.loadFromSaved(getApplicationContext());

        mainSwitch.setChecked(settings.alarmSwitch);
        changeTime(settings.afterAlarmTimeInt);




        mainSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingsEditor.putBoolean("AlarmSwitch", isChecked);
                settingsEditor.commit();
            }
        });





        set20s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTime(1);
            }
        });
        set30s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTime(2);
            }
        });
        set1m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTime(3);
            }
        });
        set2m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTime(4);
            }
        });
        set5m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTime(5);
            }
        });


    }

    public void changeTime (int clickId){
        settingsPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor settingsEditor = settingsPreferences.edit();
        settingsEditor.putInt("AfterAlarmTimeInt", clickId);
        switch (clickId){
            case 0:
                break;
            case 1:                 //20s
                resetBgColorAll();
                set20s.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settingsEditor.putLong("AfterAlarmTime", 20000);
                break;
            case 2:                 //30s
                resetBgColorAll();
                set30s.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settingsEditor.putLong("AfterAlarmTime", 30000);
                break;
            case 3:                 //1m
                resetBgColorAll();
                set1m.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settingsEditor.putLong("AfterAlarmTime", 60000);
                break;
            case 4:                 //2m
                resetBgColorAll();
                set2m.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settingsEditor.putLong("AfterAlarmTime", 120000);
                break;
            case 5:                 //5m
                resetBgColorAll();
                set5m.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settingsEditor.putLong("AfterAlarmTime", 300000);
                break;
        }
        settingsEditor.commit();
    }

    public void resetBgColorAll(){
        set20s.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));
        set30s.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));
        set1m.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));
        set2m.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));
        set5m.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        settingsPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor settingsEditor = settingsPreferences.edit();
        settingsEditor.putBoolean("showAnim", false);
        settingsEditor.commit();
    }
}

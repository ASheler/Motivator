package com.glaserproject.ondra.motivator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class RandomSettingsActivity extends AppCompatActivity {

    Settings settings = new Settings();
    TextView set3h;
    TextView set4h;
    TextView set5h;
    TextView set8h;
    TextView set12h;

    SharedPreferences settingsPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_settings);

        set3h = (TextView) findViewById(R.id.every3h);
        set4h = (TextView) findViewById(R.id.every4h);
        set5h = (TextView) findViewById(R.id.every5h);
        set8h = (TextView) findViewById(R.id.every8h);
        set12h = (TextView) findViewById(R.id.every12h);

        Switch mainSwitch = (Switch) findViewById(R.id.alarmSwitch);


        settingsPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor settingsEditor = settingsPreferences.edit();
        settings.loadFromSaved(getApplicationContext());

        mainSwitch.setChecked(settings.randomSwitch);
        changeTime(settings.randomInt);
        mainSwitch.setChecked(settings.randomSwitch);


        mainSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingsEditor.putBoolean("RandomSwitch", isChecked);
                settingsEditor.commit();
            }
        });


        set3h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTime(1);
            }
        });
        set4h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTime(2);
            }
        });
        set5h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTime(3);
            }
        });
        set8h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTime(4);
            }
        });
        set12h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTime(5);
            }
        });

    }

    public void changeTime (int clickId){

        settingsPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor settingsEditor = settingsPreferences.edit();
        settingsEditor.putInt("RandomInt", clickId);

        switch (clickId){
            case 0:
                break;
            case 1:                 //3h
                resetBgColorAll();
                set3h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settingsEditor.putLong("RandomLow", 7200000);
                settingsEditor.putLong("RandomHigh", 14400000);

                break;
            case 2:                 //4h
                resetBgColorAll();
                set4h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settingsEditor.putLong("RandomLow", 10800000);
                settingsEditor.putLong("RandomHigh", 18000000);
                break;
            case 3:                 //5h
                resetBgColorAll();
                set5h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settingsEditor.putLong("RandomLow", 14400000);
                settingsEditor.putLong("RandomHigh", 21600000);
                break;
            case 4:                 //8h
                resetBgColorAll();
                set8h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settingsEditor.putLong("RandomLow", 25200000);
                settingsEditor.putLong("RandomHigh", 32400000);
                break;
            case 5:                 //12h
                resetBgColorAll();
                set12h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settingsEditor.putLong("RandomLow", 39600000);
                settingsEditor.putLong("RandomHigh", 46800000);
                break;
        }
        settingsEditor.commit();
    }

    public void resetBgColorAll(){
        set3h.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));
        set4h.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));
        set5h.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));
        set8h.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));
        set12h.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));

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

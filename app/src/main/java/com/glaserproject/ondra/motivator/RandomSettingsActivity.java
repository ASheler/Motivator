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
    boolean mainSwitchState;

    Settings settings = new Settings();
    TextView set3h;
    TextView set4h;
    TextView set5h;
    TextView set8h;
    TextView set12h;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_settings);
        Intent intent = getIntent();
        mainSwitchState = intent.getBooleanExtra("SwitchState", false);
        Switch mainSwitch = (Switch) findViewById(R.id.alarmSwitch);
        if (mainSwitchState){
            mainSwitch.setChecked(true);
        }

        sharedPreferences = getSharedPreferences("RandomSettings", Context.MODE_PRIVATE);

        mainSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.randomSwitch = isChecked;
            }
        });

        set3h = (TextView) findViewById(R.id.every3h);
        set4h = (TextView) findViewById(R.id.every4h);
        set5h = (TextView) findViewById(R.id.every5h);
        set8h = (TextView) findViewById(R.id.every8h);
        set12h = (TextView) findViewById(R.id.every12h);

        changeTime(sharedPreferences.getInt("ClickedId", 1));

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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ClickedId", clickId);
        editor.commit();
        switch (clickId){
            case 0:
                break;
            case 1:                 //3h
                resetBgColorAll();
                set3h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settings.randomLow = 7200000;
                settings.randomHigh = 14400000;

                break;
            case 2:                 //4h
                resetBgColorAll();
                set4h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settings.randomLow = 10800000;
                settings.randomHigh = 18000000;

                break;
            case 3:                 //5h
                resetBgColorAll();
                set5h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settings.randomLow = 14400000;
                settings.randomHigh = 21600000;
                break;
            case 4:                 //8h
                resetBgColorAll();
                set8h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settings.randomLow = 25200000;
                settings.randomHigh = 32400000;
                break;
            case 5:                 //12h
                resetBgColorAll();
                set12h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settings.randomLow = 39600000;
                settings.randomHigh = 46800000;
                break;
        }

    }

    public void resetBgColorAll(){
        set3h.setBackgroundColor(getResources().getColor(R.color.colorCardSelected));
        set4h.setBackgroundColor(getResources().getColor(R.color.colorCardSelected));
        set5h.setBackgroundColor(getResources().getColor(R.color.colorCardSelected));
        set8h.setBackgroundColor(getResources().getColor(R.color.colorCardSelected));
        set12h.setBackgroundColor(getResources().getColor(R.color.colorCardSelected));

    }

}

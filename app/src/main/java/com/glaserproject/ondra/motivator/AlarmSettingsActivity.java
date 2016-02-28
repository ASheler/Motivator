package com.glaserproject.ondra.motivator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    boolean mainSwitchState;
    Settings settings = new Settings();
    TextView set20s;
    TextView set30s;
    TextView set1m;
    TextView set2m;
    TextView set5m;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_alarm_settings);
        Intent intent = getIntent();
        mainSwitchState = intent.getBooleanExtra("SwitchState", false);
        Switch mainSwitch = (Switch) findViewById(R.id.alarmSwitch);
        if (mainSwitchState){
            mainSwitch.setChecked(true);
        }

        sharedPreferences = getSharedPreferences("AlarmSettings", Context.MODE_PRIVATE);

        mainSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.alarmSwitch = isChecked;
            }
        });




        set20s = (TextView) findViewById(R.id.after20s);
        set30s = (TextView) findViewById(R.id.after30s);
        set1m = (TextView) findViewById(R.id.after1m);
        set2m = (TextView) findViewById(R.id.after2m);
        set5m = (TextView) findViewById(R.id.after5m);

        changeTime(sharedPreferences.getInt("ClickedId", 1));

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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ClickedId", clickId);
        editor.commit();
        switch (clickId){
            case 0:
                break;
            case 1:                 //20s
                resetBgColorAll();
                set20s.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settings.afterAlarmTime = 20000;
                break;
            case 2:                 //30s
                resetBgColorAll();
                set30s.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settings.afterAlarmTime = 30000;
                break;
            case 3:                 //1m
                resetBgColorAll();
                set1m.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settings.afterAlarmTime = 60000;
                break;
            case 4:                 //2m
                resetBgColorAll();
                set2m.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settings.afterAlarmTime = 120000;
                break;
            case 5:                 //5m
                resetBgColorAll();
                set5m.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                settings.afterAlarmTime = 300000;
                break;
        }

    }

    public void resetBgColorAll(){
        set20s.setBackgroundColor(getResources().getColor(R.color.colorCardSelected));
        set30s.setBackgroundColor(getResources().getColor(R.color.colorCardSelected));
        set1m.setBackgroundColor(getResources().getColor(R.color.colorCardSelected));
        set2m.setBackgroundColor(getResources().getColor(R.color.colorCardSelected));
        set5m.setBackgroundColor(getResources().getColor(R.color.colorCardSelected));

    }

}

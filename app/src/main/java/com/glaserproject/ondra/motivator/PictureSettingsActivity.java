package com.glaserproject.ondra.motivator;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class PictureSettingsActivity extends AppCompatActivity {

    SharedPreferences settingsPreferences;
    Settings settings = new Settings();

    TextView past1h;
    TextView past24h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_settings);

        past1h = (TextView) findViewById(R.id.past1h);
        past24h = (TextView) findViewById(R.id.past24h);

        settingsPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor settingsEditor = settingsPreferences.edit();
        settings.loadFromSaved(getApplicationContext());

        Switch mainSwitch = (Switch) findViewById(R.id.pictureSwitch);

        mainSwitch.setChecked(settings.pictureSwitch);
        buildUI(settings.pictureUrlInt);

        mainSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settingsEditor.putBoolean("PictureSwitch", isChecked);
                settingsEditor.commit();
            }
        });

        past1h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildUI(0);
            }
        });

        past24h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildUI(1);
            }
        });




    }


    public void buildUI(int selection){

        settingsPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor settingsEditor = settingsPreferences.edit();
        settingsEditor.putInt("PictureUrlInt", selection);


        if (selection == 0){
            past1h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            past24h.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));
            settingsEditor.putString("PictureUrl", "https://www.reddit.com/r/GetMotivated/search.rss?q=flair%3AImage&sort=top&restrict_sr=on&t=hour");

        } else if (selection == 1){
            past1h.setBackgroundColor(getResources().getColor(R.color.colorSettingsDefault));
            past24h.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            settingsEditor.putString("PictureUrl", "https://www.reddit.com/r/GetMotivated/search.rss?q=flair%3AImage&sort=top&restrict_sr=on&t=day");
        }
        settingsEditor.commit();
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

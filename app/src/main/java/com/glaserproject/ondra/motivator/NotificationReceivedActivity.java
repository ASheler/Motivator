package com.glaserproject.ondra.motivator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationReceivedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_received);

        String quoteText;
        Intent intent = getIntent();
        quoteText = intent.getStringExtra("extraQ");

        TextView quoteTextView = (TextView) findViewById(R.id.quoteText);
        quoteTextView.setText(quoteText);
    }
}

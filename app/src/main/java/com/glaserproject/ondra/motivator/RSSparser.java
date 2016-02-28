package com.glaserproject.ondra.motivator;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RSSparser extends AppCompatActivity {

    TextView link;
    private String finalUrl="https://www.reddit.com/r/GetMotivated/search.rss?q=flair%3AImage&sort=top&restrict_sr=on&t=day";
    private HandleXML obj;
    String www;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssparser);
        findViewById(R.id.webView).setVisibility(View.GONE);
        findViewById(R.id.loadingBar).setVisibility(View.VISIBLE);

        new loadImage().execute();

        final GestureDetector gestureDetector = new GestureDetector(this, new SingleTapConfirm());


        WebView img = (WebView) findViewById(R.id.webView);
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)){
                    return true;
                }
                return false;
            }
        });



    }
    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(www));
            startActivity(intent);
            return false;
        }
    }

    private class loadImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            findViewById(R.id.loadingBar).setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            parseImg();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            findViewById(R.id.loadingBar).setVisibility(View.GONE);
        }
    }

    public void parseImg(){
        obj = new HandleXML(finalUrl);
        obj.fetchXML();
        while(obj.parsingComplete);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                link = (TextView) findViewById(R.id.userName);
                link.setText(obj.getLink());

                WebView img = (WebView) findViewById(R.id.webView);
                img.setVisibility(View.VISIBLE);
//                img.loadUrl(obj.getDescription());
                //img.loadUrl("http://i.imgur.com/7dOyhlx.png");
                img.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                String summary = "<html><body><img src=\"" + obj.getDescription() + "\" width=\"100%\"></body></html>";
                img.loadData(summary, "text/html", null);
                //img.loadDataWithBaseURL(null, "<style>img{display:inline; height:auto; max-width=100%;}</style>" + obj.getDescription(), "text/html", "UTF-8", null);
            }
        });
        www = obj.getDescription();

    }

}


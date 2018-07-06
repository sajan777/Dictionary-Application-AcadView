package com.example.halfdevil.dictionaryapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView SplashDisplay;
    //create a new TextView
    private final int SPLASH_LENGTH = 2000;
    //Duration of wait

    //override the onCreate method in SplashScreen.java file
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

    //Take the refrence of the TextView Created in the SplashDisplay
    SplashDisplay = (TextView)findViewById(R.id.SplashDisplay);

        // New Handler to start the Activity and close the Splash-Screen after some seconds.
    new Handler().postDelayed(new Runnable() {
        @Override
        //overriding the run method.
        public void run() {
            //create a new intentobject using the Intent class with attributes
            //as activity name and the 2nd activity.
            Intent intobj = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intobj);
            //this starts the main activity
        }
    },SPLASH_LENGTH);

    }
}

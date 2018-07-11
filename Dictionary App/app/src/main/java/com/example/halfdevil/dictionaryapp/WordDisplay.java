package com.example.halfdevil.dictionaryapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class WordDisplay extends AppCompatActivity {

    Button next,previous;//two buttons
    EditText editText;//one edit text
    DatabaseHelper dbh=null;//obj of databaseelper class
    String newString = "";
    String temp = "";//two strings for temproray purposes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_display);

        //giving refrences to the buttons and edittext
        editText = (EditText) findViewById(R.id.editText);
        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);

        //initalization of the object
        dbh = new DatabaseHelper(getApplicationContext());
        //bundle to get the value to the key assigned.
        Bundle b = getIntent().getExtras();

        //try catch for create database
        try {
            dbh.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        //try catch to open database
        try {
            dbh.openDataBase();
        } catch (Exception e) {
            throw new Error("Unable to open database");
        }

        //logic to take the value in the bundle and pass to fetch data and further using tem in the text view
        if (b != null) {
            newString = b.getString("VAL");
            temp = dbh.fetchData(newString);
            editText.setText(temp);
        }
        else{ Toast.makeText(getApplicationContext(),"Bundle not working Database Connected",Toast.LENGTH_LONG);}

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbh.IncrementId();

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dbh.DecrementId();
            }
        });

    }
}

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

    ImageButton next,previous;
    EditText editText;
    DatabaseHelper dbh=null;
    String newString = "";
    String temp = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_display);

        editText = (EditText) findViewById(R.id.editText);

        next = (ImageButton) findViewById(R.id.next);
        previous = (ImageButton) findViewById(R.id.previous);

        dbh = new DatabaseHelper(getApplicationContext());
        Bundle b = getIntent().getExtras();
        try {
            dbh.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            dbh.openDataBase();
        } catch (Exception e) {
            throw new Error("Unable to open database");
        }

        if (b != null) {
            newString = b.getString("VAL");
            temp = dbh.fetchData(newString);
            editText.setText(temp);
        }
        else{ Toast.makeText(getApplicationContext(),"Bundle not working Database Connected",Toast.LENGTH_LONG);}

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}

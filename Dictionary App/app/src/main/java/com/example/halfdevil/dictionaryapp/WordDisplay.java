package com.example.halfdevil.dictionaryapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class WordDisplay extends AppCompatActivity {

    EditText getWord,getWorddefine,getWordtype;
    Button next,previous;
    DictionaryValueHelper dvh=null;
    DatabaseHelper dbh=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_display);

        getWord = (EditText) findViewById(R.id.getWord);
        getWorddefine = (EditText) findViewById(R.id.getWorddefine);
        getWordtype = (EditText) findViewById(R.id.getWordtype);

        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);


        dbh = new DatabaseHelper(getApplicationContext());


        // Intent intobj = new Intent();
        Bundle b = getIntent().getExtras();


        if (b != null) {
            String newString = b.getString("VAL");
            //Toast.makeText(getApplicationContext(),newString,Toast.LENGTH_LONG).show();
            try {
          Cursor cur = dbh.fetchData(newString);
          cur.moveToFirst();
                String a =cur.getCount()+"";
                Toast.makeText(getApplicationContext(),a,Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        if (dvh != null) {
            try {
                String word = dvh.getWord();

                String define = dvh.getDefinition();
                String wordtype = dvh.getWordtype();

                getWord.setText(word);
                getWorddefine.setText(define);
                getWordtype.setText(wordtype);
            } catch (Exception e) {
                throw new Error("cant");
            }
        }
    }
}

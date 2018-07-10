package com.example.halfdevil.dictionaryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class WordDisplay extends AppCompatActivity {

    EditText getWord,getWorddefine,getWordtype;
    Button next,previous;
    DictionaryValueHelper dvh = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_display);

    getWord = (EditText)findViewById(R.id.getWord);
        getWorddefine = (EditText)findViewById(R.id.getWorddefine);
        getWordtype = (EditText)findViewById(R.id.getWordtype);

        next = (Button)findViewById(R.id.next);
        previous = (Button)findViewById(R.id.previous);


        DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());



        Intent intobj = new Intent();
        Bundle b = intobj.getExtras();

        if (b!=null) {
            String newString = (String) b.get("VAL");
            try {
                dbh.fetchData(newString);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        String getword=dvh.getWord();
        String getdefine=dvh.getDefinition();
        String getwordtype=dvh.getWordtype();

        getWord.setText(getword);
        getWorddefine.setText(getdefine);
        getWordtype.setText(getwordtype);

    }
}

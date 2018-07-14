package com.example.halfdevil.dictionaryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.halfdevil.dictionaryapp.DatabaseHelper.TABLE_NAME;

public class WordDisplay extends AppCompatActivity {

    Button next, previous;//two buttons
    //EditText editText;//one edit text
    DatabaseHelper dbh = null;//obj of databaseelper class
    String newString = "";
    String temp = "";//two strings for temproray purposes
    TextView textView;


    LinkedHashMap maplist = new LinkedHashMap<>();;//linked hashmap for key and value pair
    ArrayList wordList;
    ArrayList definitionList;//two arraylist for the word and meaning
    ArrayList<DictionaryHelper> dataSet= new ArrayList<>();//another array list of object of dictionaryhelper
    int position;
    int listsize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_display);

        //giving refrences to the buttons and edittext
      //  editText = (EditText) findViewById(R.id.editText);
        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);
        textView = (TextView)findViewById(R.id.textView);

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
            textView.setText(temp);
        } else {
            Toast.makeText(getApplicationContext(), "Bundle not working Database Connected", Toast.LENGTH_LONG);
        }


        int index;
        //dbh.openDataBase();
        SQLiteDatabase db = dbh.getReadableDatabase();
        //get database to query it.
        Cursor cursor = db.rawQuery("SELECT  id , word , wordtype , definition fROM "+ TABLE_NAME , null);
        //query
        if (cursor.getCount()>0) {
            index= cursor.getColumnIndex("word");
            //gives the int type index of the columm name word
            definitionList = new ArrayList<String>();
            //initalization of the two arraylits
            wordList = new ArrayList<String>();
            while (cursor.moveToNext()) {
                //adds the value and key pair to the map at the index
                maplist.put(cursor.getString(index), cursor.getString(cursor.getColumnIndex("definition")));
            }
            Iterator entries = maplist.entrySet().iterator();
            //iterator used to parse through the values

            while (entries.hasNext()) {
                Map.Entry thisEntry = (Map.Entry) entries.next();
                //checkes the entries into the map
                wordList.add(String.valueOf(thisEntry.getKey()));
                //add data in the list  using the map getkey for word and getvalue for meaning
                definitionList.add("- " + String.valueOf(thisEntry.getValue()));
            }
            for (int a = 0; a < wordList.size(); a++) {
                //loop throghout the size of the list  and initalize the constructor
                // of the dicthelper cclass and set the parameters.
                dataSet.add(new DictionaryHelper(wordList.get(a).toString(), definitionList.get(a).toString()));
            }
        }else {Toast.makeText(getApplicationContext(),"cursor not working",Toast.LENGTH_LONG).show();}

        position =dbh.value0;
        //get the current postion of the cursor from the db helper class
        listsize =  dataSet.size();
        //final total list size in which the data is present with word and meaning

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                //on next incr the position
                    if (position == listsize){position=0;}
                    //get the o/p in the text view
                textView.setText("word: "+dataSet.get(position).getWord()+"\n"+"meaning: "+dataSet.get(position).getMeaning());

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                //on prev dec the current pos
                if (position==-1){position=0;}
                textView.setText("word: "+dataSet.get(position).getWord()+"\n"+"meaning: "+dataSet.get(position).getMeaning());
            }
        });

    }
}

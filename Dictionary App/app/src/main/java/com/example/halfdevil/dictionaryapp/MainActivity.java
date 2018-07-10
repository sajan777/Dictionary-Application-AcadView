package com.example.halfdevil.dictionaryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class
MainActivity extends AppCompatActivity {

    EditText values;
    Button search;
    String searchValue;
    //creating an edit text and a Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Taking the refreces inside the new values created
        values = (EditText)findViewById(R.id.values);
        search = (Button)findViewById(R.id.search);

                final String searchValue=search.getText().toString();

        //adding a listener to the button
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            //override the onClick event fxn.
            public void onClick(View view) {
                DatabaseHelper dbh = new DatabaseHelper(getApplicationContext());


                //first try and catch
                try{
                    //try block to create database and check if it exists already
                    dbh.createDataBase();
                }catch (Exception e){throw new Error("Unable to create database");}

                //another try and catch block to open the database and raise an exception if it doesnt exist.
                try{
                    dbh.useDatabase();
                }catch (Exception e){e.getStackTrace();}

                //create a new intentobject using the Intent class with attributes
                //as activity name and the 3rd activity.
                Intent intobj = new Intent(MainActivity.this,WordDisplay.class);
                intobj.putExtra("VAL",searchValue);
                startActivity(intobj);
                //this starts the 3rd activity

            }
        });

    }
}

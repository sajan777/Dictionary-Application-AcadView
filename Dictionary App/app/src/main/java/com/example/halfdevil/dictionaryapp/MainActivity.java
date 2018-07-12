package com.example.halfdevil.dictionaryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class
MainActivity extends AppCompatActivity {

    ImageView imageView;
    EditText values;
    Button search;
    //creating an edit text and a Button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Taking the refreces inside the new values created
        values = (EditText)findViewById(R.id.values);
        search = (Button)findViewById(R.id.search);
        imageView = (ImageView)findViewById(R.id.imageView);


        //adding a listener to the button
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            //override the onClick event fxn.
            public void onClick(View view) {
                //create a new intentobject using the Intent class with attributes
                //as activity name and the 3rd activity.
                Intent intobj = new Intent(MainActivity.this,WordDisplay.class);
                intobj.putExtra("VAL",values.getText().toString());
                //putting the seached value in the keyValue pair using putExtra
                startActivity(intobj);
                //this starts the 3rd activity

            }
        });

    }
}

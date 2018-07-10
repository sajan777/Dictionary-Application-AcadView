package com.example.halfdevil.dictionaryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "dictionary";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "Dictionary1";
    static final  String WORD = "word";
    static final String WORDTYPE = "wordtype";
    static final String DEFINITION = "definition";
    static final String KEY_ID = "id";
    String DATABASEPATH = null ;
    Context context;
    SQLiteDatabase db;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        DATABASEPATH = context.getDatabasePath(DATABASE_NAME).toString();
        this.DATABASEPATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DATABASEPATH);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DATABASEPATH+DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public void useDatabase() throws IOException{
        String myPath = DATABASEPATH+DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
    public void copyDataBase() throws IOException{
        InputStream inputFile = context.getAssets().open(DATABASE_NAME);
        String myPath = DATABASEPATH+DATABASE_NAME;
        //String getPath = myPath;
        OutputStream outputFile = new FileOutputStream(myPath);
        byte[] barr = new byte[10];
        int getvalue = inputFile.read(barr);
        while (getvalue>0){
            outputFile.write(barr,0,getvalue);
        }
        outputFile.flush();
        outputFile.close();
        inputFile.close();
    }
    public DictionaryValueHelper fetchData(String value)throws IOException{
        DictionaryValueHelper dvh = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = ("SELECT WORD,WORDTYPE,DEFINITION fROM TABLE_NAME "+"WHERE WORD= '" + value + "' ");
        //Cursor cursor = db.query("SELECT WORD,WORDTYPE,DEFINITION fROM TABLE_NAME "+"WHERE WORD= '" + value + "' ");
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor!=null){
            cursor.moveToFirst();
            dvh = new DictionaryValueHelper(cursor.getString(0),cursor.getString(1),cursor.getString(2));
        }
        return dvh;

    }

    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }




}

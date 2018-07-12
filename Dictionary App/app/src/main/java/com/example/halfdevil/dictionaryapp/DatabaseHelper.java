package com.example.halfdevil.dictionaryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    //destination path (location) of our database on device
    private static String DB_PATH = "";
    private static String DB_NAME ="dictionary.db";// Database name
    private SQLiteDatabase mDataBase;//obj to the sqlitedatabase
    private final Context mContext;//context object
    static final String TABLE_NAME = "Dictionary1";//database table name.
    Cursor cursor = null;//cursor object as null
    int value0;
    String value1 = null;
    String value2 = null;
    String value3 = null;
    String word= null;

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);// 1? Its database Version
        if(android.os.Build.VERSION.SDK_INT >= 17){
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
            //giving out the actual path according to the sdk version
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.mContext = context;
        //initalize the context object
    }


    public void createDataBase() throws IOException
   {
//        Creates a empty database on the system and rewrites it with your own database.
//         By calling this method and empty database will be created into the default system path
//            of your application so we are gonna be able to overwrite that database with our database.

        boolean mDataBaseExist = checkDataBase();
        //check for existence
        if(mDataBaseExist){}//do nothing
        else{
            this.getWritableDatabase();
            copyDataBase();
        //calls the copy database method
            }

    }

//    Check if the database already exist to avoid re-copying the file each time you open the application.
//return true if it exists, false if it doesn't
    private boolean checkDataBase()
    {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    //Copy the database from assets using the byte stream
    //by copying here it means rewriting the database file that wwas created using the create database
    //method

    private void copyDataBase() throws IOException
    {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer))>0)
        {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public void openDataBase() throws SQLException
    {
        String mPath = DB_PATH + DB_NAME;//path
        mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close()
    {       //used to close the database that was created to copy and
        //in which the data was rewritten
        if(mDataBase != null)
            mDataBase.close();
        super.close();
    }


    //fetch data fxn that takes up string as an argument
    public String fetchData(String value) {
         cursor = mDataBase.rawQuery("SELECT  id , word , wordtype , definition fROM "+ TABLE_NAME + " WHERE word= '" + value + "' " , null);
        //cursor query used to select the word,wordtype,def,form the database in the table where the word matches
         //string variables for execution purposes

        if (cursor.getCount()>0) {
            //main logic that uses to get the data from the table
            if (cursor.moveToFirst()) ;
            {
                value0 = Integer.parseInt(cursor.getString(0));
                value1 = cursor.getString(1);
                value2 = cursor.getString(2);
                value3 = cursor.getString(3);

                word = "Id: "+ value0 +"\nWord: "+ value1 +"\nWordtype: "+ value2 +"\nDefiniton: "+ value3;
                //final concatinated string
            }
        }
        return word;
        //return the total value of the string

    }

    public String IncrementId(){
        String s1=null,s2=null,s3=null,s4=null;
        int i;
        cursor = mDataBase.rawQuery("SELECT id , word , wordtype , definition fROM " + TABLE_NAME + " WHERE id= '" + value0 + "' ", null);
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            value0 = Integer.parseInt(cursor.getString(0));
            s1 = cursor.getString(1);
            s2 = cursor.getString(2);
            s3 = cursor.getString(3);
            s4 = "Id: " + value0 + "\nWord: " + s1 + "\nWordtype: " + s2 + "\nDefiniton: " + s3;

        }
        return s4;

    }
    public String DecrementId(){
        String s1=null,s2=null,s3=null,s4=null;
        int i;
        cursor = mDataBase.rawQuery("SELECT id , word , wordtype , definition fROM " + TABLE_NAME + " WHERE id= '" + value0 + "' ", null);
        if (cursor.getCount() > 0) {
            cursor.moveToPrevious();
            value0 = Integer.parseInt(cursor.getString(0));
            s1 = cursor.getString(1);
            s2 = cursor.getString(2);
            s3 = cursor.getString(3);
            s4 = "Id: " + value0 + "\nWord: " + s1 + "\nWordtype: " + s2 + "\nDefiniton: " + s3;
        }
        return s4;

    }

//onCreate and onUpgrade are empty here cause we don't need them.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

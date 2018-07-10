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

   /* static final String DATABASE_NAME = "dictionary";
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
*/

        private static String TAG = "DataBaseHelper"; // Tag just for the LogCat window
        //destination path (location) of our database on device
        private static String DB_PATH = "";
        private static String DB_NAME ="dictionary.db";// Database name
        private SQLiteDatabase mDataBase;
        private final Context mContext;
    static final String TABLE_NAME = "Dictionary1";

        public DatabaseHelper(Context context)
        {
            super(context, DB_NAME, null, 1);// 1? Its database Version
            if(android.os.Build.VERSION.SDK_INT >= 17){
                DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
            }
            else
            {
                DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
            }
            this.mContext = context;
        }

        public void createDataBase() throws IOException
        {
            //If the database does not exist, copy it from the assets.

            boolean mDataBaseExist = checkDataBase();
            if(!mDataBaseExist)
            {
                this.getReadableDatabase();
                this.close();
                try
                {
                    //Copy the database from assests
                    copyDataBase();
                    Log.e(TAG, "createDatabase database created");
                }
                catch (IOException mIOException)
                {
                    throw new Error("ErrorCopyingDataBase");
                }
            }
        }

        //Check that the database exists here: /data/data/your package/databases/Da Name
        private boolean checkDataBase()
        {
            File dbFile = new File(DB_PATH + DB_NAME);
            //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
            return dbFile.exists();
        }

        //Copy the database from assets
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
        public boolean openDataBase() throws SQLException
        {
            String mPath = DB_PATH + DB_NAME;
            //Log.v("mPath", mPath);
            mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
            //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
            return mDataBase != null;
        }

        @Override
        public synchronized void close()
        {
            if(mDataBase != null)
                mDataBase.close();
            super.close();
        }

    public Cursor fetchData(String value)throws IOException {
            copyDataBase();
        DictionaryValueHelper dvh=null ;
        SQLiteDatabase db = this.getReadableDatabase();
        //String queryString = ("SELECT WORD,WORDTYPE,DEFINITION fROM "+TABLE_NAME+" WHERE WORD= '" + value + "' ");
        //Cursor cursor = db.query("SELECT WORD,WORDTYPE,DEFINITION fROM TABLE_NAME "+"WHERE WORD= '" + value + "' ");
        Cursor cursor = db.rawQuery("SELECT WORD, WORDTYPE, DEFINITION fROM "+TABLE_NAME+" WHERE WORD= '" + value + "' ", null);
//        if (cursor.getCount()>0) {
//            cursor.moveToFirst();
//                dvh= new DictionaryValueHelper(cursor.getString(0),cursor.getString(1),cursor.getString(2));
//        }
        return cursor;

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

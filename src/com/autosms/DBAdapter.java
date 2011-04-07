package com.autosms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_TITEL = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DATE_TO_SEND = "date_to_send";    
    public static final String KEY_TYP = "typ";
    private static final String TAG = "DBAdapter";
    
    private static final String DATABASE_NAME = "autosms";
    private static final String DATABASE_TABLE = "sms";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
        "create table sms (_id integer primary key autoincrement, "
        + "title text not null, message text not null, " 
        + "date_to_send date not null, typ integer not null);";
        
    private final Context context;
    
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) 
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
        
    private static class DatabaseHelper extends SQLiteOpenHelper 
    {
        DatabaseHelper(Context context) 
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) 
        {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, 
                              int newVersion) 
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion 
                  + " to "
                  + newVersion + ", which will destroy all old data!");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }  

    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---    
    public void close() 
    {
        DBHelper.close();
    }
    
    //---insert a new SMS into the database---
    public long insertSMS(String title, String message, long date_to_send ,long typ)
    {
    	
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITEL, title);
        initialValues.put(KEY_MESSAGE, message );
        initialValues.put(KEY_DATE_TO_SEND, date_to_send);
        initialValues.put(KEY_TYP, typ);
        return db.insert(DATABASE_TABLE, null, initialValues);

    }
    
    //---retrieves all the SMS---
    public Cursor getAllSMS() 
    {
        return db.query(DATABASE_TABLE, new String[] {
        		KEY_ROWID, 
        		KEY_TITEL,
        		KEY_MESSAGE,
                KEY_DATE_TO_SEND,
                KEY_TYP}, 
                null, 
                null, 
                null, 
                null, 
                null);
    }
    
    //---delete a SMS---
    public long deleteSMS(long rowId)
    {
    	return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null);
    }

}
package com.autosms;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/*Word database*/
public class DataWords {
	
	    public static final String KEY_ROWID = "_id";
	    public static final String KEY_ISBN = "isbn";
	   
	    private static final String TAG = "DBAdapter";
	    
	    private static final String DATABASE_NAME = "books";
	    private static final String DATABASE_TABLE = "titles";
	    private static final int DATABASE_VERSION = 2;

	    private static final String DATABASE_CREATE =
	        "create table titles (_id integer primary key autoincrement, "
	        + "isbn text not null);";
	        
	    private final Context context; 
	    
	    private DatabaseHelper DBHelper;
	    private SQLiteDatabase db;
	    
		public DataWords(Context smsReceiver) 
	    {
	        this.context = smsReceiver;
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
	                    + newVersion + ", which will destroy all old data");
	            db.execSQL("DROP TABLE IF EXISTS titles");
	            onCreate(db);
	        }
	    }    
	    
	    //---opens the database---
	    public DataWords open() throws SQLException 
	    {
	        db = DBHelper.getWritableDatabase();
	        return this;
	    }

	    //---closes the database---    
	    public void close() 
	    {
	        DBHelper.close();
	    }
	    
	    //---insert a title into the database---
	    public long insertTitle(String isbn) 
	    {
	        ContentValues initialValues = new ContentValues();
	        initialValues.put(KEY_ISBN, isbn);
	     
	        return db.insert(DATABASE_TABLE, null, initialValues);
	    }

	    //---deletes a particular title---
	    public boolean deleteTitle(long rowId) 
	    {
	        return db.delete(DATABASE_TABLE, KEY_ROWID + 
	        		"=" + rowId, null) > 0;
	    }

	    //---retrieves all the titles---
	    public Cursor getAllTitles() 
	    {
	        return db.query(DATABASE_TABLE, new String[] {
	        		KEY_ROWID, 
	        		KEY_ISBN},
	       
	                null, 
	                null, 
	                null, 
	                null, 
	                null);
	    }

	    //---retrieves a particular title---
	    public Cursor getTitle(long rowId) throws SQLException 
	    {
	        Cursor mCursor =
	                db.query(true, DATABASE_TABLE, new String[] {
	                		KEY_ROWID,
	                		KEY_ISBN, 
	     
	                		}, 
	                		KEY_ROWID + "=" + rowId, 
	                		null,
	                		null, 
	                		null, 
	                		null, 
	                		null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }

	    //---updates a title---
	    public boolean updateTitle(long rowId, String isbn)
	     
	    {
	        ContentValues args = new ContentValues();
	        args.put(KEY_ISBN, isbn);
	    
	        return db.update(DATABASE_TABLE, args, 
	                         KEY_ROWID + "=" + rowId, null) > 0;
	    }
	    public void badWords(){
	    	new Thread(new Runnable() {
				
			//	private long id;

				@Override
				public void run() {
				try {
						loadWords();
					} catch (Exception e) {
						
					}
					
					
				}
	    }).start();
				
				
	    }
	    private void loadWords() throws IOException {
            Log.d(TAG, "Loading words...");
            final Resources resources = context.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.badwords);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
            	
            String str;
            while((str=reader.readLine())!=null){
            insertTitle(str);
            
            }
            } finally {
                reader.close();
            }
            Log.d(TAG, "DONE loading words.");
        }

	    
	}






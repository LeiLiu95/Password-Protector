package com.example.passwordprotector;

import android.database.sqlite.*;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;


public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";//file where we are saving on device
    public static final String TABLE = "accounts";//name of table
    public static final String COLUMN_ID = "_id";//
    public static final String COLUMN_NAME = "accountname";
    private static DBHandler instance;
    static Object obj = new Object();

    private DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public static DBHandler getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        if(instance == null){
            synchronized (obj){
                if(instance == null){
                    instance = new DBHandler(context, DATABASE_NAME, factory, DATABASE_VERSION);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE_TABLE " + TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + COLUMN_NAME + " TEXT "+");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE);
        onCreate(db);
    }



    public void addAccount(Password pass){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pass.getAccountName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE,null,values);
        db.close();
    }
    public void deleteAccount(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " +TABLE+ " WHERE " +COLUMN_NAME+ "=\"" +name+ "=\";");
    }
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("accountname"))!=null){
                dbString += c.getString(c.getColumnIndex("accountname"));
                dbString += "\n";
            }
        }
        db.close();
        return dbString;
    }
}

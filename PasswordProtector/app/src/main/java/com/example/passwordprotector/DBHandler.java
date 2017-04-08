package com.example.passwordprotector;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;

import java.io.File;
import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database.db";//file where we are saving on device
    public static final String TABLE = "accounts";//name of table
    public static final String ACCOUNT = "accountname";//
    public static final String PASSWORD = "password";
    private static DBHandler instance;
    static Object obj = new Object();
    private static Context cont;
    File databasePath;
    //could just set passphrase here at beginning?


    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;
        databasePath = context.getDatabasePath(DATABASE_NAME);
        SQLiteDatabase.loadLibs(context);
    }

    public static DBHandler getInstance(Context context){
        if(instance == null){
            synchronized (obj){
                if(instance == null){
                    instance = new DBHandler(context);
                }
                else if(cont !=context){
                    instance = new DBHandler(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String query = "CREATE TABLE " + TABLE + "(" + ACCOUNT + " TEXT " + PASSWORD + " TEXT "+");";
        db.execSQL("CREATE TABLE accounts (accountname TEXT, password TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE);
        onCreate(db);
    }



    public void addAccount(Password pass, String passphrase){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase(passphrase);

        values.put(ACCOUNT, pass.getAccountName());
        values.put(PASSWORD, pass.getPassword());


        db.insert(TABLE,null,values);
        db.close();
    }
    public void deleteAccount(String name, String passphrase){
        SQLiteDatabase db = getWritableDatabase(passphrase);
        db.execSQL("DELETE FROM " +TABLE+ " WHERE " + ACCOUNT + "=\"" +name+ "=\";");
    }
    public ArrayList<String> databaseToString(String passphrase){
        ArrayList<String> dbString = new ArrayList<>();

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(databasePath, passphrase, null);
        String query = "SELECT * FROM " + TABLE + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("accountname"))!=null){
                dbString.add(c.getString(c.getColumnIndex("accountname")));
            }
        }
        db.close();
        return dbString;
    }
}

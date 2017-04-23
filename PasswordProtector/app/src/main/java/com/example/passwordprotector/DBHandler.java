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
    public static final String ACCOUNT = "accountname";//column that contains account names
    public static final String PASSWORD = "password";//column that contains passwords

    public static final String SECANSWER1 = "securityanswer1";

    public static final String SECANSWER2 = "securityanswer2";

    public static final String SECANSWER3 = "securityanswer3";
    private static DBHandler instance;
    static Object obj = new Object();
    private static Context cont;
    File databasePath;
    SQLiteDatabase sqLiteDatabase;
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
        db.execSQL("CREATE TABLE accounts (accountname TEXT, password TEXT, securityanswer1 TEXT, securityanswer2 TEXT, securityanswer3 TEXT);");

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
        ArrayList<String> secA = pass.getSecurityAnswers();
        values.put(SECANSWER1,secA.get(0));
        values.put(SECANSWER2,secA.get(1));
        values.put(SECANSWER3,secA.get(2));
        db.insert(TABLE,null,values);
        db.close();
    }
    public void deleteAccount(String name, String passphrase){
        SQLiteDatabase db = getWritableDatabase(passphrase);
        db.delete(TABLE, ACCOUNT + "= ?", new String[]{name});
        //db.execSQL("DELETE FROM " +TABLE+ " WHERE " + ACCOUNT + "=\"" +name+ "=\";");
    }

    public ArrayList<String> getPassword(String passphrase, String name){
        SQLiteDatabase db = getWritableDatabase(passphrase);
        String query = "SELECT * FROM " + TABLE + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        ArrayList<String> stuff = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                if(c.getString(c.getColumnIndex("accountname")).equals(name)){
                    String password = c.getString(c.getColumnIndex("password"));
                    stuff.add(password);
                    password = c.getString(c.getColumnIndex("securityanswer1"));
                    stuff.add(password);
                    password = c.getString(c.getColumnIndex("securityanswer2"));
                    stuff.add(password);
                    password = c.getString(c.getColumnIndex("securityanswer3"));
                    stuff.add(password);
                    c.close();
                    db.close();
                    return stuff;
                }
            }while(c.moveToNext());
        }
        db.close();
        c.close();
        return null;
    }


    public boolean nameInDatabase(String passphrase, String name){
        SQLiteDatabase db = getWritableDatabase(passphrase);
        String query = "SELECT * FROM " + TABLE + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        if(c.moveToFirst()){
            do{
                if(c.getString(c.getColumnIndex("accountname")).equals(name)){

                    c.close();
                    db.close();
                    return true;
                }
            }while(c.moveToNext());
        }
        db.close();
        c.close();
        return false;
    }


    public ArrayList<String> databaseToString(String passphrase){
        ArrayList<String> dbString = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase(passphrase);
        //SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(databasePath, passphrase, null);
        String query = "SELECT * FROM " + TABLE + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        if(c.moveToFirst()){
            do{
                if(c.getString(c.getColumnIndex("accountname"))!=null){
                    dbString.add(c.getString(c.getColumnIndex("accountname")));
                }
            }while(c.moveToNext());
        }
//        c.moveToFirst();
//        while(!c.isAfterLast()){
//            if(c.getString(c.getColumnIndex("accountname"))!=null){
//                dbString.add(c.getString(c.getColumnIndex("accountname")));
//            }
//        }
        c.close();
        db.close();
        return dbString;
    }
}

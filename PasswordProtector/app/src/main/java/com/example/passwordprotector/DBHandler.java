package com.example.passwordprotector;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;

import java.io.File;
import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "database.db";//file where we are saving on device
    public static final String TABLE = "accounts";//name of table
    public static final String ACCOUNT = "accountname";//column that contains account names
    public static final String PASSWORD = "password";//column that contains passwords
    public static final String SECANSWER1 = "securityanswerone";
    public static final String SECANSWER2 = "securityanswertwo";
    public static final String SECANSWER3 = "securityanswerthree";
    public static final String[] COLUMNS = {ACCOUNT,PASSWORD,SECANSWER1,SECANSWER2,SECANSWER3};
    private static DBHandler instance;
    static Object obj = new Object();
    private static Context cont;

    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;

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
        db.execSQL("CREATE TABLE accounts ( accountname TEXT, password TEXT, securityanswerone TEXT, securityanswertwo TEXT, securityanswerthree TEXT)");

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

    public Password getPassword(String passphrase, String name){
        SQLiteDatabase db = getWritableDatabase(passphrase);
        //String query = "SELECT * FROM " + TABLE + " WHERE 1";
        String query = " accountname LIKE ?";
        //Cursor c = db.rawQuery("SELECT accountname, password, securityanswerone, securityanswertwo, securityanswerthree FROM accounts WHERE accountname = " + name, null);

        //Cursor c = db.rawQuery(query,null);
        Cursor c = db.query(TABLE, COLUMNS, query, new String[] {name + "%"},null,null,null);
        Password password = new Password();
        if(c != null){
            c.moveToFirst();
        }
        password.setAccountName(name);
        password.setPassword(c.getString(1));
        password.setSecurityAnswers(c.getString(c.getColumnIndex("securityanswerone")));
        password.setSecurityAnswers(c.getString(3));
        password.setSecurityAnswers(c.getString(4));

//        if(c.moveToFirst()){
//            do{
//                password.setAccountName(name);
//                password.setPassword(c.getString(c.getColumnIndex("password")));
//                password.setSecurityAnswers(c.getString(c.getColumnIndex("securityanswerone")));
//                password.setSecurityAnswers(c.getString(c.getColumnIndex("securityanswertwo")));
//                password.setSecurityAnswers(c.getString(c.getColumnIndex("securityanswerthree")));
//            }while(c.moveToNext());
//        }
        db.close();
        c.close();
        return password;
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

//    public void editPassword(String passphrase, String oldUser, String newUser, Password pass){
//        SQLiteDatabase db = getWritableDatabase(passphrase);
//        ContentValues cv = new ContentValues();
//        ArrayList<String> list = pass.getSecurityAnswers();
//        cv.put(ACCOUNT, newUser);
//        cv.put(PASSWORD, pass.getPassword());
//        cv.put(SECANSWER1, list.get(0));
//        cv.put(SECANSWER2, list.get(1));
//        cv.put(SECANSWER3, list.get(2));
//        //db.update(TABLE,cv,"accountname LIKE " + oldUser , null);
//        db.close();
//    }
    public void editPassword(String passphrase, Password pass, String oldUser){
        deleteAccount(oldUser,passphrase);
        addAccount(pass,passphrase);
    }
}

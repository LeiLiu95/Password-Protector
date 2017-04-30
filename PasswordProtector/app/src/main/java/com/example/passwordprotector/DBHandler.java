package com.example.passwordprotector;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;

import java.io.File;
import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 4; //update this whenever any changes to structure of database are made
    private static final String DATABASE_NAME = "database.db"; //file where we are saving on device
    public static final String TABLE = "accounts"; //name of table
    public static final String ACCOUNT = "accountname"; //column that contains account names
    public static final String PASSWORD = "password"; //column that contains passwords
    public static final String SECANSWER1 = "securityanswerone"; //column that contains security answer 1
    public static final String SECANSWER2 = "securityanswertwo"; //column that contains security answer 2
    public static final String SECANSWER3 = "securityanswerthree"; //column that contains security answer 3
    public static final String[] COLUMNS = {ACCOUNT,PASSWORD,SECANSWER1,SECANSWER2,SECANSWER3}; //array containing the names of columns
    private static DBHandler instance; //singleton class instance
    static Object obj = new Object(); //object for synchonization
    private static Context cont; //context

    /*
        private constructor that is only called once for instance the first time we make the instance variable
    */
    private DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cont = context;
        //loads the library information for encrypted database
        SQLiteDatabase.loadLibs(context);
    }
    /*
        method for getting the singleton instance of the database
    */
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

    //creates table in database with the correct columns
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE accounts ( accountname TEXT, password TEXT, securityanswerone TEXT, securityanswertwo TEXT, securityanswerthree TEXT)");

    }
    //if the version is updated, it just remakes table, for later versions, we would change this so that information gets carried over with upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE);
        onCreate(db);
    }
    /*
        called whenever an account is to be added into the database. 
        @param pass is type Password with all the data to be added into the database
        @param passphrase is String that decrypts the database
    */
    public void addAccount(Password pass, String passphrase){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase(passphrase);
        values.put(ACCOUNT, pass.getAccountName()); //inserting the account name
        values.put(PASSWORD, pass.getPassword()); //inserting the password
        ArrayList<String> secA = pass.getSecurityAnswers(); //gets arraylist of security answers
        //inserting security answers
        values.put(SECANSWER1,secA.get(0)); 
        values.put(SECANSWER2,secA.get(1));
        values.put(SECANSWER3,secA.get(2));
        db.insert(TABLE,null,values);
        db.close();
    }
    /*
        called whenever an account is requested to be deleted fromt he database.
        @param name is String containing name of the account
        @param passphrase is String that decrypts the database
    */
    public void deleteAccount(String name, String passphrase){
        SQLiteDatabase db = getWritableDatabase(passphrase);
        db.delete(TABLE, ACCOUNT + "= ?", new String[]{name});
    }
    /*
        called whenever the app requests for an accounts data to be extracted from database
        @param name is String containing name of the account
        @param passphrase is String that decrypts the database
        @return Password type containing the data for the account with name 'name'
    */
    public Password getPassword(String passphrase, String name){
        SQLiteDatabase db = getWritableDatabase(passphrase);
        String query = " accountname LIKE ?";
        //getting cursor pointing to location of the account we are looking for
        Cursor c = db.query(TABLE, COLUMNS, query, new String[] {name + "%"},null,null,null);
        Password password = new Password();
        //setting cursor to first of the set of rows the column found
        if(c != null){
            c.moveToFirst();
        }
        //setting password data
        password.setAccountName(name);
        password.setPassword(c.getString(1));
        password.setSecurityAnswers(c.getString(c.getColumnIndex("securityanswerone")));
        password.setSecurityAnswers(c.getString(3));
        password.setSecurityAnswers(c.getString(4));
        db.close();
        c.close();
        return password;
    }

    /*
        checks to see if the name is in the database
        @param passphrase is String that decrypts the database
        @param name is String of account name
        @return true if in database, otherwise false
    */
    public boolean nameInDatabase(String passphrase, String name){
        SQLiteDatabase db = getWritableDatabase(passphrase);
        String query = "SELECT * FROM " + TABLE + " WHERE 1";//query for retreiving all data in database
        Cursor c = db.rawQuery(query,null);
        if(c.moveToFirst()){
            do{
                if(c.getString(c.getColumnIndex("accountname")).equals(name)){ //account name was found

                    c.close();
                    db.close();
                    return true;
                }
            }while(c.moveToNext());
        }
        //account name wasnt found
        db.close();
        c.close();
        return false;
    }

    /*
        @param passphrase is String that decrypts the database
        @return arraylist of names of accounts in database
    */
    public ArrayList<String> databaseToString(String passphrase){
        ArrayList<String> dbString = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase(passphrase);
        String query = "SELECT * FROM " + TABLE + " WHERE 1";
        Cursor c = db.rawQuery(query,null);
        if(c.moveToFirst()){
            do{
                if(c.getString(c.getColumnIndex("accountname"))!=null){
                    dbString.add(c.getString(c.getColumnIndex("accountname")));
                }
            }while(c.moveToNext());
        }

        c.close();
        db.close();
        return dbString;
    }

    /*
        finds old account, deletes it and replaces with new information with new row entry
        @param passphrase is String that decrypts the database
        @param pass is type password containing the new password information
        @param oldUser is name of the old account information
    */
    public void editPassword(String passphrase, Password pass, String oldUser){
        deleteAccount(oldUser,passphrase);
        addAccount(pass,passphrase);
    }
}

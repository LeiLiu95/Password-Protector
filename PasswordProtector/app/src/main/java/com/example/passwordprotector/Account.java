package com.example.passwordprotector;

import java.util.ArrayList;

/**
 * Created by Chris on 4/7/2017.
 */

public class Account {
    private static Account instance;
    private ArrayList<Password> accountList;
    private static DBHandler database;
    static Object obj = new Object();
    private Account(){
        accountList = new ArrayList<>();
    }
    public static Account getInstance(){
        if(instance == null){
            synchronized (obj){
                if(instance == null){
                    instance = new Account();
                }
            }
        }
        return instance;
    }

    public ArrayList<Password> getPasswords(){
        return accountList;
    }
    public void edit(String name, String edit_Type){

    }
}

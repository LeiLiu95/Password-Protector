package com.example.passwordprotector;

/**
 * Created by Chris on 4/7/2017.
 */

public abstract class PasswordGenerator {
    public Password addPassword(String name, int numLetters, boolean capLet, boolean special, boolean nums){
        String params = "";
        if(capLet){
            params+= "yes ";
        }else{
            params+= "no ";
        }
        if(special){
            params+= "yes ";
        }else{
            params+= "no ";
        }
        if(nums){
            params+= "yes ";
        }else{
            params+= "no ";
        }
        int size=numLetters;
        return createPassword(name,size,params);
    }
    abstract Password createPassword(String name, int size, String params);
}

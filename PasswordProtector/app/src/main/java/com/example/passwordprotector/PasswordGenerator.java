package com.example.passwordprotector;


public abstract class PasswordGenerator {
    public Password addPassword(String name, int numLetters, boolean capLet, boolean special, boolean nums){

        return createPassword(name,numLetters,capLet,special,nums);
    }
    abstract Password createPassword(String name, int size, boolean capLet, boolean special, boolean nums);
}

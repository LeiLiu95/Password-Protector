package com.example.passwordprotector;



public class PasswordOnly extends PasswordGenerator {



    @Override
    Password createPassword(String name, int size, boolean capLet, boolean special, boolean nums) {

        Password password = new Password();
        password.setAccountName(name);
        //algorithm here pls
        //this is LOL password, pls change
        password.setPassword(passwordGeneration(size, capLet, special, nums));
        return password;
    }

    public String passwordGeneration(int length, boolean cap, boolean special, boolean num){
        return null;
    }
}

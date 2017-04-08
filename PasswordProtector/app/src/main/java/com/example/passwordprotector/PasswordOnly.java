package com.example.passwordprotector;



public class PasswordOnly extends PasswordGenerator {



    @Override
    Password createPassword(String name, int size, String params) {
        String[] param = params.split(" ");
        Password password = new Password();
        password.setAccountName(name);
        //algorithm here pls
        return password;
    }
}

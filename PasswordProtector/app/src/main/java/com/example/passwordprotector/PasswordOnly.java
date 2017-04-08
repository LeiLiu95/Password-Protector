package com.example.passwordprotector;

/**
 * Created by Chris on 4/8/2017.
 */

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

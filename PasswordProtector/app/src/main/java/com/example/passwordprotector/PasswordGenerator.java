package com.example.passwordprotector;

/**
 * Created by Chris on 4/7/2017.
 */

public abstract class PasswordGenerator {
    public Password addPassword(String label){
        Password password;
        int params=0;
        int size=0;
        password = createPassword(label,params,size);
        return password;
    }
    abstract Password createPassword(String label, int params, int size);
}

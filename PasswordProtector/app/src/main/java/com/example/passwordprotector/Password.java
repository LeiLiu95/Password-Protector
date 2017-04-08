package com.example.passwordprotector;

/**
 * Created by Chris on 4/7/2017.
 */

public class Password {
    private String accountName;
    private String password;

    public Password() {}

    public String getAccountName() {
        return accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

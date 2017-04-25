package com.example.passwordprotector;


import java.util.ArrayList;

public class Password {
    private String accountName;
    private String password;

    private ArrayList<String> securityAnswers = new ArrayList<>();



    public ArrayList<String> getSecurityAnswers() {
        return securityAnswers;
    }

    public void setSecurityAnswers(String s){
        securityAnswers.add(s);
    }
    public void addSecutityAnswers(ArrayList<String> list){
        securityAnswers = list;
    }

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

package com.example.passwordprotector;


import java.util.ArrayList;

public class Password {
    private String accountName;
    private String password;
    private ArrayList<String> securityQuestions = new ArrayList<>();
    private ArrayList<String> securityAnswers = new ArrayList<>();

    public ArrayList<String> getSecurityQuestions() {
        return securityQuestions;
    }

    public ArrayList<String> getSecurityAnswers() {
        return securityAnswers;
    }
    public void setSecurityQuestions(String s){
        securityQuestions.add(s);
    }
    public void setSecurityAnswers(String s){
        securityAnswers.add(s);
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

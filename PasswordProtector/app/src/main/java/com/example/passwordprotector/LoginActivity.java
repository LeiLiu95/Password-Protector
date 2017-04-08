package com.example.passwordprotector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView usernameText;
    TextView passwordText;
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = (TextView)  findViewById(R.id.usernameText);
        passwordText = (TextView)  findViewById(R.id.passwordText);
        loginButton = (Button)  findViewById(R.id.loginButton);
        registerButton = (Button)  findViewById(R.id.registerButton);
    }

    public void loginButtonClicked(View view){

    }
    public void registerButtonClicked(View view){

    }
}

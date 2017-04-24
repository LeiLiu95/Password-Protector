package com.example.passwordprotector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewPasswordActivity extends AppCompatActivity {

    TextView accountNameText;
    TextView passwordText;
    Button backButton;
    Button resetButton;
    TextView secAnswer1;
    TextView secAnswer2;
    TextView secAnswer3;
    Password password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_password);
        accountNameText = (TextView) findViewById(R.id.accountNameText);
        passwordText = (TextView) findViewById(R.id.passwordText);
        backButton = (Button)  findViewById(R.id.backButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        secAnswer1 = (TextView) findViewById(R.id.secAnswer1);
        secAnswer2 = (TextView) findViewById(R.id.secAnswer2);
        secAnswer3 = (TextView) findViewById(R.id.secAnswer3);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String passphrase = intent.getStringExtra("passphrase");
        accountNameText.setText(name);
        DBHandler dbHandler = DBHandler.getInstance(this);
        password = dbHandler.getPassword(passphrase, name);
        passwordText.setText(password.getPassword());
        ArrayList<String> secA = password.getSecurityAnswers();
        if(secA.size() != 0){
            secAnswer1.setText(secA.get(0));
            secAnswer2.setText(secA.get(1));
            secAnswer3.setText(secA.get(2));
        }

//        secAnswer1.setText(password.get(1));
//        secAnswer2.setText(password.get(2));
//        secAnswer3.setText(password.get(3));
    }
    public void backButtonClicked(View view){
        finish();
    }

    public void resetButtonClicked(View view){

    }
}

package com.example.passwordprotector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewPasswordActivity extends AppCompatActivity {

    TextView accountNameText;
    TextView passwordText;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_password);
        accountNameText = (TextView) findViewById(R.id.accountNameText);
        passwordText = (TextView) findViewById(R.id.passwordText);
        backButton = (Button)  findViewById(R.id.backButton);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String passphrase = intent.getStringExtra("passphrase");
        accountNameText.setText(name);
        DBHandler dbHandler = DBHandler.getInstance(this);
        String password = dbHandler.getPassword(passphrase, name);
        passwordText.setText(password);
    }
    public void backButtonClicked(View view){
        finish();
    }
}

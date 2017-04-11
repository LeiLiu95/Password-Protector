package com.example.passwordprotector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView passwordText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        passwordText = (TextView)  findViewById(R.id.passwordText);
        loginButton = (Button)  findViewById(R.id.loginButton);


    }

    public void loginButtonClicked(View view){
        Intent intent = new Intent(this, MainActivity.class);
        String passphrase = passwordText.getText().toString();
        while(true){
            try{
                passphrase = passwordText.getText().toString();
                DBHandler dbHandler = DBHandler.getInstance(this);
                dbHandler.databaseToString(passphrase);
                break;
            }catch(Exception e){
                Toast.makeText(this,"The passphrase entered is not correct.", Toast.LENGTH_SHORT);
            }
        }
        intent.putExtra("passphrase", passphrase);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        passwordText = null;
        loginButton = null;

    }
}

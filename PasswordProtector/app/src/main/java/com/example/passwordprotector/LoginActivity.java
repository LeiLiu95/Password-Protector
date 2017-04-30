
package com.example.passwordprotector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    TextView passwordText; //input for passphase for database encryption
    Button loginButton; //button that starts login process

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initializing the ui stuff
        passwordText = (TextView)  findViewById(R.id.passwordText);
        loginButton = (Button)  findViewById(R.id.loginButton);


    }
    //called when the login button is clicked
    public void loginButtonClicked(View view){
        //creating an intent to pass information and start the app
        Intent intent = new Intent(this, MainActivity.class);
        String passphrase = passwordText.getText().toString();
        //tries to start database to check to see if the passphrase is correct
        try{
            passphrase = passwordText.getText().toString();
            DBHandler dbHandler = DBHandler.getInstance(this);
            dbHandler.databaseToString(passphrase);

        }catch(Exception e){//throws an exception if the passphrase for database was entered incorrectly
            Toast.makeText(LoginActivity.this,"The passphrase entered is not correct.", Toast.LENGTH_SHORT).show();
            return;
        }
        //passphrase was entered correctly so we pass the passphrase to main activity
        intent.putExtra("passphrase", passphrase);
        //start main activity
        startActivity(intent);
        //close this activity
        finish();
    }


    //this is for data management and controlling how much cache is used. probably not needed though
    @Override
    protected void onDestroy() {
        super.onDestroy();
        passwordText = null;
        loginButton = null;

    }
}

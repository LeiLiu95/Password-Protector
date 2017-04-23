package com.example.passwordprotector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class NewPassActivity extends AppCompatActivity {

    TextView accountNameText;
    TextView numberCharText;
    TextView securityQ1;
    TextView securityQ2;
    TextView securityQ3;
    CheckBox specialCharBox;
    CheckBox capLettersBox;
    CheckBox numbersBox;
    Button saveButton;
    String passphrase;
    CheckBox securityQuestionBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        Intent intent = getIntent();
        passphrase = intent.getStringExtra("passphrase");

        saveButton = (Button)  findViewById(R.id.saveButton);
        accountNameText = (TextView)  findViewById(R.id.accountNameText);
        numberCharText = (TextView)  findViewById(R.id.numberCharText);
        securityQ1 = (TextView) findViewById(R.id.securityQ1);
        securityQ2 = (TextView) findViewById(R.id.securityQ2);
        securityQ3 = (TextView) findViewById(R.id.securityQ3);
        securityQ1.setVisibility(View.INVISIBLE);
        securityQ2.setVisibility(View.INVISIBLE);
        securityQ3.setVisibility(View.INVISIBLE);
        specialCharBox = (CheckBox)  findViewById(R.id.specialCharBox);
        capLettersBox = (CheckBox)  findViewById(R.id.capLettersBox);
        numbersBox = (CheckBox)  findViewById(R.id.numbersBox);
        securityQuestionBox = (CheckBox) findViewById(R.id.securityQuestionBox);
        securityQuestionBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    securityQ1.setVisibility(View.VISIBLE);
                    securityQ2.setVisibility(View.VISIBLE);
                    securityQ3.setVisibility(View.VISIBLE);
                }else{
                    securityQ1.setVisibility(View.INVISIBLE);
                    securityQ2.setVisibility(View.INVISIBLE);
                    securityQ3.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    public void saveButtonClicked(View view){
        String name = (String) accountNameText.getText().toString();
        boolean specialChars = specialCharBox.isChecked();
        boolean capLetters = capLettersBox.isChecked();
        boolean numbers = numbersBox.isChecked();
        boolean secQ = securityQuestionBox.isChecked();
        String q1="", q2="", q3="";
        if(secQ){
            q1 = (String) securityQ1.getText().toString();
            q2 = (String) securityQ2.getText().toString();
            q3 = (String) securityQ3.getText().toString();
        }

        String nums = (String) numberCharText.getText().toString();
        int numLetters = Integer.parseInt(nums);
        PasswordOnly passwordOnly = new PasswordOnly();
        DBHandler dbHandler = DBHandler.getInstance(this);
        if(dbHandler.nameInDatabase(passphrase,name)){
            //add a Toast that lets the user know that the account name is taken, and that they need to enter a different one
            Toast.makeText(this, "The username -" + name + "- is taken, please enter a different username.",Toast.LENGTH_SHORT);
        }else{
            Password password = passwordOnly.addPassword(name,numLetters,capLetters,specialChars,numbers);
            password.setSecurityAnswers(q1);
            password.setSecurityAnswers(q2);
            password.setSecurityAnswers(q3);
            dbHandler.addAccount(password, passphrase);
            finish();
        }

    }
}

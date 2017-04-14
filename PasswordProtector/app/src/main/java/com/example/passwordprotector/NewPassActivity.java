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
        specialCharBox = (CheckBox)  findViewById(R.id.specialCharBox);
        capLettersBox = (CheckBox)  findViewById(R.id.capLettersBox);
        numbersBox = (CheckBox)  findViewById(R.id.numbersBox);
        securityQuestionBox = (CheckBox) findViewById(R.id.securityQuestionBox);
    }
    public void saveButtonClicked(View view){
        String name = (String) accountNameText.getText().toString();
        boolean specialChars = specialCharBox.isChecked();
        boolean capLetters = capLettersBox.isChecked();
        boolean numbers = numbersBox.isChecked();
        boolean secQuestions = securityQuestionBox.isChecked();
        String nums = (String) numberCharText.getText().toString();
        int numLetters = Integer.parseInt(nums);
        PasswordOnly passwordOnly = new PasswordOnly();
        DBHandler dbHandler = DBHandler.getInstance(this);
        if(dbHandler.nameInDatabase(passphrase,name)){
            //add a Toast that lets the user know that the account name is taken, and that they need to enter a different one
            Toast.makeText(this, "The username -" + name + "- is taken, please enter a different username.",Toast.LENGTH_SHORT);
        }else{
            Password password = passwordOnly.addPassword(name,numLetters,capLetters,specialChars,numbers);
            dbHandler.addAccount(password, passphrase);
            finish();
        }

    }
}

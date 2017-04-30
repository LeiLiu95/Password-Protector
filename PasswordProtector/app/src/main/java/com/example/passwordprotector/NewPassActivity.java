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

    TextView accountNameText; //input for name of account
    TextView numberCharText; //input for length of password
    TextView securityQ1; //input for security answers
    TextView securityQ2; //^^
    TextView securityQ3; //^^
    CheckBox specialCharBox; //checkbox for whether special characters are in password
    CheckBox capLettersBox; //checkbox for whether capital letters are in password
    CheckBox numbersBox; //checkbox for whether numbers are in password
    Button saveButton; //button to save password
    String passphrase; //passphrase for database encryption
    CheckBox securityQuestionBox; //checkbox for whether user wants to save security questions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        //retreiving data sent from main activity
        Intent intent = getIntent();
        passphrase = intent.getStringExtra("passphrase");
        //initializing ui stuff
        saveButton = (Button)  findViewById(R.id.saveButton);
        accountNameText = (TextView)  findViewById(R.id.accountNameText);
        numberCharText = (TextView)  findViewById(R.id.numberCharText);
        securityQ1 = (TextView) findViewById(R.id.securityQ1);
        securityQ2 = (TextView) findViewById(R.id.securityQ2);
        securityQ3 = (TextView) findViewById(R.id.securityQ3);
        //setting these to invisible unless the checkbox is selected
        securityQ1.setVisibility(View.INVISIBLE);
        securityQ2.setVisibility(View.INVISIBLE);
        securityQ3.setVisibility(View.INVISIBLE);
        specialCharBox = (CheckBox)  findViewById(R.id.specialCharBox);
        capLettersBox = (CheckBox)  findViewById(R.id.capLettersBox);
        numbersBox = (CheckBox)  findViewById(R.id.numbersBox);
        securityQuestionBox = (CheckBox) findViewById(R.id.securityQuestionBox);
        //when checkbox is clicked the text entries become visible
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
        String name =  accountNameText.getText().toString();
        boolean specialChars = specialCharBox.isChecked();
        boolean capLetters = capLettersBox.isChecked();
        boolean numbers = numbersBox.isChecked();
        boolean secQ = securityQuestionBox.isChecked();
        String q1="";
        String q2="";
        String q3="";
        if(secQ){
            q1 = securityQ1.getText().toString();
            q2 = securityQ2.getText().toString();
            q3 = securityQ3.getText().toString();
        }
        int numLetters=0;
        try{
            String nums = numberCharText.getText().toString();
            numLetters = Integer.parseInt(nums);
        }catch(Exception e){
            Toast.makeText(NewPassActivity.this,"Please insert a valid number", Toast.LENGTH_SHORT).show();
        }

        PasswordOnly passwordOnly = new PasswordOnly();
        DBHandler dbHandler = DBHandler.getInstance(this);
        if(name.equals("")){
            Toast.makeText(NewPassActivity.this,"Please name the account.", Toast.LENGTH_SHORT).show();
        }else if(numLetters < 8){
            Toast.makeText(NewPassActivity.this,"Please set password length to greater than 8", Toast.LENGTH_SHORT).show();
        }else if(numLetters > 32){
            Toast.makeText(NewPassActivity.this,"Please set password length to less than 32", Toast.LENGTH_SHORT).show();
        }
        else if(dbHandler.nameInDatabase(passphrase,name)){
            //add a Toast that lets the user know that the account name is taken, and that they need to enter a different one
            Toast.makeText(NewPassActivity.this, "The username -" + name + "- is taken, please enter a different username.",Toast.LENGTH_SHORT).show();
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

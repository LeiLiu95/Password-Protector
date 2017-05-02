
package com.example.passwordprotector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    TextView newAccountNameBox; //input for account name changes
    TextView passwordLengthBox; //input for length of password
    TextView answer1Text; //input for change in security answers
    TextView answer2Text; //^^
    TextView answer3Text; //^^
    Button saveButton; //button to save information
    CheckBox numBox; //checkbox determining whether new password will contain numbers
    CheckBox specCharBox; //checkbox for whether new password will contain special characters
    CheckBox capLetterBox; //checkbox for whether new password will contain capital letters
    String passphrase, accountName, secAnswer1, secAnswer2, secAnswer3, password; //strings for receiving passed in data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent(); //past activity sent information upon requesting this activity to start, so this is to get that info
        passphrase = intent.getStringExtra("passphrase"); //getting passphrase for database
        accountName = intent.getStringExtra("accountname"); 
        secAnswer1 = intent.getStringExtra("secanswer1"); 
        secAnswer2 = intent.getStringExtra("secanswer2");
        secAnswer3 = intent.getStringExtra("secanswer3");
        password = intent.getStringExtra("password");

        //declaring the ui parts by their names in xml
        newAccountNameBox = (TextView) findViewById(R.id.newAccountNameBox);
        passwordLengthBox = (TextView) findViewById(R.id.passwordLengthBox);
        answer1Text = (TextView) findViewById(R.id.answer1Text);
        answer2Text = (TextView) findViewById(R.id.answer2Text);
        answer3Text = (TextView) findViewById(R.id.answer3Text);
        saveButton = (Button) findViewById(R.id.saveButton);
        numBox = (CheckBox) findViewById(R.id.numBox);
        specCharBox = (CheckBox) findViewById(R.id.specCharBox);
        capLetterBox = (CheckBox) findViewById(R.id.capLetterBox);

        //setting the text to strings from what they are previously before edit request
        answer1Text.setText(secAnswer1);
        answer2Text.setText(secAnswer2);
        answer3Text.setText(secAnswer3);
        newAccountNameBox.setText(accountName);

    }

    //when save button is clicked, this method is called
    public void saveButtonClicked(View view){
        //object for random generation of password
        PasswordOnly passwordOnly = new PasswordOnly();
        //string to determine if generating a new password is required
        String s = passwordLengthBox.getText().toString();
        DBHandler dbHandler = DBHandler.getInstance(this);//getting instance of the database handler
        if(newAccountNameBox.getText().toString().equals("")){//checks to see if no account name was inserted
            Toast.makeText(EditActivity.this,"No account was selected. Please select an account to open.", Toast.LENGTH_SHORT).show();
        }
        else if(!s.equals("")){//an input for password length was received
            int num = Integer.parseInt(s);
            if(num < 8){//checking for password length of less than 8
                Toast.makeText(EditActivity.this,"Please set password length to greater than 8", Toast.LENGTH_SHORT).show();
            }else if(num > 32){//checking for password length of greater than 32
                Toast.makeText(EditActivity.this,"Please set password length to less than 32", Toast.LENGTH_SHORT).show();
            }else{//password length is gucci
                //generating the randomized password
                Password password = passwordOnly.createPassword(newAccountNameBox.getText().toString(), num, capLetterBox.isChecked(), specCharBox.isChecked(), numBox.isChecked());
                //declaring and setting up the list of security answers
                ArrayList<String> list = new ArrayList<>();
                list.add(secAnswer1);
                list.add(secAnswer2);
                list.add(secAnswer3);
                //checking to see if security answers have changed and if they have, then changing them in the list to new values
                if(secAnswer1.equals(answer1Text.getText().toString())){
                    list.set(0, answer1Text.getText().toString());
                }
                if(secAnswer2.equals(answer2Text.getText().toString())){
                    list.set(1, answer2Text.getText().toString());
                }
                if(secAnswer3.equals(answer3Text.getText().toString())){
                    list.set(2, answer3Text.getText().toString());
                }
                password.addSecutityAnswers(list);
                //sending to the database to have the account editted
                dbHandler.editPassword(passphrase,password,accountName);
            }

        }else{//no input was found for password length, so no new password
            //creating password object for editting
            Password pass = new Password();
            pass.setAccountName(accountName);

            ArrayList<String> list = new ArrayList<>();
            list.add(secAnswer1);
            list.add(secAnswer2);
            list.add(secAnswer3);
            //checking to see if any of the old information has changed and changing it if needed
            if(!accountName.equals(newAccountNameBox.getText().toString())){
                pass.setAccountName(newAccountNameBox.getText().toString());
            }
            if(!secAnswer1.equals(answer1Text.getText().toString())){
                list.set(0, answer1Text.getText().toString());
            }
            if(!secAnswer2.equals(answer2Text.getText().toString())){
                list.set(1, answer2Text.getText().toString());
            }
            if(!secAnswer3.equals(answer3Text.getText().toString())){
                list.set(2, answer3Text.getText().toString());
            }
            pass.addSecutityAnswers(list);
            pass.setPassword(password);
            //sending password object to database for editting
            dbHandler.editPassword(passphrase,pass,accountName);
        }

        finish();
    }
}

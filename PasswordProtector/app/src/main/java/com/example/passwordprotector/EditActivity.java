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

    TextView newAccountNameBox;
    TextView passwordLengthBox;
    TextView answer1Text;
    TextView answer2Text;
    TextView answer3Text;
    Button saveButton;
    CheckBox numBox;
    CheckBox specCharBox;
    CheckBox capLetterBox;
    String passphrase, accountName, secAnswer1, secAnswer2, secAnswer3, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        passphrase = intent.getStringExtra("passphrase");
        accountName = intent.getStringExtra("accountname");
        secAnswer1 = intent.getStringExtra("secanswer1");
        secAnswer2 = intent.getStringExtra("secanswer2");
        secAnswer3 = intent.getStringExtra("secanswer3");
        password = intent.getStringExtra("password");

        newAccountNameBox = (TextView) findViewById(R.id.newAccountNameBox);
        passwordLengthBox = (TextView) findViewById(R.id.passwordLengthBox);
        answer1Text = (TextView) findViewById(R.id.answer1Text);
        answer2Text = (TextView) findViewById(R.id.answer2Text);
        answer3Text = (TextView) findViewById(R.id.answer3Text);
        saveButton = (Button) findViewById(R.id.saveButton);
        numBox = (CheckBox) findViewById(R.id.numBox);
        specCharBox = (CheckBox) findViewById(R.id.specCharBox);
        capLetterBox = (CheckBox) findViewById(R.id.capLetterBox);

        answer1Text.setText(secAnswer1);
        answer2Text.setText(secAnswer2);
        answer3Text.setText(secAnswer3);
        newAccountNameBox.setText(accountName);

    }
    public void saveButtonClicked(View view){
        PasswordOnly passwordOnly = new PasswordOnly();
        String s = passwordLengthBox.getText().toString();
        DBHandler dbHandler = DBHandler.getInstance(this);
        if(newAccountNameBox.getText().toString().equals("")){
            Toast.makeText(EditActivity.this,"No account was selected. Please select an account to open.", Toast.LENGTH_SHORT).show();
        }
        else if(!s.equals("")){
            int num = Integer.parseInt(s);
            Password password = passwordOnly.createPassword(newAccountNameBox.getText().toString(), num, capLetterBox.isChecked(), specCharBox.isChecked(), numBox.isChecked());
            ArrayList<String> list = new ArrayList<>();
            list.add(secAnswer1);
            list.add(secAnswer2);
            list.add(secAnswer3);
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
            dbHandler.editPassword(passphrase,password,accountName);
        }else{
            Password pass = new Password();
            pass.setAccountName(accountName);
            ArrayList<String> list = new ArrayList<>();
            list.add(secAnswer1);
            list.add(secAnswer2);
            list.add(secAnswer3);
            if(!accountName.equals(newAccountNameBox.getText().toString())){
                pass.setAccountName(newAccountNameBox.getText().toString());
            }
            if(secAnswer1.equals(answer1Text.getText().toString())){
                list.set(0, answer1Text.getText().toString());
            }
            if(secAnswer2.equals(answer2Text.getText().toString())){
                list.set(1, answer2Text.getText().toString());
            }
            if(secAnswer3.equals(answer3Text.getText().toString())){
                list.set(2, answer3Text.getText().toString());
            }
            pass.addSecutityAnswers(list);
            pass.setPassword(password);
            dbHandler.editPassword(passphrase,pass,accountName);
        }

        finish();
    }
}

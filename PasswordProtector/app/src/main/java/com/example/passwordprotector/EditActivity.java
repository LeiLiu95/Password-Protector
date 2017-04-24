package com.example.passwordprotector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    String passphrase, accountName, secAnswer1, secAnswer2, secAnswer3;

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
        int num = Integer.parseInt(passwordLengthBox.getText().toString());
        Password password = passwordOnly.createPassword(newAccountNameBox.getText().toString(), num, capLetterBox.isChecked(), specCharBox.isChecked(), numBox.isChecked());
        DBHandler dbHandler = DBHandler.getInstance(this);
        dbHandler.editPassword(passphrase, accountName, newAccountNameBox.getText().toString(), password);
        finish();
    }
}

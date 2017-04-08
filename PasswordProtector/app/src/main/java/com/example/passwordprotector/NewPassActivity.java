package com.example.passwordprotector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class NewPassActivity extends AppCompatActivity {

    TextView accountNameText;
    TextView numberCharText;
    CheckBox specialCharBox;
    CheckBox capLettersBox;
    CheckBox numbersBox;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pass);
        saveButton = (Button)  findViewById(R.id.saveButton);
        accountNameText = (TextView)  findViewById(R.id.accountNameText);
        numberCharText = (TextView)  findViewById(R.id.numberCharText);
        specialCharBox = (CheckBox)  findViewById(R.id.specialCharBox);
        capLettersBox = (CheckBox)  findViewById(R.id.capLettersBox);
        numbersBox = (CheckBox)  findViewById(R.id.numbersBox);
    }
    public void saveButtonClicked(View view){
        String name = (String) accountNameText.getText();
        boolean specialChars = specialCharBox.isEnabled();
        boolean capLetters = capLettersBox.isEnabled();
        boolean numbers = numbersBox.isEnabled();
        String nums = (String) numberCharText.getText();
        int numLetters = Integer.parseInt(nums);
        PasswordOnly passwordOnly = new PasswordOnly();
        Password password = passwordOnly.addPassword(name,numLetters,capLetters,specialChars,numbers);
        String passphrase = Popup.getPassphrase(this);
        DBHandler dbHandler = DBHandler.getInstance(this,null,null,1);
        dbHandler.addAccount(password, passphrase);
    }
}

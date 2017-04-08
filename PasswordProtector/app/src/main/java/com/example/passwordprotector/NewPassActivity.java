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

    }
}

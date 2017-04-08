package com.example.passwordprotector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView accountDisplay;
    DBHandler dbHandler;
    Button createButton;
    Button deleteButton;
    Button openButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountDisplay = (ListView) findViewById(R.id.accountDisplay);
        createButton = (Button)  findViewById(R.id.createButton);
        deleteButton = (Button)  findViewById(R.id.deleteButton);
        openButton = (Button)  findViewById(R.id.openButton);
        dbHandler = DBHandler.getInstance(this,null,null,1);
        printDatabase();
    }
    public void createButtonClicked(View view){
        Intent intent = new Intent(MainActivity.this, NewPassActivity.class);
        startActivity(intent);
    }

    public void deleteButtonClicked(View view){
        final String accName = (String) accountDisplay.getSelectedItem();
    }
    public void openButtonClicked(View view){
        final String accName = (String) accountDisplay.getSelectedItem();
        Intent appInfo = new Intent(MainActivity.this, ViewPasswordActivity.class);
        appInfo.putExtra("name", accName);
        startActivity(appInfo);
    }
    private void printDatabase(){
        String dbString = dbHandler.databaseToString();
        //accountDisplay.setText(dbString);
    }
}

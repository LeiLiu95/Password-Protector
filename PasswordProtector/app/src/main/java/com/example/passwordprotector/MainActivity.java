package com.example.passwordprotector;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView accountDisplay;
    DBHandler dbHandler;
    Button createButton;
    Button deleteButton;
    Button openButton;
    String passphrase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        passphrase = intent.getStringExtra("passphrase");
        //Bundle b = intent.getExtras();

//        if(b!= null){
//            passphrase = (String) b.get("passphrase");
//        }
        accountDisplay = (ListView) findViewById(R.id.accountDisplay);
        createButton = (Button)  findViewById(R.id.createButton);
        deleteButton = (Button)  findViewById(R.id.deleteButton);
        openButton = (Button)  findViewById(R.id.openButton);
        //SQLiteDatabase.loadLibs(this);
        dbHandler = DBHandler.getInstance(this);
        printDatabase(passphrase);
    }
    public void createButtonClicked(View view){
        Intent intent = new Intent(MainActivity.this, NewPassActivity.class);
        intent.putExtra("passphrase", passphrase);
        startActivity(intent);
    }

    public void deleteButtonClicked(View view){
        final String accName = (String) accountDisplay.getSelectedItem();
    }

    public void openButtonClicked(View view){
        final String accName = (String) accountDisplay.getSelectedItem();

        Intent appInfo = new Intent(MainActivity.this, ViewPasswordActivity.class);
        appInfo.putExtra("name", accName);
        appInfo.putExtra("passphrase",passphrase);
        startActivity(appInfo);
    }
    private void printDatabase(String passphrase){
        ArrayList<String> dbString = dbHandler.databaseToString(passphrase);
        String[] arr = new String[dbString.size()];
        for(int i = 0; i < dbString.size(); i++){
            arr[i] = dbString.get(i);
        }
        accountDisplay.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, arr));
    }

    @Override
    protected void onResume() {
        super.onResume();
        printDatabase(passphrase);
    }
}



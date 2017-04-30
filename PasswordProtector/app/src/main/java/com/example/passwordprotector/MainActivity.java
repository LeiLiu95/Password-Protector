package com.example.passwordprotector;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView accountDisplay; //listview for displaying the list of accounts
    DBHandler dbHandler; //handler object used for database manipulation
    Button createButton; //button to create new account
    Button deleteButton; //button used to open account and get information for that account
    Button openButton; //button used to edit which ever account is selected
    String passphrase; //passphrase for database encryption
    String selected; //string for whichever account is selected in listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //receiving information from login activity
        Intent intent = getIntent();
        passphrase = intent.getStringExtra("passphrase");
        //initializing ui stuff
        accountDisplay = (ListView) findViewById(R.id.accountDisplay);
        //method for setting selected string to correct account whenever a list item is selected
        accountDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                selected =(String) (accountDisplay.getItemAtPosition(myItemInt));
                myView.setSelected(true);
            }
        });

        createButton = (Button)  findViewById(R.id.createButton);
        deleteButton = (Button)  findViewById(R.id.deleteButton);
        openButton = (Button)  findViewById(R.id.openButton);
        dbHandler = DBHandler.getInstance(this);
        printDatabase(passphrase);
    }
    
    public void createButtonClicked(View view){
        Intent intent = new Intent(MainActivity.this, NewPassActivity.class);
        intent.putExtra("passphrase", passphrase);
        startActivity(intent);
    }

    public void deleteButtonClicked(View view){
        if(selected != null){
            dbHandler.deleteAccount(selected,passphrase);
            printDatabase(passphrase);
        }else{
            Toast.makeText(MainActivity.this,"No account was selected. Please select an account to delete.", Toast.LENGTH_SHORT).show();
        }

    }

    public void openButtonClicked(View view){
        if(selected != null){
            Intent appInfo = new Intent(MainActivity.this, ViewPasswordActivity.class);
            appInfo.putExtra("name", selected);
            appInfo.putExtra("passphrase",passphrase);
            startActivity(appInfo);
        }else{
            Toast.makeText(MainActivity.this,"No account was selected. Please select an account to open.", Toast.LENGTH_SHORT).show();
        }

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

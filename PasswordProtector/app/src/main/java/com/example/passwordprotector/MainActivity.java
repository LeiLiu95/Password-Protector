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
        //initializing the list view and adding the accounts in the database into list
        printDatabase(passphrase);
    }
    //called whenever create button is clicked
    public void createButtonClicked(View view){
        //starting a new intent to go to newpassactivity to create a new password
        Intent intent = new Intent(MainActivity.this, NewPassActivity.class);
        intent.putExtra("passphrase", passphrase); //passing the passphrase for database to the new activity
        startActivity(intent); //starting new activity
    }
    //called whenever delete button is clicked 
    public void deleteButtonClicked(View view){
        if(selected != null){//checks to verify a list item is selected
            dbHandler.deleteAccount(selected,passphrase);//deletes account from database
            printDatabase(passphrase);//reinitializes the listview
        }else{//no item in listview was selected
            Toast.makeText(MainActivity.this,"No account was selected. Please select an account to delete.", Toast.LENGTH_SHORT).show();
        }

    }
    //called whenever the open button is clicked
    public void openButtonClicked(View view){
        //check to verify a list item is selected
        if(selected != null){
            //creating new intent to go to view activity
            Intent appInfo = new Intent(MainActivity.this, ViewPasswordActivity.class);
            appInfo.putExtra("name", selected);//passing name of account
            appInfo.putExtra("passphrase",passphrase);//passing passphrase for database encryption
            startActivity(appInfo);//starting activity
        }else{//no item in listview was selected
            Toast.makeText(MainActivity.this,"No account was selected. Please select an account to open.", Toast.LENGTH_SHORT).show();
        }

    }
    //adds account names as list items into the listview
    private void printDatabase(String passphrase){
        //gets arraylist of strings of account names
        ArrayList<String> dbString = dbHandler.databaseToString(passphrase);
        //converts the arraylist to an array
        String[] arr = new String[dbString.size()];
        for(int i = 0; i < dbString.size(); i++){
            arr[i] = dbString.get(i);
        }
        //sets it into the listview
        accountDisplay.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, arr));
    }
    //called whenever returning to this activity from opening or editting
    @Override
    protected void onResume() {
        super.onResume();
        //reprints database so any changes are made to ui
        printDatabase(passphrase);
    }
}

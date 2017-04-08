package com.example.passwordprotector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    ListView accountDisplay;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountDisplay = (ListView) findViewById(R.id.accountDisplay);
        dbHandler = DBHandler.getInstance(this,null,null,1);
        printDatabase();
    }
    public void createButtonClicked(View view){

    }

    public void deleteButtonClicked(View view){

    }
    private void printDatabase(){
        String dbString = dbHandler.databaseToString();
        //accountDisplay.setText(dbString);
    }
}

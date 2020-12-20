package com.parthapp.simpletodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText editItem;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editItem = findViewById(R.id.etItem);
        saveButton = findViewById(R.id.saveBtn);

        getSupportActionBar().setTitle("Edit Item");

        editItem.setText(getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT));

        //save the edit item
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create an intent which will contain the results
                Intent i = new Intent();

                //pass the data
                i.putExtra(MainActivity.KEY_ITEM_TEXT, editItem.getText().toString());
                i.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                //set the result fo the intent
                setResult(RESULT_OK, i);

                //finish activity, close screen
                finish();
            }
        });
    }
}
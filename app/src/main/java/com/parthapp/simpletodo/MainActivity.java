package com.parthapp.simpletodo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
    ArrayList<String> items;
    Button btnAdd;
    EditText addItem;
    RecyclerView viewItems;
    adaptItems itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        addItem = findViewById(R.id.addItem);
        viewItems = findViewById(R.id.viewItems);

        loadItems();

        adaptItems.OnLongClickListener onLongClickListener = new adaptItems.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
        adaptItems.OnClickListner clickListener = new adaptItems.OnClickListner() {
            @Override
            public void OnItemClicked(int position) {
                //create the new activity
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                // pass the data being edited in the ArrayList
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);
                //display the activity
                startActivityForResult(i,EDIT_TEXT_CODE);
            }
        };
        itemsAdapter = new adaptItems(items, onLongClickListener, clickListener);
        viewItems.setAdapter(itemsAdapter);
        viewItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = addItem.getText().toString();
                items.add(item);
                itemsAdapter.notifyItemInserted(items.size()-1);
                addItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    //handle the result of the edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE) {
            //retrieve the updated text value
            String item = data.getStringExtra(KEY_ITEM_TEXT);
            //extract the original item position from position key
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            //update the model with the right position with changed item
            items.set(position, item);

            //notify the adapter
            itemsAdapter.notifyItemChanged(position);

            //save items
            saveItems();

            //notify user
            Toast.makeText(getApplicationContext(), "Item was updated", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "Unknown call to onActivity Result");
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }
        
    //function to load the items in the file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading Items", e);
            items = new ArrayList<>();
        }

    }

    //function to save the items in the files
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing Items", e);
        }
    }
}
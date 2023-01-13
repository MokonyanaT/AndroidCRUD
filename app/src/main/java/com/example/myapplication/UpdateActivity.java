package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText title_input, author_input,pages_input;
    Button update_button,delete_button;
    String id, title, author, pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);



        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        // WE first call this methods before to get all data
        getAndSetIntentData();
        // set action bar title by the data we got dynamically
        ActionBar ab = getSupportActionBar();
        if (ab !=null){
            ab.setTitle(title.toUpperCase());
        }else {
            ab.setTitle("Update Book");
        }
        update_button = findViewById(R.id.update_button);

        update_button.setOnClickListener(view -> {
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            //and only then we can call this method
            myDB.updateData (id, title_input.getText().toString().trim(),
                    author_input.getText().toString().trim(),
                    pages_input.getText().toString().trim());
        });
        delete_button = findViewById(R.id.delete_button);
        delete_button.setOnClickListener(view -> {
            confirmDialog();

        });

    }

    void getAndSetIntentData(){
        // Checking data availability
        if(getIntent().hasExtra("id") &&
                getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") &&
                getIntent().hasExtra("pages")){
            //Getting data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");
            // setting Intent data

            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);

        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage( "Are you really sure you want to erase " +title+ " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper( UpdateActivity.this);
                myDB.deleteOneRow(id);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        builder.create().show();
    }

}
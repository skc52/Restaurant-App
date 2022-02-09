package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateNote extends AppCompatActivity {

    Button back, update;
    EditText note;

    final static String KEY_ID= "KEY_ID";
    final static String KEY_NOTE= "KEY_NOTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        back = findViewById(R.id.goBackFromUpdateNote);
        update = findViewById(R.id.updateNoteBtn);
        note = findViewById(R.id.updateNote);

        int id = getIntent().getIntExtra(KEY_ID, 0);
        int idOrder = getIntent().getIntExtra("id", 0);

        String notes = getIntent().getStringExtra(KEY_NOTE);
        String[] notesList = notes.split(",");
        note.setText(notesList[id]);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        OrderDataBase orderDataBase = new OrderDataBase(UpdateNote.this);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String notes = note.getText().toString();
                notesList[id] = notes;
                orderDataBase.updateNote(idOrder, notesList);


                Intent i = new Intent(UpdateNote.this, OrdersListActivity.class);
                startActivity(i);
            }
        });

    }
}
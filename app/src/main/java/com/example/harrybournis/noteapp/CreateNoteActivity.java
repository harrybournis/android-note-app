package com.example.harrybournis.noteapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

import models.Note;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.NotesService;

public class CreateNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        final Button createButton = (Button) findViewById(R.id.btn_save);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNote();
            }
        });
    }

    private void createNote() {
        String content = ((EditText) findViewById(R.id.new_note_field)).getText().toString();
        Date date = new Date();

        NotesApi notesApi = NotesService.getIntance().getNotesApi();

        notesApi.createNote(new Note(content, date)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showToast(R.string.msg_note_created);
                    showMainActivity();
                } else {
                    Log.d("ERROR", response.message());
                    Log.d("ERROR", String.valueOf(response.code()));
                    showToast(R.string.msg_note_creation_error);
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast(R.string.msg_server_error);
            }
        });
    }

    private void showToast(int msgString) {
        Toast.makeText(this, msgString, Toast.LENGTH_SHORT).show();
    }

    private void showMainActivity() {
        Intent startIntent = new Intent(this, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(startIntent);
    }
}

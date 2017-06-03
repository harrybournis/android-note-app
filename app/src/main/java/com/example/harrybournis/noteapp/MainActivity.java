package com.example.harrybournis.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import models.Note;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import serialization.NoteDeserializer;
import serialization.NoteSerializer;

public class MainActivity extends AppCompatActivity {

    private NotesApi notesApi;
    private List<Note> notes;
    private ListView notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreatePage();
            }
        });

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Note.class, new NoteSerializer());
        gsonBuilder.registerTypeAdapter(Note.class, new NoteDeserializer());
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NotesApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        notesApi = retrofit.create(NotesApi.class);

        notesList = (ListView) findViewById(R.id.notes_list);
        getNotesFromServer();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void getNotesFromServer() {
        notesApi.getNotes()
                .enqueue(new Callback<List<Note>>() {
                    @Override
                    public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                        Log.d("stuff", response.message());
                        if (response.isSuccessful()) {
                            notes = response.body();
                            notesList.setAdapter(new ArrayAdapter<Note>(MainActivity.this, android.R.layout.simple_list_item_1, notes));
                        } else {
                            showToast(R.string.msg_get_notes_error);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Note>> call, Throwable t) {
                       showToast(R.string.msg_server_error);
                    }
                });
    }

    private void showToast(int msgString) {
        Toast.makeText(this, msgString, Toast.LENGTH_SHORT).show();
    }

    private void showCreatePage() {
        Intent intent = new Intent(this, CreateNoteActivity.class);
        startActivity(intent);
        return;
    }
}

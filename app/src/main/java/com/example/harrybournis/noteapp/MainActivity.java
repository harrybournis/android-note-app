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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.Note;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import serialization.NoteDeserializer;
import serialization.NoteSerializer;
import services.NotesService;

public class MainActivity extends AppCompatActivity {

    private NotesApi notesApi;
    private List<Note> notes;
    private ListView notesList;

    private final SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy - hh:mm:ss");

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


        NotesService notesService = NotesService.getIntance();
        notesApi = notesService.getNotesApi();

        notesList = (ListView) findViewById(R.id.notes_list);
        getNotesFromServer();
    }

    private void getNotesFromServer() {
        notesApi.getNotes()
                .enqueue(new Callback<List<Note>>() {
                    @Override
                    public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                        if (response.isSuccessful()) {
                            notes = response.body();
                            List<String> noteList = new ArrayList<String>();

                            Collections.sort(notes, new Comparator<Note>() {
                                public int compare(Note note1, Note note2) {
                                    return note1.getDate().compareTo(note2.getDate());
                                }
                            });

                            for (Note note : notes) {
                                String result = dt.format(note.getDate()) + "\n" + note.getContent();
                                noteList.add(result);
                            }
                            notesList.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, noteList));

                            ListView notesListView = (ListView) findViewById(R.id.notes_list);
                            notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (notes != null) {
                                        Note note = notes.get(position);
                                        startActivity(ShowNoteActivity.getIntent(MainActivity.this, dt.format(note.getDate()), note.getContent(), note.getId()));
                                    }
                                }
                            });
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
    }
}

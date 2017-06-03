package com.example.harrybournis.noteapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import models.Note;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.example.harrybournis.noteapp.NotesApi.NOTES_URL;

/**
 * Created by harrybournis on 02/06/17.
 */

public interface NotesApi {
    static final String BASE_URL = "http://notes-sdmdcity.rhcloud.com/rest/";
    static final String NOTES_URL = "notes";

    @GET(NOTES_URL)
    Call<List<Note>> getNotes();

    @POST(NOTES_URL)
    Call<Void> createNote(@Body Note note);

    @DELETE(NOTES_URL + "/{note_id}")
    Response deleteNote(@Path("note_id") int noteId);
}

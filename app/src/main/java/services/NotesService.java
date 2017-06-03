package services;

import android.widget.Toast;

import com.example.harrybournis.noteapp.NotesApi;
import com.example.harrybournis.noteapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import models.Note;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import serialization.NoteDeserializer;
import serialization.NoteSerializer;

import static okhttp3.internal.Internal.instance;

/**
 * Created by harrybournis on 03/06/17.
 */

public class NotesService{
   private static NotesService instance = null;
   private NotesApi notesApi;

   private NotesService() {
      GsonBuilder gsonBuilder = new GsonBuilder();
      gsonBuilder.registerTypeAdapter(Note.class, new NoteSerializer());
      gsonBuilder.registerTypeAdapter(Note.class, new NoteDeserializer());
      Gson gson = gsonBuilder.create();

      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(HttpLoggingInterceptor.Level.BODY);
      OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
      httpClient.addInterceptor(logging);  // <-- this is the important line!

      Retrofit retrofit = new Retrofit.Builder()
         .baseUrl(NotesApi.BASE_URL)
         .addConverterFactory(GsonConverterFactory.create(gson))
         .client(httpClient.build())
         .build();

      notesApi = retrofit.create(NotesApi.class);
   }

   public static NotesService getIntance() {
      if (instance == null)
         instance = new NotesService();
      return instance;
   }

   public NotesApi getNotesApi() {
      return notesApi;
   }
}

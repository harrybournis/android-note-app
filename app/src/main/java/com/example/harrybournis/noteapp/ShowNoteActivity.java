package com.example.harrybournis.noteapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.NotesService;

public class ShowNoteActivity extends AppCompatActivity {

    private static final String EXTRA_DATE = "date";
    private static final String EXTRA_CONTENT = "content";
    private static final String EXTRA_ID = "id";

    private int id;

    public static Intent getIntent(Context context, String date, String content, int id) {
        Intent intent = new Intent(context, ShowNoteActivity.class);
        intent.putExtra(EXTRA_DATE, date);
        intent.putExtra(EXTRA_CONTENT, content);
        intent.putExtra(EXTRA_ID, id);

        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        final NotesApi notesApi = NotesService.getIntance().getNotesApi();

        Intent intent = getIntent();
        String date = intent.getStringExtra(EXTRA_DATE);
        String content = intent.getStringExtra(EXTRA_CONTENT);
        id = intent.getIntExtra(EXTRA_ID, -1);

        TextView txDate = (TextView) findViewById(R.id.date);
        TextView txContent = (TextView) findViewById(R.id.content);

        txDate.setText(date);
        txContent.setText(content);

        Button deleteBtn = (Button) findViewById(R.id.btn_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesApi.deleteNote(id).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            showToast(R.string.msg_note_deleted);
                            showMainActivity();
                        } else {
                            showToast(R.string.msg_note_deletion_error);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        showToast(R.string.msg_server_error);
                    }
                });

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

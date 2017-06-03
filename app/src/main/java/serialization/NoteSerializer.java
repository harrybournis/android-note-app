package serialization;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import models.Note;

/**
 * Created by harrybournis on 03/06/17.
 */

public class NoteSerializer implements JsonSerializer<Note> {
    @Override
    public JsonElement serialize(Note note, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonNote = new JsonObject();

        //jsonNote.addProperty("id", note.getId());
        jsonNote.addProperty("content", note.getContent());
        jsonNote.addProperty("date", note.getDate().getTime());

        return jsonNote;
    }
}

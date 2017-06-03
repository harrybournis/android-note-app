package serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import models.Note;

/**
 * Created by harrybournis on 03/06/17.
 */

public class NoteDeserializer implements JsonDeserializer<Note> {
    @Override
    public Note deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jObject = json.getAsJsonObject();

        JsonElement element;
        Note note = new Note();

        element = jObject.get("id");
        if (element != null)
            note.setId(element.getAsInt());

        element = jObject.get("content");
        if (element != null)
            note.setContent(element.getAsString());

        element = jObject.get("date");
        if (element != null) {
            String dateString = element.getAsString();
            long dateLong = Long.parseLong(dateString);
            note.setDate(new Date(dateLong));
        }
        return note;
    }
}

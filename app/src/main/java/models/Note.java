package models;

import java.util.Date;

/**
 * Created by harrybournis on 02/06/17.
 */

public class Note {
    private int id;
    private String content;
    private Date date;

    public Note(){
    }
    public Note(int id, String content, Date date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public Note(String content, Date date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

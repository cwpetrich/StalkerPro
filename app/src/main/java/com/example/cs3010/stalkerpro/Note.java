package com.example.cs3010.stalkerpro;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Conrad on 4/22/2015.
 */
public class Note {
    // main data fields
    public UUID nuuid;
    public UUID puuid;
    public String note;
    public String created_at;
    public String modified_at;

    // make an empty profile
    public Note() {
        nuuid = UUID.randomUUID();
        puuid = UUID.randomUUID(); //This should be auto set to the puuid of unknown person.
        note = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date();
        created_at = dateFormat.format(date);
        modified_at = dateFormat.format(date);
    }

    public Note clone(Note note)
    {
        Note newNote = new Note();
        newNote.nuuid = note.nuuid;
        newNote.puuid = note.puuid;
        newNote.note = note.note;
        newNote.created_at = note.created_at;
        newNote.modified_at = note.modified_at;
        return newNote;
    }
}

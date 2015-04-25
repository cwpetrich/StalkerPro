package com.example.cs3010.stalkerpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Conrad on 4/22/2015.
 */
public class NoteDatabaseAdapter {
    DatabaseSchema dbSchema;
    Context context;

    public NoteDatabaseAdapter(Context context)
    {
        this.context = context;
        dbSchema = new DatabaseSchema(context);
    }

    public long insertNote(Note note)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.nuuid, String.valueOf(note.nuuid));
        contentValues.put(DatabaseSchema.puuid, String.valueOf(note.puuid));
        contentValues.put(DatabaseSchema.note, note.note);
        contentValues.put(DatabaseSchema.created_at, note.created_at);
        contentValues.put(DatabaseSchema.modified_at, note.modified_at);
        long id = db.insert(DatabaseSchema.NOTES_TABLE_NAME, null, contentValues);
        return id;
    }

    public long insertPerson(Person person)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.puuid, String.valueOf(person.puuid));
        contentValues.put(DatabaseSchema.name, person.name);
        contentValues.put(DatabaseSchema.created_at, person.created_at);
        contentValues.put(DatabaseSchema.modified_at, person.modified_at);
        long id = db.insert(DatabaseSchema.PEOPLE_TABLE_NAME, null, contentValues);
        return id;
    }

    public int deleteNoteRow(UUID nuuid)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] whereArgs = {String.valueOf(nuuid)};
        int count = db.delete(DatabaseSchema.NOTES_TABLE_NAME, DatabaseSchema.nuuid + " =?", whereArgs);
        return count;
    }

    public int deletePersonRow(UUID puuid)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] whereArgs = {String.valueOf(puuid)};
<<<<<<< HEAD
        int pcount = db.delete(DatabaseSchema.PEOPLE_TABLE_NAME, DatabaseSchema.puuid + " =?", whereArgs);
        int ncount = db.delete(DatabaseSchema.NOTES_TABLE_NAME, DatabaseSchema.puuid + " =?",whereArgs);
        return pcount+ncount;
=======
        int count = db.delete(DatabaseSchema.PEOPLE_TABLE_NAME, DatabaseSchema.puuid+" =?",whereArgs);
        return count;
>>>>>>> tyler
    }

    public ArrayList<Note> getNotes()
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.nuuid, DatabaseSchema.puuid, DatabaseSchema.note,
                DatabaseSchema.created_at, DatabaseSchema.modified_at};
        Cursor cursor = db.query(DatabaseSchema.NOTES_TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Note> notes = new ArrayList<>();
        while(cursor.moveToNext())
        {
            Note note = new Note();
            note.nuuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(DatabaseSchema.nuuid)));
            note.puuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(DatabaseSchema.puuid)));
            note.note = cursor.getString(cursor.getColumnIndex(DatabaseSchema.note));
            note.created_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.created_at));
            note.modified_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.modified_at));
            notes.add(note);
        }
        return notes;
    }

    public Note getNote(UUID nuuid)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.nuuid, DatabaseSchema.puuid, DatabaseSchema.note,
                DatabaseSchema.created_at, DatabaseSchema.modified_at};
        String[] whereArgs = {String.valueOf(nuuid)};
        Cursor cursor = db.query(DatabaseSchema.NOTES_TABLE_NAME, columns, DatabaseSchema.nuuid + " =?", whereArgs, null, null, null);
        Note note = new Note();
        while (cursor.moveToNext()) {
            note.nuuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(DatabaseSchema.nuuid)));
            note.puuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(DatabaseSchema.puuid)));
            note.note = cursor.getString(cursor.getColumnIndex(DatabaseSchema.note));
            note.created_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.created_at));
            note.modified_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.modified_at));
        }
        return note;
    }

    public ArrayList<Person> getPeople()
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.puuid, DatabaseSchema.name,
                DatabaseSchema.created_at, DatabaseSchema.modified_at};
        Cursor cursor = db.query(DatabaseSchema.PEOPLE_TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Person> people = new ArrayList<>();
        while(cursor.moveToNext())
        {
            Person person = new Person();
            person.puuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(DatabaseSchema.puuid)));
            person.name = cursor.getString(cursor.getColumnIndex(DatabaseSchema.name));
            person.created_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.created_at));
            person.modified_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.modified_at));
            people.add(person);
        }
        return people;
    }

    public String getPersonData(UUID puuid)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.puuid, DatabaseSchema.name, DatabaseSchema.created_at,
                DatabaseSchema.modified_at};
        String[] whereArgs = {String.valueOf(puuid)};
        Cursor cursor = db.query(DatabaseSchema.PEOPLE_TABLE_NAME, columns, DatabaseSchema.puuid+" =?", whereArgs, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext())
        {
            String p_uuid = cursor.getString(cursor.getColumnIndex(DatabaseSchema.puuid));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseSchema.name));
            String create_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.created_at));
            String modified_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.modified_at));
            buffer.append(p_uuid + "\n" +
                    name + "\n" +
                    create_at + "\n" +
                    modified_at);
        }
        return buffer.toString();
    }

    public int updateNoteText(UUID nuuid, String new_note)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.note, new_note);
        String[] whereArgs = {String.valueOf(nuuid)};
        int count = db.update(DatabaseSchema.NOTES_TABLE_NAME, contentValues, DatabaseSchema.nuuid+" =?", whereArgs);
        return count;
    }

    public int updateNoteModifiedAt(UUID nuuid, String modified_at)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.modified_at, modified_at);
        String[] whereArgs = {String.valueOf(nuuid)};
        int count = db.update(DatabaseSchema.NOTES_TABLE_NAME, contentValues, DatabaseSchema.nuuid+" =?", whereArgs);
        return count;
    }

    public int updatePersonName(UUID puuid, String new_name)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.name, new_name);
        String[] whereArgs = {String.valueOf(puuid)};
        int count = db.update(DatabaseSchema.PEOPLE_TABLE_NAME, contentValues, DatabaseSchema.nuuid+" =?", whereArgs);
        return count;
    }

    public int updatePersonModifiedAt(UUID puuid, String modified_at)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.modified_at, modified_at);
        String[] whereArgs = {String.valueOf(puuid)};
        int count = db.update(DatabaseSchema.PEOPLE_TABLE_NAME, contentValues, DatabaseSchema.nuuid+" =?", whereArgs);
        return count;
    }


    static class DatabaseSchema extends SQLiteOpenHelper {
        // Database name, table name, and database version
        private static final String DATABASE_NAME = "stalkerNotesDatabase.db";
        private static final String NOTES_TABLE_NAME = "notes";
        private static final String PEOPLE_TABLE_NAME = "people";
        private static final int DATABASE_VERSION = 1;
        // Column names
        private static final String id = "_id";
        private static final String nuuid = "nuuid";
        private static final String puuid = "puuid";
        private static final String note = "note";
        private static final String name = "name";
        private static final String created_at = "created_at";
        private static final String modified_at = "modified_at";
        // sql query to create a new table with the correct schema
        private static final String CREATE_NOTES_TABLE = "CREATE TABLE "+
                NOTES_TABLE_NAME+" ("+
                id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                nuuid+" VARCHAR(255), "+
                puuid+" VARCHAR(255), "+
                note+" VARCHAR(255), "+
                created_at+" VARCHAR(255), "+
                modified_at+" VARCHAR(255));";
        private static final String CREATE_PEOPLE_TABLE = "CREATE TABLE "+
                PEOPLE_TABLE_NAME+" ("+
                id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                puuid+" VARCHAR(255), "+
                name+" VARCHAR(255), "+
                created_at+" VARCHAR(255), "+
                modified_at+" VARCHAR(255));";
        // sql query to remove the table if it exists
        private static final String DROP_NOTES_TABLE = "DROP TABLE IF EXISTS "+NOTES_TABLE_NAME;
        private static final String DROP_PEOPLE_TABLE = "DROP TABLE IF EXISTS "+PEOPLE_TABLE_NAME;
        private Context context;

        public DatabaseSchema(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Log.d("CONRAD", "Database Schema is created");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_NOTES_TABLE);
            db.execSQL(CREATE_PEOPLE_TABLE);
            Log.d("CONRAD", "onCreate is called");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_NOTES_TABLE);
            db.execSQL(DROP_PEOPLE_TABLE);
            Log.d("CONRAD", "onUpdate is called");
            onCreate(db);
        }
    }
}

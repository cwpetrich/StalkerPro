package com.example.cs3010.stalkerpro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Conrad on 4/22/2015.
 */
public class NoteDatabaseAdapter {
    DatabaseSchema dbSchema;
    Context context;
    private static UUID UnknownUUID;
    private static boolean NoteDatabaseLoadedFlag = false;
    private static String unknownName = "UnKnown";

    public NoteDatabaseAdapter(Context context)
    {
        this.context = context;
        dbSchema = new DatabaseSchema(context);

        //////////////////////////////////////////////////////
        //Getting writable DB to initialize creation
        //in order to properly test if a DB has been made yet.
        dbSchema.getWritableDatabase();
        //////////////////////////////////////////////////////

        //Create Unknown person if db hasn't been made yet.
        if(dbSchema.databaseJustMade()){
            Person unknown = new Person();
            unknown.name = unknownName;
            UnknownUUID = unknown.puuid;
            insertPerson(unknown);
        }else{
            UnknownUUID = getPeople(unknownName).get(0).puuid;
        }
        NoteDatabaseLoadedFlag = true;
    }

    public UUID getUnknownUUID(){
        return UnknownUUID;
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
        if(!NoteDatabaseLoadedFlag || person.name != unknownName) {
            SQLiteDatabase db = dbSchema.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseSchema.puuid, String.valueOf(person.puuid));
            contentValues.put(DatabaseSchema.name, person.name);
            contentValues.put(DatabaseSchema.created_at, person.created_at);
            contentValues.put(DatabaseSchema.modified_at, person.modified_at);
            long id = db.insert(DatabaseSchema.PEOPLE_TABLE_NAME, null, contentValues);
            return id;
        }
        return -1;
    }

    public long insertImage(ImageClass image)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.image_name, image.image_name);
        contentValues.put(DatabaseSchema.puuid, String.valueOf(image.puuid));
        contentValues.put(DatabaseSchema.image_caption, image.image_caption);
        contentValues.put(DatabaseSchema.created_at, image.created_at);
        contentValues.put(DatabaseSchema.modified_at, image.modified_at);
        long id = db.insert(DatabaseSchema.IMAGE_TABLE_NAME, null, contentValues);
        return id;
    }

    public long insertVideo(VideoClass video)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.video_name, video.video_name);
        contentValues.put(DatabaseSchema.puuid, String.valueOf(video.puuid));
        contentValues.put(DatabaseSchema.video_caption, video.video_caption);
        contentValues.put(DatabaseSchema.created_at, video.created_at);
        contentValues.put(DatabaseSchema.modified_at, video.modified_at);
        long id = db.insert(DatabaseSchema.VIDEO_TABLE_NAME, null, contentValues);
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
        int pcount = db.delete(DatabaseSchema.PEOPLE_TABLE_NAME, DatabaseSchema.puuid + " =?", whereArgs);
        int ncount = db.delete(DatabaseSchema.NOTES_TABLE_NAME, DatabaseSchema.puuid + " =?",whereArgs);
        int icount = db.delete(DatabaseSchema.IMAGE_TABLE_NAME, DatabaseSchema.puuid + " =?", whereArgs);
        return pcount+ncount+icount;
    }

    public int deleteImageRow(String image_name)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] whereArgs = {image_name};
        int count = db.delete(DatabaseSchema.IMAGE_TABLE_NAME, DatabaseSchema.image_name + " =?", whereArgs);
        return count;
    }

    public int deleteVideoRow(String video_name)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] whereArgs = {video_name};
        int count = db.delete(DatabaseSchema.VIDEO_TABLE_NAME, DatabaseSchema.video_name + " =?", whereArgs);
        return count;
    }

    public ArrayList<Note> getNotes()
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.nuuid, DatabaseSchema.puuid, DatabaseSchema.note,
                DatabaseSchema.created_at, DatabaseSchema.modified_at};
        Cursor cursor = db.query(DatabaseSchema.NOTES_TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Note> notes = new ArrayList<Note>();
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

    public ArrayList<Note> getNotesFor(UUID puuid)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.nuuid, DatabaseSchema.puuid, DatabaseSchema.note,
                DatabaseSchema.created_at, DatabaseSchema.modified_at};
        String[] whereArgs = {String.valueOf(puuid)};
        Cursor cursor = db.query(DatabaseSchema.NOTES_TABLE_NAME, columns, DatabaseSchema.puuid + " =?", whereArgs, null, null, null);
        ArrayList<Note> notes = new ArrayList<Note>();
        while (cursor.moveToNext()) {
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

    public ArrayList<VideoClass> getVideosFor(UUID puuid)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.video_name, DatabaseSchema.puuid,
                DatabaseSchema.video_caption, DatabaseSchema.created_at, DatabaseSchema.modified_at};
        String[] whereArgs = {String.valueOf(puuid)};
        Cursor cursor = db.query(DatabaseSchema.VIDEO_TABLE_NAME, columns, DatabaseSchema.puuid + " =?", whereArgs, null, null, null);
        ArrayList<VideoClass> videos = new ArrayList<>();
        while (cursor.moveToNext()) {
            VideoClass video = new VideoClass();
            video.video_name = cursor.getString(cursor.getColumnIndex(DatabaseSchema.video_name));
            video.puuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(DatabaseSchema.puuid)));
            video.video_caption = cursor.getString(cursor.getColumnIndex(DatabaseSchema.video_caption));
            video.created_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.created_at));
            video.modified_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.modified_at));
            videos.add(video);
        }

        return videos;
    }

    public ArrayList<ImageClass> getImagesFor(UUID puuid)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.image_name, DatabaseSchema.puuid,
                DatabaseSchema.image_caption, DatabaseSchema.created_at, DatabaseSchema.modified_at};
        String[] whereArgs = {String.valueOf(puuid)};
        Cursor cursor = db.query(DatabaseSchema.IMAGE_TABLE_NAME, columns, DatabaseSchema.puuid + " =?", whereArgs, null, null, null);
        ArrayList<ImageClass> images = new ArrayList<>();
        while (cursor.moveToNext()) {
            ImageClass image = new ImageClass();
            image.image_name = cursor.getString(cursor.getColumnIndex(DatabaseSchema.image_name));
            image.puuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(DatabaseSchema.puuid)));
            image.image_caption = cursor.getString(cursor.getColumnIndex(DatabaseSchema.image_caption));
            image.created_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.created_at));
            image.modified_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.modified_at));
            images.add(image);
        }

        return images;
    }

    public ArrayList<Person> getPeople(String name)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.puuid, DatabaseSchema.name,
                DatabaseSchema.created_at, DatabaseSchema.modified_at};
        String[] whereArgs = {name+"%"};
        Cursor cursor = db.query(DatabaseSchema.PEOPLE_TABLE_NAME, columns, DatabaseSchema.name + " LIKE ?", whereArgs, null, null, null);
        ArrayList<Person> people = new ArrayList<Person>();
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

    public ArrayList<Person> getPeople()
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.puuid, DatabaseSchema.name,
                DatabaseSchema.created_at, DatabaseSchema.modified_at};
        Cursor cursor = db.query(DatabaseSchema.PEOPLE_TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Person> people = new ArrayList<Person>();
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

    public String getPersonName(UUID puuid)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.puuid, DatabaseSchema.name, DatabaseSchema.created_at,
                DatabaseSchema.modified_at};
        String[] whereArgs = {String.valueOf(puuid)};
        Cursor cursor = db.query(DatabaseSchema.PEOPLE_TABLE_NAME, columns, DatabaseSchema.puuid + " =?", whereArgs, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(cursor.getColumnIndex(DatabaseSchema.name));
        }
        return "";
    }

    public Person getPerson(UUID puuid)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        String[] columns = {DatabaseSchema.id, DatabaseSchema.puuid, DatabaseSchema.name,
                DatabaseSchema.created_at, DatabaseSchema.modified_at};
        String[] whereArgs = {String.valueOf(puuid)};
        Cursor cursor = db.query(DatabaseSchema.PEOPLE_TABLE_NAME, columns, DatabaseSchema.puuid + " =?", whereArgs, null, null, null);
        Person person = new Person();
        while (cursor.moveToNext()) {
            person.puuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(DatabaseSchema.puuid)));
            person.name = cursor.getString(cursor.getColumnIndex(DatabaseSchema.note));
            person.created_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.created_at));
            person.modified_at = cursor.getString(cursor.getColumnIndex(DatabaseSchema.modified_at));
        }
        return person;

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

    public int updateNotePuuid(UUID nuuid, UUID puuid)
    {
        SQLiteDatabase db = dbSchema.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseSchema.puuid, puuid.toString());
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
        private static boolean newDatabase = false;
        private static final String DATABASE_NAME = "stalkerNotesDatabase.db";
        private static final String NOTES_TABLE_NAME = "notes";
        private static final String PEOPLE_TABLE_NAME = "people";
        private static final String IMAGE_TABLE_NAME = "images";
        private static final String VIDEO_TABLE_NAME = "videos";
        private static final int DATABASE_VERSION = 1;
        // Column names
        private static final String id = "_id";
        private static final String nuuid = "nuuid";
        private static final String puuid = "puuid";
        private static final String note = "note";
        private static final String name = "name";
        private static final String image_name = "image_name";
        private static final String image_caption = "image_caption";
        private static final String video_name = "video_name";
        private static final String video_caption = "video_caption";
        private static final String created_at = "created_at";
        private static final String modified_at = "modified_at";

        private static final String CREATE_VIDEO_TABLE = "CREATE TABLE "+
                VIDEO_TABLE_NAME+" ("+
                id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                video_name+" VARCHAR(255), "+
                puuid+" VARCHAR(255), "+
                video_caption+" VARCHAR(255), "+
                created_at+" VARCHAR(255), "+
                modified_at+" VARCHAR(255));";

        private static final String CREATE_IMAGE_TABLE = "CREATE TABLE "+
                IMAGE_TABLE_NAME+" ("+
                id+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                image_name+" VARCHAR(255), "+
                puuid+" VARCHAR(255), "+
                image_caption+" VARCHAR(255), "+
                created_at+" VARCHAR(255), "+
                modified_at+" VARCHAR(255));";
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
        private static final String DROP_IMAGE_TABLE = "DROP TABLE IF EXISTS "+IMAGE_TABLE_NAME;
        private static final String DROP_VIDEO_TABLE = "DROP TABLE IF EXISTS "+VIDEO_TABLE_NAME;
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
            db.execSQL(CREATE_IMAGE_TABLE);
            db.execSQL(CREATE_VIDEO_TABLE);
            Log.d("CONRAD", "onCreate is called");
            newDatabase = true;
        }
        public boolean databaseJustMade(){
            if(newDatabase){
                newDatabase = false;
                return true;
            }
            return false;
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_NOTES_TABLE);
            db.execSQL(DROP_PEOPLE_TABLE);
            db.execSQL(DROP_IMAGE_TABLE);
            db.execSQL(DROP_VIDEO_TABLE);
            Log.d("CONRAD", "onUpdate is called");
            onCreate(db);
        }
    }
}

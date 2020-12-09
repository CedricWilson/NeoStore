package com.example.neostore2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase {

    private static NoteRoomDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteRoomDatabase.class, "set").
                    fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsync(instance).execute();
        }
    };

    private static class PopulateAsync extends AsyncTask<Void, Void, Void>{
        private  NoteDao noteDao;

        private PopulateAsync(NoteRoomDatabase db){
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("kid@g.com", "Cedric", "A - 501"));
            return null;
        }
    }


}
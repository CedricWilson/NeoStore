package com.example.neostore2;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepo {
    private API api;
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepo(Application application){
        NoteRoomDatabase database = NoteRoomDatabase.getInstance(application);
        String mail="0";
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes(mail);
    }

    public void insert(Note note){
        new InsertAsync(noteDao).execute(note);

    }

    public void delete(Note note){
        new DeleteAsync(noteDao).execute(note);
    }

    public void deleteAll(){
        new DeleteAllAsync(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes(String mail){
        return noteDao.getAllNotes(mail);
    }



    private static class InsertAsync extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        private InsertAsync(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteAsync extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        private DeleteAsync(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllAsync extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;

        private DeleteAllAsync(NoteDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }






}

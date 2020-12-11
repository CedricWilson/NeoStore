package com.example.neostore2.Helpers;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.neostore2.Note;
import com.example.neostore2.NoteDao;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepo repository;
    NoteDao nd;
    private LiveData<List<Note>> allNotes;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepo(application);
        String mail="0";
        allNotes  = repository.getAllNotes(mail);
        NoteRoomDatabase database = NoteRoomDatabase.getInstance(application);
        nd = database.noteDao();
    }

    public void insert(Note note){
        repository.insert(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public void deleteAll(Note note){
        repository.deleteAll();
    }

    public LiveData<List<Note>> getAllNotes(String mail){
        return repository.getAllNotes(mail);
    }

}

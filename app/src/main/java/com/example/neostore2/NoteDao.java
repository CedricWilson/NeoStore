package com.example.neostore2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.neostore2.Note;

import java.util.List;


@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note WHERE email LIKE :mail")
    LiveData<List<Note>> getAllNotes(String mail);

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM Note")
    void deleteAll();
}

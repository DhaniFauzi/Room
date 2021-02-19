package com.example.room.room
import android.provider.ContactsContract
import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note:note)

    @Update
    suspend fun updateNote(note:note)

    @Delete
    suspend fun deleteNote(note:note)

    @Query("SELECT * FROM note")
    suspend fun getNotes():List<note>

    @Query("SELECT * FROM note WHERE id=:note_id")
    suspend fun getNote(note_id: Int):List<note>
}

package nl.hva.task01.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import nl.hva.task01.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes LIMIT 1")
    fun getNote(): LiveData<Note>

    @Insert
    fun addNote(note: Note)

    @Update
    fun updateNote(note: Note)
}
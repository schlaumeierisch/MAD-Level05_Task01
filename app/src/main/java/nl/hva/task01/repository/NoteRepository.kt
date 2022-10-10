package nl.hva.task01.repository

import android.content.Context
import androidx.lifecycle.LiveData
import nl.hva.task01.dao.NoteDao
import nl.hva.task01.database.NotepadRoomDatabase
import nl.hva.task01.model.Note

class NoteRepository(context: Context) {
    private var noteDao: NoteDao

    init {
        val notepadRoomDatabase = NotepadRoomDatabase.getDatabase(context)
        noteDao = notepadRoomDatabase!!.noteDao()
    }

    fun getNote(): LiveData<Note> = noteDao.getNote()

    suspend fun addNote(note: Note) = noteDao.addNote(note)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)
}
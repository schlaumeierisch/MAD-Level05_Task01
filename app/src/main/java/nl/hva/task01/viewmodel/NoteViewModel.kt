package nl.hva.task01.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.hva.task01.model.Note
import nl.hva.task01.repository.NoteRepository
import java.util.*

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val noteRepository = NoteRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val note = noteRepository.getNote()
    val error = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    fun updateNote(title: String, text: String) {
        val newNote = Note(
            id = note.value?.id,
            title = title,
            date = Date(),
            text = text
        )

        if (isNoteValid(newNote)) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    noteRepository.updateNote(newNote)
                }
                success.value = true
            }
        }
    }

    private fun isNoteValid(note: Note): Boolean {
        return when {
            note.title.isBlank() -> {
                error.value = "Title must not be empty"
                false
            }
            else -> true
        }
    }
}
package nl.hva.task01.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.hva.task01.dao.NoteDao
import nl.hva.task01.model.Note
import nl.hva.task01.util.Converters
import java.util.*

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NotepadRoomDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DATABASE_NAME = "NOTE_DATABASE"

        @Volatile
        private var notepadRoomDatabaseInstance: NotepadRoomDatabase? = null

        fun getDatabase(context: Context): NotepadRoomDatabase? {
            if (notepadRoomDatabaseInstance == null) {
                synchronized(NotepadRoomDatabase::class.java) {
                    if (notepadRoomDatabaseInstance == null) {
                        notepadRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            NotepadRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    notepadRoomDatabaseInstance?.let { database ->
                                        CoroutineScope(Dispatchers.IO).launch {
                                            database.noteDao().addNote(Note("Title", Date(), "", 1))
                                        }
                                    }
                                }
                            })
                            .build()
                    }
                }
            }
            return notepadRoomDatabaseInstance
        }
    }
}
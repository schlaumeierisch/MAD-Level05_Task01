package nl.hva.task01.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "date")
    val date: Date,

    @ColumnInfo(name = "text")
    val text: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long?
)

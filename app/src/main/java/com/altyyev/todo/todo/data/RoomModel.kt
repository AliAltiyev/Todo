package com.altyyev.todo.todo.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.altyyev.todo.todo.util.Constants.Companion.TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class RoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val message: String,
    val priority: Priority
) : Parcelable

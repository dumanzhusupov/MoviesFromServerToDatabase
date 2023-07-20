package com.example.database.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.database.data.Movie

@Entity
data class MovieEntity (
    @PrimaryKey(autoGenerate = false) val id:Long,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "budget") val budget:Long,
    @ColumnInfo(name = "release_date") val releaseDate:String,
    @ColumnInfo(name = "poster") val poster:String?,
){
    @Ignore
    fun toMovie(): Movie {
        return Movie(id,title,releaseDate,budget,poster)
    }
}
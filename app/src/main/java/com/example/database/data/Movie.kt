package com.example.database.data

import com.example.database.database.MovieEntity
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id") val id:Long,
    @SerializedName("title") val title:String,
    @SerializedName("release_date") val releaseDate:String,
    @SerializedName("budget") val budget:Long,
    @SerializedName("poster_path") val poster:String?,
){
    fun toMovieEntity():MovieEntity{
        return MovieEntity(id,title,budget,releaseDate,poster)
    }
}
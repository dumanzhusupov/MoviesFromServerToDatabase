package com.example.database.data

import com.google.gson.annotations.SerializedName

class MoviePosters(
    @SerializedName("posters") val posters:List<PosterFilePath>,
){
    fun size():Int = posters.size
    fun get(index:Int):String = posters[index].poster.toString()
}
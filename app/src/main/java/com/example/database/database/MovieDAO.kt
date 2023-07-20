package com.example.database.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDAO {
    @Insert
    fun insert(movieEntity:MovieEntity)

    @Query("SELECT * FROM movieentity WHERE id = :movieID LIMIT 1")
    fun get(movieID:Long):MovieEntity?

    @Query("SELECT DISTINCT * FROM movieentity")
    fun getAllMovies():List<MovieEntity?>

    @Query("SELECT COUNT(DISTINCT id) FROM movieentity")
    fun getCountMovies():Int

}
package com.example.database.repository

import com.example.database.data.Movie
import com.example.database.data.MoviePosters

interface MovieRepository {
    suspend fun getMovieDetails(movieId:Long): Movie?
    suspend fun getMoviePosters(movieId:Long):MoviePosters?
    suspend fun getAllMoviesFromDatabase():List<Movie?>
}
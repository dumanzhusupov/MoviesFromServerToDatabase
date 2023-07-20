package com.example.database.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.database.api.MovieAPI
import com.example.database.data.Movie
import com.example.database.data.MoviePosters
import com.example.database.database.MovieDatabase

class MovieRepositoryImpl(
    private val context:Context
):MovieRepository{
    private val database = Room
        .databaseBuilder(context,MovieDatabase::class.java,"movie_database")
        .build()

    override suspend fun getMovieDetails(movieId: Long): Movie? {
        val savedMovieEntity = database.movieDAO().get(movieId)
        if(savedMovieEntity!=null){
            Log.i("DATABASE", "getMovieDetails -> get: ${savedMovieEntity.toMovie()}")
            return savedMovieEntity.toMovie()
        }
        else{
            val response = MovieAPI.INSTANCE.getMovieDetails(movieId, API_KEY).execute()
            Log.i("API", "MovieRepositoryImpl -> getMovieDetails -> ${response.isSuccessful}")
            return if (response.isSuccessful) {
                val movie = response.body()
                if (movie != null) {
                    database.movieDAO().insert(movie.toMovieEntity())
                    Log.i("DATABASE", "getMovieDetails -> inserted: $movie")
                }
                return movie
            } else null
        }
    }

    override suspend fun getMoviePosters(movieId: Long): MoviePosters? {
        val response = MovieAPI.INSTANCE.getMoviePosters(movieId, API_KEY).execute()
        Log.i("API","MovieRepositoryImpl -> getMoviePosters -> ${response.isSuccessful}")

        return if(response.isSuccessful){
            //response.body()?.posters?.forEach { it.poster = it.poster?.substring(8) }
            response.body()
        } else null
    }

    override suspend fun getAllMoviesFromDatabase(): List<Movie?> {
        val movieEntities = database.movieDAO().getAllMovies()
        if(movieEntities!=null){
            val movies:MutableList<Movie> = mutableListOf()
            movieEntities.forEach {
                if (it != null) {
                    movies.add(it.toMovie())
                }
            }
            Log.i("DATABASE_UK","in Database: ${movies.forEach{Log.i("DATABASE_UK","${it.title}")}}")
            return movies
        }
        return emptyList()
    }

    companion object{
        val API_KEY = "f0bf4cbd3cd1fddc24f38560e9ef83a5"
    }
}
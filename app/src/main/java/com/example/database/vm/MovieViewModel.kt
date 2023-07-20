package com.example.database.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.database.data.Movie
import com.example.database.data.MoviePosters
import com.example.database.repository.MovieRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(application:Application):AndroidViewModel(application) {
    private val repo = MovieRepositoryImpl(application)
    private  var _currentMovieAndPosters = MutableStateFlow<Pair<Movie?,MoviePosters?>>(Pair(null,null))
    private var _currentAllMovies = MutableStateFlow<List<Movie?>>(listOf())

    val currentMovieAndPosters: StateFlow<Pair<Movie?,MoviePosters?>> = _currentMovieAndPosters
    val currentAllMovies:StateFlow<List<Movie?>> = _currentAllMovies

    fun loadMovie(movieId:Long){
        viewModelScope.launch(Dispatchers.IO) {
            val movie = repo.getMovieDetails(movieId)
            val posters = repo.getMoviePosters(movieId)
            Log.i("API","loadMovie -> $posters")
            _currentMovieAndPosters.value = Pair(movie,posters)
        }
    }
    fun getAllMoviesFromDatabase(){
        Log.i("DATABASE_UK","getAllMoviesFromDatabase()")
        viewModelScope.launch (Dispatchers.IO) {
             _currentAllMovies.value = repo.getAllMoviesFromDatabase()
        }
    }

}
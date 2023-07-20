package com.example.database

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.database.data.Movie
import com.example.database.repository.MovieAdapter
import com.example.database.vm.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesActivity:AppCompatActivity() {
    private val backButton: Button by lazy{findViewById(R.id.back_button)}
    private val recyclerView:RecyclerView by lazy{findViewById(R.id.recyclerView)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val viewModel: MovieViewModel by viewModels()


        viewModel.getAllMoviesFromDatabase()

        var dataMovies:List<Movie?> = emptyList()
        val adapter = dataMovies?.let { MovieAdapter(it) }
        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        recyclerView.adapter = adapter

        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.currentAllMovies.collect{movies ->
                    dataMovies = movies
                    Log.i("DATABASE_UK","lifecycle-launch -> ${movies}")
                    withContext(Dispatchers.Main) {
                        recyclerView.adapter = MovieAdapter(dataMovies)
                    }
                }
            }
        }

        backButton.setOnClickListener {
            val intent = Intent(this@MoviesActivity,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
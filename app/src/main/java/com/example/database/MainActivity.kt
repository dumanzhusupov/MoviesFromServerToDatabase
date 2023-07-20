package com.example.database

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.bumptech.glide.Glide
import com.example.database.data.Movie
import com.example.database.data.MoviePosters
import com.example.database.data.PosterFilePath
import com.example.database.databinding.ActivityMainBinding
import com.example.database.repository.MovieAdapter
import com.example.database.vm.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val loadButton: Button by lazy { findViewById(R.id.load_button) }
    private val inputId: EditText by lazy { findViewById(R.id.enterId) }
    private val title: TextView by lazy { findViewById(R.id.title_val) }
    private val budget: TextView by lazy { findViewById(R.id.budget_val) }
    private val releaseDate: TextView by lazy { findViewById(R.id.release_date_val) }
    private val listButton:Button by lazy{findViewById(R.id.go_movies_activity_button)}
    private val poster:ImageView by lazy{findViewById(R.id.poster)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val viewModel: MovieViewModel by viewModels()

        var dataMovies:List<Movie?> = emptyList()

        lifecycleScope.launch(Dispatchers.Default) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentMovieAndPosters.collect { pair ->
                    Log.i("API", "lifecycleScope.launch -> ${pair.second}")
                    withContext(Dispatchers.Main) {
                        setMovieDetails(pair.first)
                    }
                }
            }
        }

        loadButton.setOnClickListener {
            val movieId: Long? = inputId.text.toString().toLongOrNull()
            Toast.makeText(this, "BUTTON PRESSED", Toast.LENGTH_SHORT).show()
            Log.i("API", "loadButtonClickListener -> ${movieId}")
            if (movieId != null) {
                viewModel.loadMovie(movieId)
            }
        }
        listButton.setOnClickListener {
            val intent = Intent(this@MainActivity,MoviesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setMovieDetails(movie: Movie?) {
        if (movie != null) {
            title.text = movie.title
            budget.text = movie.budget.toString()
            releaseDate.text = movie.releaseDate
            Glide.with(this)
                .load(BASE_URL+movie.poster)
                .fitCenter()
                .into(poster)
        }
    }
    private fun setMoviePosters(moviePosters:MoviePosters?){
        if(moviePosters!=null){
            moviePosters.posters.forEach { Log.i("API","setMoviePosters -> $it") }
        }else{
            Log.i("API","setMoviePosters -> moviePosters = ${moviePosters}")
        }
    }
    companion object{
        val BASE_URL = "https://image.tmdb.org/t/p/original/"
    }
}
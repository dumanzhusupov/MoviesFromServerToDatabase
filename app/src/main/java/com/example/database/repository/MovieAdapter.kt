package com.example.database.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.database.R
import com.example.database.data.Movie
import com.example.database.data.MoviePosters
import com.example.database.data.PosterFilePath
import com.example.database.vm.MovieViewModel

class MovieAdapter(
    private val dataset: List<Movie?>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView
        val title:TextView
        val release_date:TextView
        init {
            poster = view.findViewById(R.id.poster)
            title = view.findViewById(R.id.title_val)
            release_date = view.findViewById(R.id.release_date_val)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = dataset[position]
        if(movie!=null) {
            holder.title.text = movie.title
            holder.release_date.text = movie.releaseDate
            Glide.with(holder.itemView.context)
                .load(BASE_URL + movie.poster)
                .fitCenter()
                .into(holder.poster)
        }
    }
    companion object{
        val BASE_URL = "https://image.tmdb.org/t/p/original/"
    }
}
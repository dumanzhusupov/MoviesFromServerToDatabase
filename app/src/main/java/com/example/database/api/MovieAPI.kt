package com.example.database.api

import com.example.database.data.Movie
import com.example.database.data.MoviePosters
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie/{movieId}")
    fun getMovieDetails(@Path("movieId")movieId:Long, @Query("api_key") api_key:String): Call<Movie>

    @GET("movie/{movie_id}/images")
    fun getMoviePosters(@Path("movie_id")movie_id:Long, @Query("api_key") api_key: String):Call<MoviePosters>


    companion object{
        val INSTANCE: MovieAPI = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.themoviedb.org/3/")
            .build()
            .create(MovieAPI::class.java)
    }
}
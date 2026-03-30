package com.example.sem8snsor

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.adapter.MovieAdapter
import com.example.movielist.model.MovieResponse
import com.example.movielist.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDataFetch : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnLoad: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_data_fetch)

        recyclerView = findViewById(R.id.recyclerView)
        btnLoad = findViewById(R.id.btnLoad)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // ❌ REMOVE this line
        // fetchMovies()

        // ✅ Button click पर API call
        btnLoad.setOnClickListener {
            fetchMovies()
        }
    }

    private fun fetchMovies() {

        val apiKey = "0ab8e9ef4f94beceb88ccd8f2498b6b0"

        RetrofitClient.api.getPopularMovies(apiKey)
            .enqueue(object : Callback<MovieResponse> {

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {

                        val movies = response.body()?.results ?: emptyList()

                        if (movies.isNotEmpty()) {
                            recyclerView.adapter = MovieAdapter(movies)
                        } else {
                            Toast.makeText(this@MovieDataFetch, "No Movies Found", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this@MovieDataFetch, "API Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Toast.makeText(this@MovieDataFetch, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
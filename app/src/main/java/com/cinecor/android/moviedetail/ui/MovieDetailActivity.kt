package com.cinecor.android.moviedetail.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.cinecor.android.R
import com.cinecor.android.common.model.Movie
import com.cinecor.android.common.ui.BaseActivity
import com.cinecor.android.common.viewmodel.CinemaViewModel
import com.cinecor.android.common.viewmodel.CinemaViewModelFactory
import kotlinx.android.synthetic.main.activity_movie_detail.*
import javax.inject.Inject

class MovieDetailActivity : BaseActivity(), Observer<Movie> {
    companion object {
        const private val ARG_CINEMA_ID = "ARG_CINEMA_ID"
        const private val ARG_MOVIE_ID = "ARG_MOVIE_ID"
        fun getInstance(context: Context, cinemaId: Int?, movieId: Int?): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(ARG_CINEMA_ID, cinemaId)
            intent.putExtra(ARG_MOVIE_ID, movieId)
            return intent
        }

    }

    @Inject lateinit var factory: CinemaViewModelFactory

    private lateinit var viewModel: CinemaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setupView()
        getMovie()
    }

    private fun getMovie() {
        val cinemaId = intent.extras.getInt(ARG_CINEMA_ID, -1)
        val movieId = intent.extras.getInt(ARG_MOVIE_ID, -1)
        viewModel = ViewModelProviders.of(this, factory).get(CinemaViewModel::class.java)
        viewModel.getMovieFromCinema(movieId, cinemaId).observe(this, this)
    }

    private fun setupView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun showMovie(movie: Movie) {
        val mainColor = Color.parseColor(movie.colors[Movie.Colors.MAIN.toString()])
        val textColor = Color.parseColor(movie.colors[Movie.Colors.TITLE.toString()])
        name.text = movie.title
        genres.text = movie.genres.joinToString(", ")
        overview.text = movie.overview
        poster.setImageURI(movie.images[Movie.Images.POSTER.toString()])
        backdrop.setImageURI(movie.getBackdropImages()?.first)
        banner.setBackgroundColor(mainColor)
        genres.setTextColor(textColor)
        name.setTextColor(textColor)
    }

    override fun onChanged(movie: Movie?) {
        movie?.let { showMovie(movie) }
    }
}

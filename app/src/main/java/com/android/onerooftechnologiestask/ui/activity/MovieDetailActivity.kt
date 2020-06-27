package com.android.onerooftechnologiestask.ui.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.onerooftechnologiestask.R
import com.android.onerooftechnologiestask.model.Result
import com.android.onerooftechnologiestask.ui.activity.MainActivity.Companion.movieImagePathBuilder
import com.squareup.picasso.Picasso





class MovieDetailActivity : AppCompatActivity() {

    var mMovieTitle: TextView? = null

    var mMoviePoster: ImageView? = null

    var mMovieRating: TextView? = null

    var mMovieSynopsis: TextView? = null

    var mMovieReleaseDate: TextView? = null

    private var mMovie: Result? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(findViewById(R.id.toolbar))
        mMoviePoster=findViewById(R.id.movie_activity_poster)
        mMovieTitle=findViewById(R.id.movie_activity_title)
        mMovieRating=findViewById(R.id.movie_activity_rating)
        mMovieReleaseDate=findViewById(R.id.movie_activity_release_date)
        mMovieSynopsis=findViewById(R.id.movie_activity_synopsis)
        val intent = intent
        val bundle = intent.extras
        mMovie = bundle!!.getParcelable<Result>("movie")
        Picasso.with(this).load(movieImagePathBuilder(mMovie!!.poster_path)).into(mMoviePoster)
        mMovieTitle!!.text = mMovie!!.title
        title=mMovie!!.title
        mMovieSynopsis!!.setText(mMovie!!.overview)
        mMovieReleaseDate!!.setText(mMovie!!.release_date)
        val userRatingText: String =
            java.lang.String.valueOf(mMovie!!.vote_average) + "/10"
        mMovieRating!!.text = userRatingText

    }


}
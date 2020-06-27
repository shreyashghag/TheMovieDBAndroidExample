package com.android.onerooftechnologiestask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.android.onerooftechnologiestask.R
import com.android.onerooftechnologiestask.model.Result
import com.android.onerooftechnologiestask.ui.activity.MainActivity.Companion.getMeasuredPosterHeight
import com.android.onerooftechnologiestask.ui.activity.MainActivity.Companion.getScreenWidth
import com.android.onerooftechnologiestask.ui.activity.MainActivity.Companion.movieImagePathBuilder
import com.squareup.picasso.Picasso


class MovieAdapter(
    movieList: List<Result>,
    movieClickListener: MovieClickListener
) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val movieClickListener: MovieClickListener = movieClickListener
    private val movieList: List<Result> = movieList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_card_view, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Result = movieList[position]
        holder.bind(movie, movieClickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onViewRecycled(holder: MovieViewHolder) {
        super.onViewRecycled(holder)
    }

    class MovieViewHolder(itemView: View?) : ViewHolder(itemView!!) {

        var mMovieTitle: TextView? = null
        var mMoviePoster: ImageView? = null
        var mMovieCard: CardView? = null

        init {
            mMoviePoster=itemView!!.findViewById(R.id.iv_movie_poster)
            mMovieCard=itemView!!.findViewById(R.id.cv_movie_card)
            mMovieTitle=itemView!!.findViewById(R.id.movie_title)
        }
        fun bind(movie: Result, movieClickListener: MovieClickListener) {
            mMovieCard!!.setLayoutParams( ViewGroup.LayoutParams(getScreenWidth()/2, getMeasuredPosterHeight(getScreenWidth()/2)));
            mMovieTitle!!.setText(movie.original_title)
            Picasso.with(mMoviePoster!!.getContext())
                .load(movieImagePathBuilder(movie.poster_path))
                .placeholder(R.drawable.ic_launcher_background).fit().centerCrop().into(mMoviePoster)
            itemView.setOnClickListener{
                    movieClickListener.onMovieClick(movie)

            }
        }


    }

    interface MovieClickListener {
        fun onMovieClick(movie: Result?)
    }
}
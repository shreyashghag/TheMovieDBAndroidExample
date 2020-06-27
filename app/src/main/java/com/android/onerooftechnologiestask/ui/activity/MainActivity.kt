package com.android.onerooftechnologiestask.ui.activity

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.android.onerooftechnologiestask.R
import com.android.onerooftechnologiestask.model.Movie
import com.android.onerooftechnologiestask.model.Result
import com.android.onerooftechnologiestask.network.GetMovieDataService
import com.android.onerooftechnologiestask.network.RetrofitInstance
import com.android.onerooftechnologiestask.ui.adapter.MovieAdapter
import com.android.onerooftechnologiestask.ui.adapter.MovieAdapter.MovieClickListener
import com.android.onerooftechnologiestask.ui.utils.ConnectionDetector
import com.android.onerooftechnologiestask.ui.utils.EndlessRecyclerViewScrollListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {


    private val FIRST_PAGE = 1
    private var totalPages = 0
    private var call: Call<Movie>? = null
    private var movieResults: ArrayList<Result>? = null
    private var movieAdapter: MovieAdapter? = null
    private val API_KEY="0a24829613ee6dbbce1483d1a912541a"


    var recyclerView: RecyclerView? = null
    var searchView:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.rv_movies)
        searchView=findViewById(R.id.searchView)
        setupListeners()

    }

    fun setupListeners(){
        val manager = GridLayoutManager(this, 2)
        manager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 1
            }
        }
        recyclerView!!.layoutManager = manager
        val scrollListener: EndlessRecyclerViewScrollListener =
            object : EndlessRecyclerViewScrollListener(manager) {
                override fun onLoadMore(
                    page: Int,
                    totalItemsCount: Int,
                    view: RecyclerView?
                ) {
                    if (page + 1 <= totalPages ) {
                        loadPage(page + 1,1)
                    }
                }
            }

        recyclerView!!.addOnScrollListener(scrollListener)
        if(ConnectionDetector.isConnected(this)){
            loadPage(FIRST_PAGE,1)
        }else{
            Toast.makeText(this,"no Internet Connection",Toast.LENGTH_SHORT).show()
        }
    }


    fun loadPage(page:Int,cucurrentSortMode:Int) {
        val movieDataService = RetrofitInstance.getRetrofitInstance()!!.create(GetMovieDataService::class.java) as GetMovieDataService
        when (cucurrentSortMode) {
            1 -> call = movieDataService.getPopularMovies(page, API_KEY)
            2 -> call = movieDataService.getTopRatedMovies(page, API_KEY)
        }
        call!!.enqueue(object : Callback<Movie?> {
            override fun onResponse(
                call: Call<Movie?>,
                response: Response<Movie?>
            ) {
                if (page === 1) {
                    movieResults = response.body()!!.results
                    totalPages = response.body()!!.total_pages
                    Log.d("pages",response.body()!!.total_pages.toString())
                    Log.d("result",response.body()!!.results.toString())
                    movieAdapter = MovieAdapter(movieResults!!, object : MovieClickListener {

                        override fun onMovieClick(movie: Result?) {
                            val intent =
                                Intent(this@MainActivity, MovieDetailActivity::class.java)
                            val bundle = Bundle()
                            bundle.putParcelable("movie", movie)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }
                    })
                    recyclerView!!.adapter = movieAdapter
                } else {
                    assert(response.body() != null)
                    val movies: List<Result> = response.body()!!.results
                    for (movie in movies) {
                        movieResults!!.add(movie)
                        movieAdapter!!.notifyItemInserted(movieResults!!.size - 1)
                    }
                }
            }

            override fun onFailure(call: Call<Movie?>, t: Throwable) {
                Log.d("error",t.message)
               Toast.makeText(applicationContext,"some error occured",Toast.LENGTH_SHORT).show()
            }
          })

        }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //SortID 1 -> Popularity
        //SortID 2 -> Top rated
        when (item.getItemId()) {
            R.id.sort_by_popularity -> loadPage(1,1)
            R.id.sort_by_top -> loadPage(1,2)

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_movie_detail, menu)
        return true
    }

        companion object {
            fun getScreenWidth(): Int {
                return Resources.getSystem().getDisplayMetrics().widthPixels
            }

            fun getMeasuredPosterHeight(width: Int): Int {
                return (width * 1.5f).toInt()
            }
            fun movieImagePathBuilder(imagePath: String): String? {
                return "https://image.tmdb.org/t/p/" +
                        "w500" +
                        imagePath
            }
    }
}
package com.android.onerooftechnologiestask.model

data class Movie(
    val page: Int,
    val results: ArrayList<Result>,
    val total_pages: Int,
    val total_results: Int
)
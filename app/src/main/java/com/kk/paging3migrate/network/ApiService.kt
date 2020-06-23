package com.kk.paging3migrate.network

import com.kk.paging3migrate.entity.Article
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author kuky.
 * @description
 */
interface ApiService {
    @GET("/article/list/{page}/json")
    suspend fun homeArticles(@Path("page") page: Int): Article
}
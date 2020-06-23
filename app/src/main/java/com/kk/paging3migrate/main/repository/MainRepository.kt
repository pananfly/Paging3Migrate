package com.kk.paging3migrate.main.repository

import androidx.paging.PagingSource
import com.kk.paging3migrate.entity.DataX
import com.kk.paging3migrate.network.RetrofitManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author kuky.
 * @description
 */

class MainRepository {
    suspend fun loadHomeArticleByPage(page: Int): MutableList<DataX> =
        withContext(Dispatchers.IO) {
            RetrofitManager.apiService.homeArticles(page).data.datas
        }
}

class ArticlePagingSource(private val repository: MainRepository) : PagingSource<Int, DataX>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
        val position = params.key ?: 0

        return try {
            val repos = repository.loadHomeArticleByPage(position)

            LoadResult.Page(
                data = repos,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
package com.kk.paging3migrate.main.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.kk.paging3migrate.MigrateApplication
import com.kk.paging3migrate.data.MigrateDatabase
import com.kk.paging3migrate.entity.DataX
import com.kk.paging3migrate.entity.RemoteKeys
import com.kk.paging3migrate.network.RetrofitManager
import java.io.InvalidObjectException

/**
 * @author kuky.
 * @description
 */
@ExperimentalPagingApi
class ArticleRemoteMediator : RemoteMediator<Int, DataX>() {
    private val migDb by lazy {
        MigrateDatabase.getInstance(MigrateApplication.ctx)
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, DataX>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyForCurrentItem(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: throw  InvalidObjectException("RemoteKey should not be null")
                if (remoteKeys.prevKey == null) return MediatorResult.Success(true)
                remoteKeys.prevKey ?: 0
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null)
                    throw InvalidObjectException("RemoteKey should not be null for $loadType")
                remoteKeys.nextKey ?: 0
            }
        }

        try {
            val response = RetrofitManager.apiService.homeArticles(page)
            val articles = response.data.datas
            val endOfPagingReached = articles.isNullOrEmpty()

            migDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    migDb.remoteQueryDao().clearRemoteKeys()
                    migDb.remoteQueryDao().clearArticle()
                }

                val prevKey = if (page == 0) null else page - 1
                val nextKey = if (endOfPagingReached) null else page + 1
                val keys = articles.map { RemoteKeys(it.id, prevKey, nextKey) }
                migDb.remoteQueryDao().insertRemoteKeys(keys)
                migDb.remoteQueryDao().insertArticles(articles)
            }
            return MediatorResult.Success(endOfPagingReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DataX>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()?.let { article ->
                migDb.remoteQueryDao().remoteKeyById(article.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DataX>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()?.let { article ->
                migDb.remoteQueryDao().remoteKeyById(article.id)
            }
    }

    private suspend fun getRemoteKeyForCurrentItem(state: PagingState<Int, DataX>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                migDb.remoteQueryDao().remoteKeyById(it)
            }
        }
    }
}
package com.kk.paging3migrate.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kk.paging3migrate.entity.DataX
import com.kk.paging3migrate.entity.RemoteKeys

/**
 * @author kuky.
 * @description
 */
@Dao
interface QueryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: MutableList<DataX>)

    @Query("select * from article")
    fun allArticles(): PagingSource<Int, DataX>

    @Query("delete from article")
    suspend fun clearArticle()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKeys(keys: List<RemoteKeys>)

    @Query("select * from remote_keys where articleId = :id")
    suspend fun remoteKeyById(id: Int): RemoteKeys?

    @Query("delete from remote_keys")
    suspend fun clearRemoteKeys()
}
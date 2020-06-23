package com.kk.paging3migrate.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kk.paging3migrate.entity.DataX
import com.kk.paging3migrate.main.repository.ArticlePagingSource
import com.kk.paging3migrate.main.repository.MainRepository

/**
 * @author kuky.
 * @description
 */

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val articles = Pager(
        PagingConfig(pageSize = 20, prefetchDistance = 5, enablePlaceholders = true)
    ) { ArticlePagingSource(repository) }.flow
}
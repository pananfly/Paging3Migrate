@file:Suppress("UNCHECKED_CAST")

package com.kk.paging3migrate.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.kk.paging3migrate.main.repository.MainRepository

/**
 * @author kuky.
 * @description
 */

@ExperimentalPagingApi
class MainViewModelFactory(private val repository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}
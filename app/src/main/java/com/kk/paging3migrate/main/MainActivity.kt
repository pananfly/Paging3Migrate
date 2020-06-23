package com.kk.paging3migrate.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.kk.paging3migrate.R
import com.kk.paging3migrate.databinding.ActivityMainBinding
import com.kk.paging3migrate.main.adapter.ArticleAdapter
import com.kk.paging3migrate.main.adapter.PagingHeaderAdapter
import com.kk.paging3migrate.main.adapter.PagingLoadStateAdapter
import com.kk.paging3migrate.main.repository.MainRepository
import com.kk.paging3migrate.main.viewmodel.MainViewModel
import com.kk.paging3migrate.main.viewmodel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val mArticleAdapter by lazy { ArticleAdapter() }

    private val mBinding by lazy<ActivityMainBinding> {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private val mViewModel by lazy {
        ViewModelProvider(this, MainViewModelFactory(MainRepository()))
            .get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding.articleRefresh.setColorSchemeResources(
            R.color.colorPrimaryDark, R.color.colorPrimary
        )

        mBinding.articles.adapter = mArticleAdapter.withLoadStateHeaderAndFooter(
            header = PagingHeaderAdapter(),
            footer = PagingLoadStateAdapter { mArticleAdapter.retry() }
        )

        launch {
            mViewModel.articles.collect {
                mBinding.articleRefresh.isRefreshing = false
                mArticleAdapter.submitData(it)
            }
        }

        mArticleAdapter.addLoadStateListener { loadState ->
            mBinding.articles.isVisible = loadState.refresh is LoadState.NotLoading
            mBinding.loading.isVisible = loadState.refresh is LoadState.Loading
        }

        mBinding.articleRefresh.setOnRefreshListener {
            mArticleAdapter.refresh()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
        cancel()
    }
}
package com.lxy.songsearch.ui

import SongsListAdapter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.lxy.songsearch.base.ui.BaseActivity
import com.lxy.songsearch.databinding.ActivitySearchSongBinding
import com.lxy.songsearch.ext.extLaunchWhenState
import com.lxy.songsearch.ext.extLogPrint
import com.lxy.songsearch.ext.extToJsonString
import com.lxy.songsearch.ext.extToastShow
import com.lxy.songsearch.viewmodel.SearchViewModel

class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchSongBinding>() {

    private val TAG = SearchActivity::class.java.simpleName

    override val viewModel: SearchViewModel by viewModels()

    private val mAdapter = SongsListAdapter()

    private var sortByPrice = false

    private var term = ""


    override fun getViewBinding(layoutInflater: LayoutInflater): ActivitySearchSongBinding =
        ActivitySearchSongBinding.inflate(layoutInflater)


    override fun onCreateUI(savedInstanceState: Bundle?) {
        translucentStatusBar()

        binding.run {
            recySongs.run {
                layoutManager = LinearLayoutManager(this@SearchActivity)
                adapter = mAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        this@SearchActivity,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }

            rbOff.run {
                isChecked = true
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        rbSort.isChecked = false
                        sortByPrice = false
                        viewModel.search(term, false)
                    }
                }
            }

            rbSort.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    rbOff.isChecked = false
                    sortByPrice = true
                    viewModel.search(term, true)
                }
            }

            editSearch.doOnTextChanged { text, start, before, count ->
                text?.let {
                    term = it.toString()
                    viewModel.search(it.toString(), sortByPrice)
                }
            }
        }
    }

    override fun onCreatObserver() {
        viewModel.run {
            extLaunchWhenState {
                searchedSongsFlow.collect { songs ->
                    mAdapter.setNewInstance(songs)
                    binding.recySongs.scrollToPosition(0)
                    extLogPrint(songs.extToJsonString(), tag = TAG)

                }
            }

            extLaunchWhenState {
                fetchDataFailFlow.collect {
                    extToastShow("fetch song list fail!")
                }
            }
        }
    }


    override fun loadData() {
        extToastShow("fetch songs...")
        viewModel.fetchData()
    }

    private fun translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            with(window) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insetsController?.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                    statusBarColor = Color.TRANSPARENT
                }
            }
        } else {
            with(window) {
                decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                statusBarColor = Color.TRANSPARENT
            }
        }

    }

}
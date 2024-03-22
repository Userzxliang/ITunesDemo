package com.lxy.songsearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lxy.songsearch.ext.extLogPrint
import com.lxy.songsearch.ext.extToJsonString
import com.lxy.songsearch.model.SearchModel
import com.lxy.songsearch.pojo.SongInfo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val mModel = SearchModel()

    private val _songsFlow = MutableStateFlow<List<SongInfo>>(arrayListOf())
    val songsFlow = _songsFlow.asStateFlow()

    private val _fetchDataFailFlow = MutableSharedFlow<Throwable>()
    val fetchDataFailFlow = _fetchDataFailFlow.asSharedFlow()

    private val _searchedSongsFlow = MutableSharedFlow<List<SongInfo>>()
    val searchedSongsFlow = _searchedSongsFlow.asSharedFlow()


    fun fetchData() {
        viewModelScope.launch {
            runCatching {
                mModel.fetchData()
            }.onSuccess { response ->
                extLogPrint(response.extToJsonString())
                _songsFlow.emit(response.results)
                _searchedSongsFlow.emit(response.results)
            }.onFailure { throwable ->
                _fetchDataFailFlow.emit(throwable)
            }
        }
    }


    fun search(term: String, sortByPrice: Boolean) {
        viewModelScope.launch {
            var searchedSongs = if (term.isEmpty()) songsFlow.value else searchByTerm(term)
            if (sortByPrice) {
                searchedSongs = searchedSongs.sortedByDescending { it.trackPrice }
            }
            _searchedSongsFlow.emit(searchedSongs)
        }
    }


    private fun searchByTerm(term: String): List<SongInfo> {
        val fullSongs = songsFlow.value
        val searchedSongs = arrayListOf<SongInfo>()
        fullSongs.forEach { songInfo ->
            if (songInfo.trackName.contains(term, ignoreCase = true)) {
                searchedSongs.add(songInfo)
            }
        }
        return searchedSongs
    }


}
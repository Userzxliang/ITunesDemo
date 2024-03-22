package com.lxy.songsearch.model

import com.lxy.songsearch.net.iTunesApiService
import com.lxy.songsearch.net.response.ITunesSongSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchModel {
    private val TERM = "歌"
    private val LIMIT = 200
    private val COUNTRY = "HK"

    suspend fun fetchData(): ITunesSongSearchResponse = withContext(Dispatchers.IO) {
        iTunesApiService.search(TERM, LIMIT, COUNTRY)
    }

}
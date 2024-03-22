package com.lxy.songsearch.net.response

import com.lxy.songsearch.pojo.SongInfo

data class ITunesSongSearchResponse(
    val resultCount: Int,
    val results: List<SongInfo>
)
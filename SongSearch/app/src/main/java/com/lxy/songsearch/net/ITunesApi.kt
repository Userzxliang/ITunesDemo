package com.lxy.songsearch.net

import com.lxy.songsearch.net.response.ITunesSongSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {
    /**
     * API as follows: https://itunes.apple.com/search?term=歌&limit=200&country=HK
     * @param term String
     * @param limit Int
     * @param country String
     */
    @GET("search")
    suspend fun search(
        @Query("term") term: String,
        @Query("limit") limit: Int,
        @Query("country") country: String
    ): ITunesSongSearchResponse
}
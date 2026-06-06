//creado por CESAR ULISES BARILLA UMANA
package com.example.teachevent.data.remote

import com.example.techevent.data.remote.EventDto
import retrofit2.http.GET

interface ApiService {

    @GET("https://gist.githubusercontent.com/cesain1220/6d430492c0a5dc1d8008e10f79d205cf/raw/a314ad688329fbdca69194d0edf96e54499a50ed/events.json")
    suspend fun fetchEvents(): List<EventDto>
}
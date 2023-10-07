package com.example.bookkmm.data

import com.example.bookkmm.data.model.BookVolume

interface RemoteData {
    suspend fun getBooksFromApi(q:String): List<BookVolume.VolumeItem>
}
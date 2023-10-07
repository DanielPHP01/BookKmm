package com.example.bookkmm.data

import com.example.bookkmm.data.model.BookVolume


class RemoteDataSource(private val networkService: NetworkService) {
    suspend fun getBook(search: String): List<BookVolume.VolumeItem> {
        return networkService.getBooksFromApi(q = search)
    }
}
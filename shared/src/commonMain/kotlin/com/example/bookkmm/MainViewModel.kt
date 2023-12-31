package com.example.bookkmm

import Repository
import com.example.bookkmm.data.Either
import com.example.bookkmm.data.model.BookVolume
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

expect class MainViewModel(repository: Repository, scope: CoroutineScope) {
    val booksFlow:  Flow<List<BookVolume.VolumeItem>>
    val errorMessage: Flow<String?>
    fun getBooks(query: String)

}
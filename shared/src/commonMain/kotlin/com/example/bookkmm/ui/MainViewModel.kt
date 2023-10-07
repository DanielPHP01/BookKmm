package com.example.bookkmm.ui

import Repository
import com.example.bookkmm.data.model.BookVolume
import com.rickclephas.kmm.viewmodel.KMMViewModel
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val repository: Repository) : KMMViewModel() {
    init {
        getBooks()
    }

    fun getBooks(query: String = "book"): Flow<List<BookVolume.VolumeItem>> {
        return repository.getBooks(query)
    }
}
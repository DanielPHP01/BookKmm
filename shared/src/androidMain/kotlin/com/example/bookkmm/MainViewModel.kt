package com.example.bookkmm

import Repository
import com.example.bookkmm.data.model.BookVolume
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

actual class MainViewModel actual constructor(
    private val repository: Repository,
    private val scope: CoroutineScope
) : ViewModel() {
    private val _booksFlow = MutableStateFlow<List<BookVolume.VolumeItem>>(emptyList())
    actual val booksFlow: Flow<List<BookVolume.VolumeItem>> get() = _booksFlow

    actual fun getBooks(query: String) {
        repository.getBooks(query)
            .flowOn(Dispatchers.Main)
            .onEach { _booksFlow.value = it }
            .launchIn(scope)
    }
}
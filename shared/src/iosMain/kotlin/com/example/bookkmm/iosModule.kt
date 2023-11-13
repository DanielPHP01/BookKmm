package com.example.bookkmm

import Repository
import com.example.bookkmm.data.Either
import com.example.bookkmm.data.model.BookVolume
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Suppress("NO_ACTUAL_FOR_EXPECT")
actual class MainViewModel actual constructor(
    private val repository: Repository,
    private val scope: CoroutineScope
) : ViewModel() {
    private val _booksFlow = MutableStateFlow<List<BookVolume.VolumeItem>>(emptyList())
    actual val booksFlow:  Flow<List<BookVolume.VolumeItem>> get() = _booksFlow
    private var listener: BooksUpdateListener? = null

    private val _errorMessage = MutableStateFlow<String?>(null)
    actual val errorMessage: Flow<String?> get() = _errorMessage

    fun setBooksUpdateListener(listener: BooksUpdateListener?) {
        this.listener = listener
    }

    actual fun getBooks(query: String) {
        repository.getBooks(query)
            .flowOn(Dispatchers.Main)
            .onEach {
                when (it) {
                    is Either.Right -> {
                        _booksFlow.value = it.value
                        _errorMessage.value = null
                    }
                    is Either.Left ->  _errorMessage.value = it.value
                }
            }
            .launchIn(scope)
    }
}



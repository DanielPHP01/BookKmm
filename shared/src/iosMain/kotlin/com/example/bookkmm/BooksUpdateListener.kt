package com.example.bookkmm

import com.example.bookkmm.data.Either
import com.example.bookkmm.data.model.BookVolume

interface BooksUpdateListener {
    fun onBooksUpdated(books: Either<String,List<BookVolume.VolumeItem>>)
}
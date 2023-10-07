package com.example.bookkmm

import com.example.bookkmm.data.model.BookVolume

interface BooksUpdateListener {
    fun onBooksUpdated(books: List<BookVolume.VolumeItem>)
}
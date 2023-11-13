package com.example.bookkmm.data

import com.example.bookkmm.data.model.BookVolume

sealed class Either<out A, out B> {
    data class Left<out A>(val value: A) : Either<A, Nothing>()
    data class Right<out B>(val value: B) : Either<Nothing, B>()
}

interface RemoteData {
    suspend fun getBooksFromApi(q: String): Either<String, List<BookVolume.VolumeItem>>
}

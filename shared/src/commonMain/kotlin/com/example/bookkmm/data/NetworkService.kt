package com.example.bookkmm.data

import com.example.bookkmm.data.model.BookVolume
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

class NetworkService(private val httpClient: HttpClient) : RemoteData {
    override suspend fun getBooksFromApi(q: String): List<BookVolume.VolumeItem> {
        val response: HttpResponse = httpClient.get("https://www.googleapis.com/books/v1/volumes?q=$q")
        if (response.status == HttpStatusCode.OK) {
            return response.body<BookVolume>().items ?: emptyList()
        } else {
            throw Exception("Failed to fetch books: ${response.status}")
        }
    }
}
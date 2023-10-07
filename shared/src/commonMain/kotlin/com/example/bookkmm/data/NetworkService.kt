package com.example.bookkmm.data

import com.example.bookkmm.data.model.BookVolume
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json

class NetworkService(private val httpClient: HttpClient,private val json: Json) : RemoteData {
    override suspend fun getBooksFromApi(q: String): List<BookVolume.VolumeItem> {
        val response: HttpResponse = httpClient.get("https://www.googleapis.com/books/v1/volumes?q=$q")
        if (response.status == HttpStatusCode.OK) {
            val responseBody = response.bodyAsText()
            val bookVolume = json.decodeFromString<BookVolume>(responseBody)
            return bookVolume.items ?: emptyList()
        } else {
            throw Exception("Failed to fetch books: ${response.status}")
        }
    }
}
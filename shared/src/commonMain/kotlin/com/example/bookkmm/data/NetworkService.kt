package com.example.bookkmm.data

import com.example.bookkmm.data.model.BookVolume
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class NetworkService(private val httpClient: HttpClient, private val json: Json) : RemoteData {
    override suspend fun getBooksFromApi(q: String): Either<String, List<BookVolume.VolumeItem>> {
        return try {
            val response: HttpResponse = httpClient.get("https://www.googleapis.com/books/v1/volumes?q=$q")
            if (response.status == HttpStatusCode.OK) {
                val responseBody = response.bodyAsText()
                val bookVolume = json.decodeFromString<BookVolume>(responseBody)
                Either.Right(bookVolume.items ?: emptyList())
            } else {
                val errorResponseBody = response.bodyAsText()
                val errorJson = json.parseToJsonElement(errorResponseBody)
                val errorMessage = errorJson
                    .jsonObject
                    .get("error")
                    ?.jsonObject
                    ?.get("message")
                    ?.jsonPrimitive
                    ?.contentOrNull

                // Проверяем, что удалось извлечь сообщение об ошибке
                if (errorMessage != null) {
                    Either.Left(errorMessage)
                } else {
                    Either.Left("Не удалось получить книги: ${response.status}")
                }
            }
        } catch (e: Exception) {
            Either.Left("Произошла ошибка: ${e.message}")
        }
    }
}

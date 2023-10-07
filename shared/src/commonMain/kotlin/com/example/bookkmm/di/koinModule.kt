package com.example.bookkmm.di

import Repository
import com.example.bookkmm.data.NetworkService
import com.example.bookkmm.data.RemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun appModule() = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    }
                )
            }
        }
    }

    single { NetworkService(get()) }
    single { RemoteDataSource(get()) }
    single { Repository(get()) }
}

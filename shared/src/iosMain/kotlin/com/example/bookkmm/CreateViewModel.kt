package com.example.bookkmm

import Repository
import com.example.bookkmm.data.NetworkService
import com.example.bookkmm.data.RemoteDataSource
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json

object ViewModelFactory {
    fun createMainViewModel(): MainViewModel {
        val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
            prettyPrint = true
        }
        val httpClient = HttpClient()
        val networkService = NetworkService(httpClient, json)  // Now passing json as a parameter
        val remoteDataSource = RemoteDataSource(networkService)
        val repository = Repository(remoteDataSource)
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        return MainViewModel(repository, coroutineScope)
    }
}
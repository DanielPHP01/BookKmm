package com.example.bookkmm

import Repository
import com.example.bookkmm.data.NetworkService
import com.example.bookkmm.data.RemoteDataSource
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object ViewModelFactory {
    fun createMainViewModel(): MainViewModel {
        val httpClient = HttpClient()
        val networkService = NetworkService(httpClient)
        val remoteDataSource = RemoteDataSource(networkService)
        val repository = Repository(remoteDataSource)
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        return MainViewModel(repository, coroutineScope)
    }
}
package com.example.bookkmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
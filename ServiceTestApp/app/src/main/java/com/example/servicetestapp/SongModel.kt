package com.example.servicetestapp

import java.io.Serializable

data class SongModel(
    var title: String,
    var image: Int,
    var resource: Int
): Serializable

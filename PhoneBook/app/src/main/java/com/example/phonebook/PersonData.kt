package com.example.phonebook

import android.net.Uri

data class PersonData(
    val title: String,
    val description: String,
    val imageUrl: Uri,
)
package com.example.phonebook

import android.net.Uri

data class Person(
    val name: String,
    val phone: String,
    val imageUri: Uri?
)

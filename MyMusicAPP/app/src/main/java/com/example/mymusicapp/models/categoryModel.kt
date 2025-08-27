package com.example.mymusicapp.models

data class categoryModel(
    val name: String,
    val coverUrl : String,

){
    constructor(): this("", "");

}

package com.example.shoppingcartwithaisuggesttopratedproducts

object CartManager {
    private val items = mutableListOf<Product>()
    fun add(p: Product) { items.add(p) }
    fun remove(p: Product) { items.remove(p) }
    fun all(): List<Product> = items.toList()
    fun total(): Double = items.sumOf { it.price }
    fun count(): Int = items.size
}

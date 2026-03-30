package com.example.unit6cse226testing

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val mainActivity = MainActivity()
        val result = mainActivity.addNumbers(5, 3)
        println("Test Log: Sum of 5 and 3 is $result")
        assertEquals(8, result)
    }
}
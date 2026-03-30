package com.example.sem7testing

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val a = findViewById<EditText>(R.id.editA)
        val b = findViewById<EditText>(R.id.editB)
        val btn = findViewById<Button>(R.id.btnAdd)
        val result = findViewById<TextView>(R.id.txtResult)

        btn.setOnClickListener {
            Log.d("DEBUG", "Button clicked")

            try {

                val num1 = a.text.toString().toInt()
                val num2 = b.text.toString().toInt()
                Log.d("DEBUG", "num1 = $num1, num2 = $num2")
                result.text = "Result = ${num1 + num2}"

            } catch (e: Exception) {
                Log.d("ERROR", "Error occurred: ${e.message}")
                result.text = "Error: ${e.message}"
            }
        }
    }
}


//class CalculatorTest {
//
//    @Test
//    fun testMultiply() {
//        val calc = Calculator()
//        assertEquals(12, calc.multiply(3, 4))
//    }
//}

//class CalculatorTest {
//
//    @Test
//    fun testMultiply() {
//        val calc = Calculator()
//        assertEquals(12, calc.multiply(3, 4))
//    }
//}

//class Calculator {
//    fun multiply(a: Int, b: Int): Int = a * b
//}

//if (BuildConfig.DEBUG) {
//    Log.d("BUILD", "Debug Mode")
//} else {
//    Log.d("BUILD", "Release Mode – Ready for Play Store")
//}


//@RunWith(AndroidJUnit4::class)
//class LoginUITest {
//
//    @Test
//    fun testWelcomeMessage() {
//
//        onView(withId(R.id.edtUsername)).perform(typeText("Sumant"))
//        closeSoftKeyboard()
//
//        onView(withId(R.id.btnSubmit)).perform(click())
//
//        onView(withId(R.id.txtWelcome))
//            .check(matches(withText("Welcome Sumant")))
//    }
//}



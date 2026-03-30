package com.example.sem8canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class CarView(context: Context) : View(context) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 🛣 Road
        paint.color = Color.DKGRAY
        canvas.drawRect(0f, 800f, width.toFloat(), 1000f, paint)

        // 🚗 Car Body (Lower)
        paint.color = Color.RED
        canvas.drawRect(200f, 650f, 900f, 800f, paint)

        // 🚗 Car Top
        paint.color = Color.MAGENTA
        canvas.drawRect(350f, 550f, 750f, 650f, paint)

        // 🪟 Windows
        paint.color = Color.CYAN
        canvas.drawRect(380f, 570f, 520f, 640f, paint)
        canvas.drawRect(550f, 570f, 720f, 640f, paint)

        // ⚫ Wheels
        paint.color = Color.BLACK
        canvas.drawCircle(350f, 800f, 50f, paint)
        canvas.drawCircle(750f, 800f, 50f, paint)

        // ⚪ Wheel center
        paint.color = Color.LTGRAY
        canvas.drawCircle(350f, 800f, 20f, paint)
        canvas.drawCircle(750f, 800f, 20f, paint)
    }
}

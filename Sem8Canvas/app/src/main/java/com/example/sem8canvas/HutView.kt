package com.example.sem8canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class HutView(context: Context) : View(context) {
    private val paint: Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.color = Color.GREEN
        canvas.drawRect(0f, 800f, 1080f, 1000f, paint)


        paint.color = Color.YELLOW
        canvas.drawRect(300f, 500f, 800f, 800f, paint)

        // Hot roof
        paint.color = Color.RED
        val roofPath = android.graphics.Path()
        roofPath.moveTo(250f, 500f)
        roofPath.lineTo(850f, 500f)
        roofPath.lineTo(550f, 350f)

        roofPath.close()
        canvas.drawPath(roofPath, paint)

        // Door
        paint.color = Color.BLUE
        canvas.drawRect(500f, 600f, 600f, 800f, paint)

        // window
        paint.color = Color.WHITE
        canvas.drawRect(350f, 600f, 450f, 700f, paint)


    }
}
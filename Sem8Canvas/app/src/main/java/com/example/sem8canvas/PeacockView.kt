package com.example.sem8canvas

import android.content.Context
import android.graphics.*
import android.view.View

class PeacockView(context: Context) : View(context) {

    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 🌱 Ground
        paint.color = Color.GREEN
        canvas.drawRect(0f, 900f, width.toFloat(), 1100f, paint)

        // 🦚 Body
        paint.color = Color.BLUE
        canvas.drawOval(450f, 650f, 650f, 850f, paint)

        // 🦚 Neck
        canvas.drawRect(520f, 550f, 580f, 700f, paint)

        // 🦚 Head
        canvas.drawCircle(550f, 520f, 40f, paint)

        // 👁 Eye
        paint.color = Color.WHITE
        canvas.drawCircle(560f, 510f, 8f, paint)
        paint.color = Color.BLACK
        canvas.drawCircle(560f, 510f, 4f, paint)

        // 🪶 Beak
        paint.color = Color.YELLOW
        val beak = Path().apply {
            moveTo(590f, 520f)
            lineTo(630f, 530f)
            lineTo(590f, 540f)
            close()
        }
        canvas.drawPath(beak, paint)

        // 🪶 Feather Tail
        paint.color = Color.CYAN
        for (i in -4..4) {
            canvas.drawArc(250f,500f,850f,1100f,(-90 + i * 12).toFloat(),
                10f,true, paint)
        }

        // 👑 Crown
        paint.color = Color.MAGENTA
        canvas.drawLine(550f, 480f, 550f, 450f, paint)
        canvas.drawCircle(550f, 440f, 6f, paint)
    }
}

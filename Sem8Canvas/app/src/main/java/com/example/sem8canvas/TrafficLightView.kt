package com.example.trafficlight

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class TrafficLightView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var detachProgress = 0f
    private var isDetached = false

    fun toggleDetach() {

        val target = if (isDetached) 0f else 1f

        val animator = ValueAnimator.ofFloat(detachProgress, target)
        animator.duration = 800

        animator.addUpdateListener {
            detachProgress = it.animatedValue as Float
            invalidate()
        }

        animator.start()
        isDetached = !isDetached
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()
        val centerX = w / 2

        val radius = w * 0.07f

        // Strong detach movement
        val moveUp = -h * 0.30f * detachProgress
        val moveDown = h * 0.30f * detachProgress
        val moveLeft = -w * 0.30f * detachProgress
        val moveRight = w * 0.30f * detachProgress

        // ===== Stick (move down fully) =====
        paint.color = Color.DKGRAY
        canvas.drawRect(
            centerX - w * 0.03f,
            h * 0.75f + moveDown,
            centerX + w * 0.03f,
            h + moveDown,
            paint
        )

        // ===== Brown Rectangle (move right) =====
        paint.color = Color.rgb(139, 69, 19)
        canvas.drawRect(
            w * 0.35f + moveRight,
            h * 0.25f,
            w * 0.65f + moveRight,
            h * 0.75f,
            paint
        )

        // ===== Triangle (move up fully) =====
        paint.color = Color.BLACK
        val path = Path()
        path.moveTo(centerX, h * 0.15f + moveUp)
        path.lineTo(w * 0.35f, h * 0.25f + moveUp)
        path.lineTo(w * 0.65f, h * 0.25f + moveUp)
        path.close()
        canvas.drawPath(path, paint)

//         ===== Red (move left + up) =====
        paint.color = Color.RED
        canvas.drawCircle(
            centerX + moveLeft,
            h * 0.35f + moveUp,
            radius,
            paint
        )

        // ===== Yellow (move right) =====
        paint.color = Color.YELLOW
        canvas.drawCircle(
            centerX + moveRight,
            h * 0.50f,
            radius,
            paint
        )

        // ===== Green (move left + down) =====
        paint.color = Color.GREEN
        canvas.drawCircle(
            centerX + moveLeft,
            h * 0.65f + moveDown,
            radius,
            paint
        )
    }
}
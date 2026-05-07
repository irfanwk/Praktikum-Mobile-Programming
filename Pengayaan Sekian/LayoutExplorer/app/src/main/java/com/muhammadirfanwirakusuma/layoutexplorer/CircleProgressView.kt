package com.muhammadirfanwirakusuma.layoutexplorer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class CircleProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress = 0f
    private val strokeWidth = 20f

    private val backgroundPaint = Paint().apply {
        color = Color.LTGRAY
        style = Paint.Style.STROKE
        this.strokeWidth = this@CircleProgressView.strokeWidth
        isAntiAlias = true
    }

    private val progressPaint = Paint().apply {
        color = Color.parseColor("#1565C0")
        style = Paint.Style.STROKE
        this.strokeWidth = this@CircleProgressView.strokeWidth
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }

    private val rectF = RectF()

    fun setProgress(value: Float) {
        progress = value
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (Math.min(width, height) / 2f) - strokeWidth

        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

        val sweepAngle = (progress / 100f) * 360f
        canvas.drawArc(rectF, -90f, sweepAngle, false, progressPaint)
    }
}
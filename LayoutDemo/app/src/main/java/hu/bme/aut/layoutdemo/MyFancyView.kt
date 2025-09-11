package hu.bme.aut.layoutdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.Button

class MyFancyView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var paintBg = Paint()
    var paintLine = Paint()

    init {
        paintBg.color = Color.GREEN
        paintBg.style = Paint.Style.FILL

        paintLine.color = Color.BLUE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        /*canvas.drawRect(0f,0f,
            width.toFloat(), height.toFloat(), paintBg)*/
        canvas.drawLine(0f,0f,
            width.toFloat(), height.toFloat(), paintLine)
    }
}
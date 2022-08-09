package uz.gita.app2048MBF.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class MyTouchListener(context: Context) : View.OnTouchListener {
    private val gesture = GestureDetector(context, MyGestureDetector())
    private var resultListener: ((SideEnums) -> Unit)? = null

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        gesture.onTouchEvent(event)
        return true
    }

    inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {
        private val MIN_MOVE: Int = 100

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (abs(e2.x - e1.x) > abs(e2.y - e1.y)) {
                // horizontal
                if (abs(e2.x - e1.x) < MIN_MOVE) return false
                if (e2.x > e1.x) resultListener?.invoke(SideEnums.RIGHT)
                else resultListener?.invoke(SideEnums.LEFT)
            } else {
                // vertical
                if (abs(e2.y - e1.y) < MIN_MOVE) return false
                if (e2.y > e1.y) resultListener?.invoke(SideEnums.DOWN)
                else resultListener?.invoke(SideEnums.UP)
            }
            return true
        }
    }

    fun setResultListener(block: (SideEnums) -> Unit) {
        resultListener = block
    }
}


package uz.gita.app2048MBF.utils

import uz.gita.app2048MBF.R

object BackgroundUtils {
     val backgrounds =
        arrayOf(
            R.drawable.cell_rectangle,
            R.drawable.cell_rectangle2,
            R.drawable.cell_rectangle4,
            R.drawable.cell_rectangle8,
            R.drawable.cell_rectangle16,
            R.drawable.cell_rectangle32,
            R.drawable.cell_rectangle64,
            R.drawable.cell_rectangle128,
            R.drawable.cell_rectangle256,
            R.drawable.cell_rectangle512,
            R.drawable.cell_rectangle1024,
            R.drawable.cell_rectangle2048,
            R.drawable.cell_rectangle4096,
        )
}
fun Int.getBackgroundByValue(): Int {
    var value = this
    var degree = 0
    while (value > 0) {
        value /= 2
        degree++
    }
    return BackgroundUtils.backgrounds[degree]
}
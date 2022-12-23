package danyil.karabinovskyi.studenthub.common.utils

import android.graphics.Color
import kotlin.math.roundToInt

public fun adjustColorBrightness(color: Int, factor: Float): Int {
    val a = Color.alpha(color)
    val r = (Color.red(color) * factor).roundToInt()
    val g = (Color.green(color) * factor).roundToInt()
    val b = (Color.blue(color) * factor).roundToInt()
    return Color.argb(
        a,
        r.coerceAtMost(MAX_COLOR_COMPONENT_VALUE),
        g.coerceAtMost(MAX_COLOR_COMPONENT_VALUE),
        b.coerceAtMost(MAX_COLOR_COMPONENT_VALUE)
    )
}

private const val MAX_COLOR_COMPONENT_VALUE = 255

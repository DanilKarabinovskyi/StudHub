package danyil.karabinovskyi.studenthub.common.extensions

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.convertToByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 90, stream)
    return stream.toByteArray()
}
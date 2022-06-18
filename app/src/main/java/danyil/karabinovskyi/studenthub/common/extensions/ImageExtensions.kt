package danyil.karabinovskyi.studenthub.common.extensions

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import java.io.ByteArrayOutputStream

fun Bitmap.convertToByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 90, stream)
    return stream.toByteArray()
}

fun ContentResolver.getFileName(uri: Uri): String {
    val returnCursor = query(uri, null, null, null, null) ?: return ""
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val fileName = returnCursor.getString(nameIndex)
    returnCursor.close()
    return fileName
}
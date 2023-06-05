package danyil.karabinovskyi.studenthub.common.extensions

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


//fun File.toBase64(){
//    return Base64.encodeToString(this.readBytes(), Base64.NO_WRAP)
//}


fun Context.saveFileFromUriToAttachments(fileUri: Uri, fileName: String): File? {
    val file: File? = this.getFileInCache(ATTACHMENTS, fileName)
    if (file != null) {
        try {
            this.contentResolver.openInputStream(fileUri).use { fileStream ->
                FileOutputStream(file).use { fos ->
                    assert(fileStream != null)
                    val buffer = ByteArray(1024)
                    var readCount: Int
                    while (fileStream!!.read(buffer).also { readCount = it } != -1) {
                        fos.write(buffer, 0, readCount)
                    }
                    fos.flush()
                }
            }
        } catch (e: IOException) {
            Timber.e(e)
            return null
        }
    }
    return file
}

fun Context.getFileInCache( folder: String, fileName: String): File? {
    val dir = File(this.cacheDir, folder)
    return if (!dir.exists() && !dir.mkdir()) {
        null
    } else File(dir, fileName)
}
fun Context.doesFileExistInAttachments(filename: String): Boolean {
    val file: File? = this.getFileInCache(ATTACHMENTS, filename)
    return file != null && file.exists()
}
fun Context.getFileName( uri: Uri): String {
    if (uri.scheme == "content") {
        val cursor = this.contentResolver.query(uri, null, null, null, null)
        cursor.use {
            if (cursor != null) {
                if(cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
    }

    return uri.path?.substring(uri.path!!.lastIndexOf('/') + 1) ?: " "
}
const val ATTACHMENTS = "Attachments"
const val SHARE_EXTERNAL = "share_external"
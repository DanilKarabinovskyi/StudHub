package danyil.karabinovskyi.studenthub.features.auth.presentation.document_scan

import android.content.Intent
import android.os.Bundle
import com.zynksoftware.documentscanner.ScanActivity
import com.zynksoftware.documentscanner.model.DocumentScannerErrorModel
import com.zynksoftware.documentscanner.model.ScannerResults

import danyil.karabinovskyi.studenthub.R


class AppScanActivity: ScanActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_scan_activity_layout)
        addFragmentContentLayout()
    }

    private var path = APP_SCAN_ACTIVITY_ERROR

    override fun onError(error: DocumentScannerErrorModel) {
        sendActivityResult()
    }

    override fun onSuccess(scannerResults: ScannerResults) {
        var i = scannerResults
        val imgFile = scannerResults.croppedImageFile

        if (imgFile != null) {
            if (imgFile.exists()) {
                path = imgFile.absolutePath
                sendActivityResult()
            }
        }
    }
    private fun sendActivityResult() {
        setResult(RESULT_OK, Intent().apply {
            putExtra(APP_SCAN_ACTIVITY_KEY, path)
        })
        finish()
    }
    override fun onClose() {
        finish()
    }

    companion object{
        const val APP_SCAN_ACTIVITY_KEY = "app scan key"
        const val APP_SCAN_ACTIVITY_ERROR = "that's sucks"
    }
}
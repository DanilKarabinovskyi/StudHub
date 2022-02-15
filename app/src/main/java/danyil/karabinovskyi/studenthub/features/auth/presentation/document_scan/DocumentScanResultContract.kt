package danyil.karabinovskyi.studenthub.features.auth.presentation.document_scan

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class DocumentScanResultContract :
    ActivityResultContract<Int, String>() {

    override fun parseResult(
        resultCode: Int, intent: Intent?
    ): String {
        return if (resultCode == Activity.RESULT_OK) {
            return intent?.getStringExtra(AppScanActivity.APP_SCAN_ACTIVITY_KEY)
                ?: AppScanActivity.APP_SCAN_ACTIVITY_ERROR
        } else {
            AppScanActivity.APP_SCAN_ACTIVITY_ERROR
        }
    }

    override fun createIntent(context: Context, input: Int): Intent {
        return Intent(context, AppScanActivity::class.java).apply {
        }
    }
}
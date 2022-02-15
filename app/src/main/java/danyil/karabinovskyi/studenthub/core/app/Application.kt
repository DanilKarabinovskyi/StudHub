package danyil.karabinovskyi.studenthub.core.app

import android.app.Application
import android.graphics.Bitmap
import com.zynksoftware.documentscanner.ui.DocumentScanner
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}
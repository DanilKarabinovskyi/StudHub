package danyil.karabinovskyi.studenthub.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import coil.ImageLoader
import dagger.hilt.android.AndroidEntryPoint
import danyil.karabinovskyi.studenthub.components.back_press.LocalBackPressedDispatcher
import danyil.karabinovskyi.studenthub.core.app.StudentHubApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var imageLoader: ImageLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                LocalBackPressedDispatcher provides this.onBackPressedDispatcher
            ) {
                StudentHubApp(imageLoader)
            }
        }
    }
}
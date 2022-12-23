package danyil.karabinovskyi.studenthub.features.dashboard.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import danyil.karabinovskyi.studenthub.components.surface.Surface
import danyil.karabinovskyi.studenthub.components.text_fields.TransparentTextField
import danyil.karabinovskyi.studenthub.features.home.presentation.addHomeGraph

@Composable
fun DashboardScreen(
) {

    Surface(modifier = Modifier.fillMaxSize()) {
        TransparentTextField(
            textLabel = "Dashboard",
            keyboardType = KeyboardType.Number,
            keyboardActions = KeyboardActions.Default,
            imeAction = ImeAction.Default,
            onValueChange = {  }
        )
    }

}
package danyil.karabinovskyi.studenthub.features.feed.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar

@Composable
fun FeedScreen(
) {
    fun getFirebaseToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
        })
    }
    val context = LocalContext.current
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        getFirebaseToken()
        if (isGranted) {
            getFirebaseToken()
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            val a = 0
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity,
                Manifest.permission.POST_NOTIFICATIONS)) {
            val a = 0
        } else {
            // Directly ask for the permission
            SideEffect {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

            }
        }
    StandardToolbar(
        title = {
            Text(
                text = stringResource(id = R.string.feed),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        },
        modifier = Modifier.fillMaxWidth(),
        showBackArrow = false,
        navActions = {
            IconButton(onClick = {
//                onNavigate(Screen.SearchScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        }
    )
}
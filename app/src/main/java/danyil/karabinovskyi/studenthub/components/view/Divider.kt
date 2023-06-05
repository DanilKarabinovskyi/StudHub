package danyil.karabinovskyi.studenthub.components.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun StudDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(color = StudentHubTheme.colorsV2.onBackground)
            .padding(bottom = StudentHubTheme.dimensions.SpaceSSSSmall)
    )
}
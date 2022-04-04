package danyil.karabinovskyi.studenthub.components.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import danyil.karabinovskyi.studenthub.core.domain.model.Filter
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme

@Composable
fun BottomSheetItem(filter: Filter, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(filter.name) })
            .height(55.dp)
            .background(color = StudentHubTheme.colors.uiFloated)
            .padding(start = 15.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        filter.icon?.let {
            Icon(
                imageVector = it,
                contentDescription = "Share",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = filter.name, color = Color.White)
    }
}
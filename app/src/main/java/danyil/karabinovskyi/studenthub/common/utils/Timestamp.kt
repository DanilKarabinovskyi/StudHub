package danyil.karabinovskyi.studenthub.common.utils

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme
import java.util.Date


@Composable
public fun Timestamp(
    date: Date?,
    modifier: Modifier = Modifier,
    formatter: DateFormatter = StudentHubTheme.dateFormatter,
    formatType: DateFormatType = DateFormatType.DATE,
) {
    val timestamp = if (LocalInspectionMode.current) {
        "13:49"
    } else {
        when (formatType) {
            DateFormatType.TIME -> formatter.formatTime(date)
            DateFormatType.DATE -> formatter.formatDate(date)
        }
    }

    Text(
        modifier = modifier,
        text = timestamp,
        color = StudentHubTheme.colors.brand
    )
}

public enum class DateFormatType {
    TIME,
    DATE,
}


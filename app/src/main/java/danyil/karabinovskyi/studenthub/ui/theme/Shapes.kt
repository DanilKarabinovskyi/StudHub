package danyil.karabinovskyi.studenthub.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class Shapes(
    val avatar: Shape,
    val myMessageBubble: Shape,
    val otherMessageBubble: Shape,
    val inputField: Shape,
    val attachment: Shape,
    val imageThumbnail: Shape,
    val bottomSheet: Shape,
    val suggestionList: Shape,
    val attachmentSiteLabel: Shape,
    val header: Shape,
    val quotedAttachment: Shape,
) {
    companion object {
        fun defaultShapes(): Shapes = Shapes(
            avatar = CircleShape,
            myMessageBubble = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp),
            otherMessageBubble = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp),
            inputField = RoundedCornerShape(24.dp),
            attachment = RoundedCornerShape(16.dp),
            imageThumbnail = RoundedCornerShape(8.dp),
            bottomSheet = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            suggestionList = RoundedCornerShape(16.dp),
            attachmentSiteLabel = RoundedCornerShape(topEnd = 14.dp),
            header = RectangleShape,
            quotedAttachment = RoundedCornerShape(4.dp)
        )
    }
}


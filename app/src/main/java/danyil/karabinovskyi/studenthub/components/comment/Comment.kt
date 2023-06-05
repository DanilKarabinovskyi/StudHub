package danyil.karabinovskyi.studenthub.components.comment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.components.text.StudText
import danyil.karabinovskyi.studenthub.core.domain.model.Comment
import danyil.karabinovskyi.studenthub.ui.theme.*

@ExperimentalCoilApi
@Composable
fun Comment(
    modifier: Modifier = Modifier,
    comment: Comment,
    imageLoader: ImageLoader,
    onLikeClick: () -> Unit = {},
    onLikedByClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = 5.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = StudentHubTheme.colorsV2.secondaryBackground,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SpaceMedium)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = comment.profilePictureUrl,
                            imageLoader = imageLoader
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(ProfilePictureSizeExtraSmall)
                    )
                    Spacer(modifier = Modifier.width(SpaceSmall))
                    StudText(
                        text = comment.username,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body2,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    StudText(
                        text = comment.formattedTime,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                ) {
                    StudText(
                        text = comment.comment,
                        style = StudentHubTheme.typography.bodySmall,
                        color = StudentHubTheme.colorsV2.textHighEmphasis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                onLikeClick()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                tint = if (comment.isLiked) {
                                    Rose9
                                } else {
                                    Rose0
                                },
                                contentDescription = if (comment.isLiked) {
                                    stringResource(id = R.string.unlike)
                                } else stringResource(id = R.string.like)
                            )
                        }
                        StudText(
                            text = stringResource(id = R.string.x_likes, comment.likeCount),
                            fontWeight = FontWeight.Bold,
                            style = StudentHubTheme.typography.bodySmall,
                            color = StudentHubTheme.colorsV2.textHighEmphasis,
                            modifier = Modifier.weight(6f)
                                .clickable {
                                    onLikedByClick()
                                }
                        )
                    }

                }
            }
        }
    }
}
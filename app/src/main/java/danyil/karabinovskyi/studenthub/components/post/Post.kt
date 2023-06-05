package danyil.karabinovskyi.studenthub.components.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import danyil.karabinovskyi.studenthub.common.utils.Constants
import danyil.karabinovskyi.studenthub.components.text.StudText
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post
import danyil.karabinovskyi.studenthub.ui.theme.*

@ExperimentalCoilApi
@Composable
fun Post(
    post: Post,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    showProfileImage: Boolean = true,
    canDelete: Boolean = false,
    onPostClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onUsernameClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(SpaceMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .shadow(5.dp)
                .background(MediumGray)
                .clickable {
                    onPostClick()
                }
        ) {
            Image(
                painter = rememberImagePainter(
                    data = post.imageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = "Post image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceMedium)
            ) {
                ActionRow(
                    title = post.title,
                    modifier = Modifier.fillMaxWidth(),
                    isLiked = post.isLiked,
                    onLikeClick = onLikeClick,
                    onCommentClick = onCommentClick,
                    onShareClick = onShareClick,
                    onUsernameClick = onUsernameClick,
                    onDeleteClick = onDeleteClick
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = StudentHubTheme.colorsV2.textHighEmphasis,
                            )
                        ) {
                            append(post.description)
                        }
                        withStyle(
                            SpanStyle(
                                color = StudentHubTheme.colorsV2.textLowEmphasis,
                                fontSize = StudentHubTheme.dimensions.SizeSmall
                            )
                        ) {
                            append(
                                " " + LocalContext.current.getString(
                                    danyil.karabinovskyi.studenthub.R.string.read_more
                                )
                            )
                        }
                    },
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = Constants.MAX_POST_DESCRIPTION_LINES
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StudText(
                        text = stringResource(
                            id = danyil.karabinovskyi.studenthub.R.string.x_likes,
                            post.likesCount
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2
                    )
                    StudText(
                        text = stringResource(
                            id = danyil.karabinovskyi.studenthub.R.string.x_comments,
                            post.commentsCount
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.h2
                    )
                }
            }
        }
        if (showProfileImage) {
            Image(
                painter = rememberImagePainter(
                    data = post.profilePictureUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(ProfilePictureSizeSmall)
                    .clip(CircleShape)
                    .align(Alignment.TopEnd)
                    .padding(SpaceExtraSmall)
            )
        }
    }
}

@Composable
fun EngagementButtons(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    iconSize: Dp = 30.dp,
    canDelete: Boolean = false,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            onClick = {
                onLikeClick()
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                tint = if (isLiked) {
                    Rose9
                } else {
                    StudentHubTheme.colorsV2.iconPrimary
                },
                contentDescription = if (isLiked) {
                    stringResource(
                        id =
                        danyil.karabinovskyi.studenthub.R.string.unlike
                    )
                } else {
                    stringResource(
                        id =
                        danyil.karabinovskyi.studenthub.R.string.like
                    )
                }
            )
        }
        Spacer(modifier = Modifier.width(SpaceMedium))
        IconButton(
            onClick = {
                onCommentClick()
            },
            modifier = Modifier.size(iconSize)
        ) {
            Icon(
                imageVector = Icons.Filled.Comment,
                contentDescription = stringResource(
                    id =
                    danyil.karabinovskyi.studenthub.R.string.comment
                ),
                tint = StudentHubTheme.colorsV2.iconPrimary
            )
        }
        Spacer(modifier = Modifier.width(SpaceMedium))
        IconButton(
            onClick = {
                onShareClick()
            },
            modifier = Modifier.size(iconSize),
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(
                    id =
                    danyil.karabinovskyi.studenthub.R.string.share
                ),
                tint = StudentHubTheme.colorsV2.iconPrimary
            )
        }
        Spacer(modifier = Modifier.width(SpaceMedium))
        if (canDelete) {
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(iconSize)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(
                        id =
                        danyil.karabinovskyi.studenthub.R.string.delete_post
                    ),
                    tint = StudentHubTheme.colorsV2.iconPrimary
                )
            }
        }
    }
}

@Composable
fun ActionRow(
    modifier: Modifier = Modifier,
    isLiked: Boolean = false,
    title: String,
    canDelete: Boolean = false,
    onLikeClick: () -> Unit = {},
    onCommentClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onUsernameClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = StudentHubTheme.colorsV2.textHighEmphasis,
                fontSize = StudentHubTheme.dimensions.SizeExtraLarge
            ),
            modifier = Modifier
                .clickable {
                    onUsernameClick()
                }
                .padding(top = SpaceSmall, bottom = SpaceSmall)
        )
        EngagementButtons(
            isLiked = isLiked,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
            onShareClick = onShareClick,
            onDeleteClick = onDeleteClick,
            canDelete = canDelete,
        )
    }
}
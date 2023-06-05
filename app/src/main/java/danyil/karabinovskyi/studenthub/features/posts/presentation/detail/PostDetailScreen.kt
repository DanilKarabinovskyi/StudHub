package danyil.karabinovskyi.studenthub.features.posts.presentation.detail

import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.extensions.observeLifecycle
import danyil.karabinovskyi.studenthub.common.extensions.sendSharePostIntent
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.model.asString
import danyil.karabinovskyi.studenthub.common.model.showKeyboard
import danyil.karabinovskyi.studenthub.components.comment.Comment
import danyil.karabinovskyi.studenthub.components.post.ActionRow
import danyil.karabinovskyi.studenthub.components.text.StudText
import danyil.karabinovskyi.studenthub.components.text_fields.SendTextField
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar
import danyil.karabinovskyi.studenthub.core.app.MainDestinations
import danyil.karabinovskyi.studenthub.ui.theme.*
import kotlinx.coroutines.flow.collectLatest
import java.io.File


@ExperimentalCoilApi
@Composable
fun PostDetailScreen(
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PostDetailViewModel = hiltViewModel(),
    shouldShowKeyboard: Boolean = false,
) {
    val state = viewModel.state.value
//    val state by viewModel.loadPostDetails().collectAsState()
    val commentTextFieldState = viewModel.commentTextFieldState.value
    lateinit var manager: DownloadManager
    val focusRequester = remember {
        FocusRequester()
    }
    viewModel.observeLifecycle(LocalLifecycleOwner.current.lifecycle)
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        if (shouldShowKeyboard) {
            context.showKeyboard()
            focusRequester.requestFocus()
        }
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp.invoke()
                }
                else -> {}
            }
        }

    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = stringResource(id = R.string.post_details),
                    fontWeight = FontWeight.Bold,
                    color = StudentHubTheme.colorsV2.primary
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
            navActions = {
                IconButton(onClick = {
                    onNavigate(MainDestinations.POSTS_CREATE_EDIT + "/${viewModel.state.value.postId}")
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "",
                        tint = StudentHubTheme.colorsV2.iconPrimary,
                    )
                }
            }
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(StudentHubTheme.colorsV2.background),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(StudentHubTheme.colorsV2.secondaryBackground)
                ) {
                    Spacer(modifier = androidx.compose.ui.Modifier.height(SpaceLarge))
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = ProfilePictureSizeMedium / 2f)
                                .clip(MaterialTheme.shapes.medium)
                                .shadow(5.dp)
                                .background(MediumGray)
                        ) {
                            state.post?.let { post ->
                                Image(
                                    painter = rememberImagePainter(
                                        data = state.post.imageUrl,
                                        imageLoader = imageLoader
                                    ),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "Post image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f)
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(SpaceLarge)
                                ) {
                                    ActionRow(
                                        title = state.post.title,
                                        modifier = Modifier.fillMaxWidth(),
                                        onDeleteClick = {
                                            viewModel.onEvent(PostDetailEvent.DeletePost)
                                        },
                                        onLikeClick = {
                                            viewModel.onEvent(PostDetailEvent.LikePost)
                                        },
                                        onCommentClick = {
                                            context.showKeyboard()
                                            focusRequester.requestFocus()
                                        },
                                        onShareClick = {
                                            context.sendSharePostIntent(post.id)
                                        },
                                        onUsernameClick = {
//                                            onNavigate(Screen.ProfileScreen.route + "?userId=${post.userId}")
                                        },
                                        isLiked = state.post.isLiked,
                                        canDelete = state.post.isOwnPost
                                    )
                                    Spacer(modifier = androidx.compose.ui.Modifier.height(SpaceSmall))
                                    Text(
                                        text = state.post.description,
                                        style = MaterialTheme.typography.body2,
                                    )
                                    Spacer(
                                        modifier = androidx.compose.ui.Modifier.height(
                                            SpaceMedium
                                        )
                                    )
                                    Text(
                                        text = stringResource(
                                            id = danyil.karabinovskyi.studenthub.R.string.x_likes,
                                            post.likesCount
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.body2,
                                        modifier = Modifier.clickable {
//                                            onNavigate(Screen.PersonListScreen.route + "/${post.id}")
                                        }
                                    )
                                    LazyRow {
                                        post.attachments?.let {
                                            items(it.size) { i ->
                                                val attachment = post.attachments[i]
                                                Column(modifier = Modifier.width(100.dp)) {
                                                    Icon(
                                                        modifier = Modifier.size(40.dp),
                                                        imageVector = Icons.Default.PictureAsPdf,
                                                        contentDescription = null,
                                                        tint = Color.Red
                                                    )
                                                    StudText(text = attachment.filename)
                                                    Button(
                                                        shape = StudentHubTheme.shapes.quotedAttachment,
                                                        colors = ButtonDefaults.buttonColors(
                                                            backgroundColor = StudentHubTheme.colorsV2.buttonBackgroundPrimary
                                                        ),
                                                        onClick = {

                                                            val filename = attachment.url.substring(attachment.url.lastIndexOf("/") + 1)
                                                            val file =
                                                                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/StudentsHub/" + filename)
                                                            Log.d("Environment", "Environment extraData=" + file.getPath())
                                                            val request = DownloadManager.Request(Uri.parse(attachment.url))
                                                                .setTitle(filename)
                                                                .setDescription("Downloading")
                                                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                                                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                                                                .setDestinationUri(Uri.fromFile(file))
                                                                .setAllowedOverMetered(true)
                                                                .setAllowedOverRoaming(true)
                                                            manager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                                                            val referenceID = manager.enqueue(request)
                                                            Toast.makeText(context,"File is downloading... Check your storage",Toast.LENGTH_LONG).show()
                                                        },
                                                        enabled = true,
                                                        modifier = Modifier
                                                            .align(Alignment.Start),
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.Download,
                                                            contentDescription = null,
                                                            tint = StudentHubTheme.colorsV2.iconPrimary
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        Image(
                            painter = rememberImagePainter(
                                data = state.post?.profilePictureUrl,
                                imageLoader = imageLoader
                            ),
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(ProfilePictureSizeMedium)
                                .clip(CircleShape)
                                .align(Alignment.TopCenter)
                        )
                        if (state.isLoadingPost) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(SpaceExtraLarge))
            }
            items(state.comments) { comment ->
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = SpaceSmall,
                            vertical = SpaceMedium
                        ),
                    imageLoader = imageLoader,
                    comment = comment,
                    onLikeClick = {
                        viewModel.onEvent(PostDetailEvent.LikeComment(comment.id))
                    },
                    onLikedByClick = {
//                        onNavigate(Screen.PersonListScreen.route + "/${comment.id}")
                    }
                )
            }
        }
        SendTextField(
            state = viewModel.commentTextFieldState.value,
            onValueChange = {
                viewModel.onEvent(PostDetailEvent.EnteredComment(it))
            },
            onSend = {
                viewModel.onEvent(PostDetailEvent.Comment)
            },
            hint = stringResource(id = R.string.enter_a_comment),
            isLoading = viewModel.commentState.value.isLoading,
            focusRequester = focusRequester
        )
    }
}
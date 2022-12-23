package danyil.karabinovskyi.studenthub.features.posts.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.extensions.observeLifecycle
import danyil.karabinovskyi.studenthub.common.extensions.sendSharePostIntent
import danyil.karabinovskyi.studenthub.components.bottom_sheet.BottomSheet
import danyil.karabinovskyi.studenthub.components.filter_bar.FilterBar
import danyil.karabinovskyi.studenthub.components.post.Post
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar
import danyil.karabinovskyi.studenthub.core.app.MainDestinations
import danyil.karabinovskyi.studenthub.core.domain.model.sortFilters
import danyil.karabinovskyi.studenthub.ui.theme.SpaceLarge
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class, coil.annotation.ExperimentalCoilApi::class)
@Composable
fun PostsScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    viewModel: PostsViewModel = hiltViewModel()
) {
    val pagingState = viewModel.pagingState.value
    val filters = viewModel.formFilters
    val context = LocalContext.current
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    viewModel.observeLifecycle(LocalLifecycleOwner.current.lifecycle)
    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheet(sortFilters, bottomSheetState, onItemClick = { filterName ->
                viewModel.loadNextPosts(true, socialTag = filterName)
            })
        },
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = StudentHubTheme.colors.brand,
        sheetContentColor = StudentHubTheme.colors.brand,
        scrimColor = androidx.compose.ui.graphics.Color.Transparent
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            StandardToolbar(
                title = {
                    Text(
                        text = stringResource(id = R.string.posts),
                        fontWeight = FontWeight.Bold,
                        color = StudentHubTheme.colors.textPrimary
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = false,
                navActions = {
                    IconButton(onClick = {
                        onNavigate(MainDestinations.POSTS_CREATE_EDIT + "/${-777}")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            tint = StudentHubTheme.colors.iconPrimary,
                        )
                    }
                }
            )
            FilterBar(
                filters,
                showFilterIcon = true,
                onShowFilters = { scope.launch { bottomSheetState.show() } },
                onFilterClick = { filters ->
                    viewModel.loadNextPosts(true,filters)
                })
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
                    items(pagingState.items.size) { i ->
                        val post = pagingState.items[i]
                        if (i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
                            viewModel.loadNextPosts(false)
                        }
                        Post(
                            post = post,
                            imageLoader = imageLoader,
                            onUsernameClick = {
//                                onNavigate(MainDestinations.POSTS_DETAIL + "?userId=${post.userId}")
                            },
                            onPostClick = {
                                onNavigate(MainDestinations.POSTS_DETAIL + "/${post.id}")
                            },
                            onCommentClick = {
                                onNavigate(MainDestinations.POSTS_DETAIL + "/${post.id}?shouldShowKeyboard=true")
                            },
                            onLikeClick = {
                                viewModel.onEvent(PostsEvents.LikedPost(post))
                            },
                            onShareClick = {
                                context.sendSharePostIntent(post.id)
                            },
                            onDeleteClick = {
                                viewModel.onEvent(PostsEvents.DeletePost(post))
                            }
                        )
                        if (i < pagingState.items.size - 1) {
                            Spacer(modifier = Modifier.height(SpaceLarge))
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(90.dp))
                    }
                }
                if (pagingState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
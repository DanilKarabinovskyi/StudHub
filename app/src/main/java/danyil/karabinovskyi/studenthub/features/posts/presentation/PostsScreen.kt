package danyil.karabinovskyi.studenthub.features.posts.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.decode.SvgDecoder
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.extensions.observeLifecycle
import danyil.karabinovskyi.studenthub.components.bottom_sheet.BottomSheet
import danyil.karabinovskyi.studenthub.components.filter_bar.FilterBar
import danyil.karabinovskyi.studenthub.components.post.Post
import danyil.karabinovskyi.studenthub.components.toolbar.StandardToolbar
import danyil.karabinovskyi.studenthub.core.app.MainDestinations
import danyil.karabinovskyi.studenthub.core.domain.model.postsTags
import danyil.karabinovskyi.studenthub.core.domain.model.sortFilters
import danyil.karabinovskyi.studenthub.ui.theme.FunctionalRed
import danyil.karabinovskyi.studenthub.ui.theme.Neutral7
import danyil.karabinovskyi.studenthub.ui.theme.StudentHubTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostsScreen(
    imageLoader: ImageLoader,
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PostsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    viewModel.observeLifecycle(LocalLifecycleOwner.current.lifecycle)
    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheet(sortFilters, bottomSheetState, onItemClick = { filterName ->
                Toast.makeText(
                    context,
                    filterName,
                    Toast.LENGTH_SHORT
                ).show()
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
                        text = stringResource(id = R.string.feed),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = false,
                navActions = {}
            )
            FilterBar(postsTags, showFilterIcon = true, onShowFilters = { scope.launch { bottomSheetState.show() }}, onFilterClick = { string ->
                val a = string
            })
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
                    var lorem =
                        "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum "
                    items(20) { i ->
                        val post = danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post(
                            "2",
                            "2",
                            "Danyil",
                            "https://images.unsplash.com/photo-1630518615523-0d82e3985c06?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                            "https://images.unsplash.com/photo-1630370939214-4c4041b5efc4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                            lorem,
                            555,
                            555,
                            true,
                            false
                        )
//                    if (i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading) {
//                        viewModel.loadNextPosts()
//                    }
                        Post(
                            post = post,
                            imageLoader = ImageLoader.Builder(LocalContext.current)
                                .crossfade(true)
                                .componentRegistry {
                                    add(SvgDecoder(LocalContext.current))
                                }
                                .build(),
                            onUsernameClick = {
//                            onNavigate(Screen.ProfileScreen.route + "?userId=${post.userId}")
                            },
                            onPostClick = {
                                onNavigate(MainDestinations.POSTS_DETAIL + "/${post.id}")
                            },
                            onCommentClick = {
//                            onNavigate(Screen.PostDetailScreen.route + "/${post.id}?shouldShowKeyboard=true")
                            },
                            onLikeClick = {
//                            viewModel.onEvent(MainFeedEvent.LikedPost(post.id))
                            },
                            onShareClick = {
//                            context.sendSharePostIntent(post.id)
                            },
                            onDeleteClick = {
//                            viewModel.onEvent(MainFeedEvent.DeletePost(post))
                            }
                        )
//                    if (i < pagingState.items.size - 1) {
//                        Spacer(modifier = Modifier.height(SpaceLarge))
//                    }
                    }
                    item {
                        Spacer(modifier = Modifier.height(90.dp))
                    }
                }
//            if (pagingState.isLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
            }

        }
    }


}
package danyil.karabinovskyi.studenthub.features.posts.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.model.Event
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.utils.Paginate
import danyil.karabinovskyi.studenthub.core.data.Query
import danyil.karabinovskyi.studenthub.core.domain.model.FormFilter
import danyil.karabinovskyi.studenthub.core.domain.model.postsTags
import danyil.karabinovskyi.studenthub.core.presentation.PagingState
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.AuthenticateUseCase
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post
import danyil.karabinovskyi.studenthub.features.posts.domain.use_case.PostUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val authenticate: AuthenticateUseCase,
    private val postUseCases: PostUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), DefaultLifecycleObserver {

    private val query = Query()

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState

    private val _filters = mutableStateListOf<FormFilter>(FormFilter("fsdfsdf"),FormFilter("sdfsdfs"),FormFilter("sdfsdfsdf"))
    val formFilters: SnapshotStateList<FormFilter> = _filters
    init {
        viewModelScope.launch {
            delay(1000)
            formFilters.clear()
            formFilters.addAll(postsTags)
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        viewModelScope.launch {
        }
    }

    private val paginator = Paginate.Base(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            postUseCases.getPostsUseCase.execute(query = query.copy(query.take * page))
        },
        onSuccess = { posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
        }
    )
    init {
        loadNextPosts()
    }



    fun onEvent(event: PostsEvents) {
        when(event) {
            is PostsEvents.LikedPost -> {
                toggleLikeForParent(event.postId)
            }
            is PostsEvents.DeletePost -> {
                deletePost(event.post.id)
            }
        }
    }

    private fun deletePost(postId: Int) {
        viewModelScope.launch {
//            when(val result = postUseCases.deletePost(postId)) {
//                is Resource.Success -> {
//                    _pagingState.value = pagingState.value.copy(
//                        items = pagingState.value.items.filter {
//                            it.id != postId
//                        }
//                    )
//                    _eventFlow.emit(
//                        UiEvent.ShowSnackbar(
//                            UiText.StringResource(
//                            R.string.successfully_deleted_post
//                        ))
//                    )
//                }
//                is Resource.Error -> {
//                    _eventFlow.emit(
//                        UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError())
//                    )
//                }
//            }
        }
    }

    fun loadNextPosts() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun toggleLikeForParent(
        parentId: Int,
    ) {
        viewModelScope.launch {
//            postLiker.toggleLike(
//                posts = pagingState.value.items,
//                parentId = parentId,
//                onRequest = { isLiked ->
//                    postUseCases.toggleLikeForParent(
//                        parentId = parentId,
//                        parentType = ParentType.Post.type,
//                        isLiked = isLiked
//                    )
//                },
//                onStateUpdated = { posts ->
//                    _pagingState.value = pagingState.value.copy(
//                        items = posts
//                    )
//                }
//            )
        }
    }

}
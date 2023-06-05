package danyil.karabinovskyi.studenthub.features.posts.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.extensions.createFormValues
import danyil.karabinovskyi.studenthub.common.model.Event
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.utils.Paginate
import danyil.karabinovskyi.studenthub.core.data.AppData
import danyil.karabinovskyi.studenthub.core.data.Query
import danyil.karabinovskyi.studenthub.core.domain.model.FormFilter
import danyil.karabinovskyi.studenthub.core.domain.model.SocialFilters
import danyil.karabinovskyi.studenthub.core.presentation.PagingState
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.AuthenticateUseCase
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post
import danyil.karabinovskyi.studenthub.features.posts.domain.use_case.PostUseCases
import danyil.karabinovskyi.studenthub.features.posts.presentation.util.Like
import danyil.karabinovskyi.studenthub.features.posts.presentation.util.LikePost
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val authenticate: AuthenticateUseCase,
    private val postUseCases: PostUseCases,
    private val likePostExecutor: LikePost
) : ViewModel(), DefaultLifecycleObserver {

    private var wasCleared = false
    private var query = Query()

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState

    private val _filters =
        mutableStateListOf<FormFilter>(*AppData.postTags.createFormValues().toTypedArray())
    val formFilters: SnapshotStateList<FormFilter> = _filters

    override fun onResume(owner: LifecycleOwner) {
        query = Query()
        wasCleared = true
        loadNextPosts(true)
    }

    private val paginator = Paginate.Base(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(isLoading = isLoading)
        }, onRequest = { page ->
            postUseCases.getPostsUseCase.execute(
                query = query.copy(take = query.take * page, skip = query.take * (page - 1))
            )
        }, onSuccess = { posts ->
            _pagingState.value = pagingState.value.copy(
                items = if (wasCleared) posts else pagingState.value.items + posts,
                endReached = posts.isEmpty(), isLoading = false)
        }, onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
        }
    )

    fun loadNextPosts(
        needToClear: Boolean,
        filters: List<String> = query.filter.tags,
        socialTag: String = SocialFilters.ALL.type
    ) {
        query.filter = query.filter.copy(tags = filters)
        query.socialTag = socialTag
        viewModelScope.launch {
            if (needToClear) {
                paginator.clear()
                _pagingState.value = pagingState.value.copy(items = emptyList(), endReached = false, isLoading = false
                )
            }
            wasCleared = false
            paginator.loadNextItems()
        }
    }

    init {
        loadNextPosts(false)
    }

    fun onEvent(event: PostsEvents) {
        when (event) {
            is PostsEvents.LikedPost -> {
                toggleLikeForPost(event.post)
            }
        }
    }

    private fun toggleLikeForPost(
        post: Post
    ) {
        viewModelScope.launch {
            likePostExecutor.toggleLike(
                posts = pagingState.value.items,
                parentId = post.id,
                onRequest = { isLiked ->
                    postUseCases.likeToggleUseCase.execute(parentId = post.id, likeParent = Like.Post, isLiked = !post.isLiked) },
                onStateUpdated = { posts ->
                    _pagingState.value = pagingState.value.copy(
                        items = posts) }) }
    }
}
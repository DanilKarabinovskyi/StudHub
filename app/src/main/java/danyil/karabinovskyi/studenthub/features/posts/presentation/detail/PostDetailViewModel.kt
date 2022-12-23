package danyil.karabinovskyi.studenthub.features.posts.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.CommentError
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.components.comment.CommentState
import danyil.karabinovskyi.studenthub.core.model.states.StandardTextFieldState
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.AuthenticateUseCase
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.CreateCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.domain.use_case.PostUseCases
import danyil.karabinovskyi.studenthub.features.posts.presentation.util.Like
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val authenticate: AuthenticateUseCase,
    private val postUseCases: PostUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), DefaultLifecycleObserver {

    private var postId by Delegates.notNull<Int>()

    private val _state = mutableStateOf(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _commentTextFieldState =
        mutableStateOf(StandardTextFieldState(error = CommentError.FieldEmpty))
    val commentTextFieldState: State<StandardTextFieldState> = _commentTextFieldState

    private val _commentState = mutableStateOf(CommentState())
    val commentState: State<CommentState> = _commentState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var isUserLoggedIn = false

    init {
        savedStateHandle.get<Int>("postId")?.let { postId ->
            this.postId = postId
            _state.value = state.value.copy(postId = postId)
            loadPostDetails()
            loadCommentsForPost()
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        //todo check if post id wont be null here
        loadPostDetails()
    }

    fun onEvent(event: PostDetailEvent) {
        when (event) {
            is PostDetailEvent.LikePost -> {
                val isLiked = state.value.post?.isLiked == true
                toggleLikeForParent(
                    parentId = state.value.post?.id ?: return,
                    parentType = Like.Post,
                    isLiked = isLiked
                )
            }
            is PostDetailEvent.DeletePost -> {
                deletePost()
            }
            is PostDetailEvent.Comment -> {
                createComment(
                    postId = savedStateHandle.get<Int>("postId") ?: -1,
                    comment = commentTextFieldState.value.text
                )
            }
            is PostDetailEvent.LikeComment -> {
                val isLiked = state.value.comments.find {
                    it.id == event.commentId
                }?.isLiked == true
                toggleLikeForParent(
                    parentId = event.commentId,
                    parentType = Like.Comment,
                    isLiked = isLiked
                )
            }
            is PostDetailEvent.EnteredComment -> {
                _commentTextFieldState.value = commentTextFieldState.value.copy(
                    text = event.comment,
                    error = if (event.comment.isBlank()) CommentError.FieldEmpty else null
                )
            }
        }
    }

    private fun toggleLikeForParent(
        parentId: Int,
        parentType: Like,
        isLiked: Boolean
    ) {
        viewModelScope.launch {
            isUserLoggedIn = authenticate() is Resource.Success
            if (!isUserLoggedIn) {
                _eventFlow.emit(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_not_logged_in)))
                return@launch
            }
            val currentLikeCount = state.value.post?.likesCount ?: 0
            when (parentType) {
                Like.Post -> {
                    val post = state.value.post
                    _state.value = state.value.copy(
                        post = state.value.post?.copy(
                            isLiked = !isLiked,
                            likesCount = if (isLiked) {
                                post?.likesCount?.minus(1) ?: 0
                            } else post?.likesCount?.plus(1) ?: 0
                        )
                    )
                }
                Like.Comment -> {
                    _state.value = state.value.copy(
                        comments = state.value.comments.map { comment ->
                            if (comment.id == parentId) {
                                comment.copy(
                                    isLiked = !isLiked,
                                    likeCount = if (isLiked) {
                                        comment.likeCount - 1
                                    } else comment.likeCount + 1
                                )
                            } else comment
                        }
                    )
                }
            }
            val result = postUseCases.likeToggleUseCase.execute(
                likeParent = parentType,
                isLiked = !isLiked,
                parentId = parentId
            )
            when (result) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    when (parentType) {
                        Like.Post -> {
                            _state.value = state.value.copy(
                                post = state.value.post?.copy(
                                    isLiked = isLiked,
                                    likesCount = currentLikeCount
                                )
                            )
                        }
                        Like.Comment -> {
                            _state.value = state.value.copy(
                                comments = state.value.comments.map { comment ->
                                    if (comment.id == parentId) {
                                        comment.copy(
                                            isLiked = isLiked,
                                            likeCount = if (comment.isLiked) {
                                                comment.likeCount - 1
                                            } else comment.likeCount + 1
                                        )
                                    } else comment
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun createComment(postId: Int, comment: String) {
        viewModelScope.launch {
            isUserLoggedIn = authenticate() is Resource.Success
            if (!isUserLoggedIn) {
                _eventFlow.emit(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_not_logged_in)))
                return@launch
            }
            _commentState.value = commentState.value.copy(
                isLoading = true
            )
            val result = postUseCases.createCommentUseCase.execute(
                CreateCommentRequest(
                    postId = postId,
                    text = comment
                )
            )
            when (result) {
                is Resource.Success -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = UiText.StringResource(R.string.comment_posted)
                        )
                    )
                    _commentState.value = commentState.value.copy(
                        isLoading = false
                    )
                    _commentTextFieldState.value = commentTextFieldState.value.copy(
                        text = "",
                        error = CommentError.FieldEmpty
                    )
                    loadCommentsForPost()
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            uiText = result.uiText ?: UiText.unknownError()
                        )
                    )
                    _commentState.value = commentState.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadPostDetails() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingPost = true
            )
            val result = postUseCases.getPostUseCase.execute(postId)
            when (result) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        post = result.data,
                        isLoadingPost = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingPost = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    private fun deletePost() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingPost = true
            )
            when (val result = postUseCases.deletePostUseCase.execute(postId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        isLoadingPost = false
                    )
                    _eventFlow.emit(
                        UiEvent.NavigateUp
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingPost = false
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    private fun loadCommentsForPost() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingComments = true
            )
            when (val result = postUseCases.getCommentsForPostUseCase.execute(postId)) {
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        comments = result.data ?: emptyList(),
                        isLoadingComments = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoadingComments = false
                    )
                }
            }
        }
    }
}
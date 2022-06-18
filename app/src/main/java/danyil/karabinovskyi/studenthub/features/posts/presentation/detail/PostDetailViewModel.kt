package danyil.karabinovskyi.studenthub.features.posts.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import danyil.karabinovskyi.studenthub.features.posts.presentation.types.ParentType
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.CommentError
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.components.comment.CommentState
import danyil.karabinovskyi.studenthub.core.domain.model.Comment
import danyil.karabinovskyi.studenthub.core.model.states.StandardTextFieldState
import danyil.karabinovskyi.studenthub.features.auth.domain.usecase.AuthenticateUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val authenticate: AuthenticateUseCase,
//    private val postUseCases: PostUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(PostDetailState())
    val state: State<PostDetailState> = _state

    private val _commentTextFieldState = mutableStateOf(StandardTextFieldState(error = CommentError.FieldEmpty))
    val commentTextFieldState: State<StandardTextFieldState> = _commentTextFieldState

    private val _commentState = mutableStateOf(CommentState())
    val commentState: State<CommentState> = _commentState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var isUserLoggedIn = false

    init {
        savedStateHandle.get<String>("postId")?.let { postId ->
            loadPostDetails(postId)
            loadCommentsForPost(postId)
        }
    }

    fun onEvent(event: PostDetailEvent) {
        when(event) {
            is PostDetailEvent.LikePost -> {
                val isLiked = state.value.post?.isLiked == true
                toggleLikeForParent(
                    parentId = state.value.post?.id ?: return,
                    parentType = ParentType.Post.type,
                    isLiked = isLiked
                )
            }
            is PostDetailEvent.Comment -> {
                createComment(
                    postId = savedStateHandle.get<String>("postId") ?: "",
                    comment = commentTextFieldState.value.text
                )
            }
            is PostDetailEvent.LikeComment -> {
                val isLiked = state.value.comments.find {
                    it.id == event.commentId
                }?.isLiked == true
                toggleLikeForParent(
                    parentId = event.commentId,
                    parentType = ParentType.Comment.type,
                    isLiked = isLiked
                )
            }
            is PostDetailEvent.EnteredComment -> {
                _commentTextFieldState.value = commentTextFieldState.value.copy(
                    text = event.comment,
                    error = if(event.comment.isBlank()) CommentError.FieldEmpty else null
                )
            }
        }
    }

    private fun toggleLikeForParent(
        parentId: Int,
        parentType: Int,
        isLiked: Boolean
    ) {
        viewModelScope.launch {
            isUserLoggedIn = authenticate() is Resource.Success
            if(!isUserLoggedIn) {
                _eventFlow.emit(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_not_logged_in)))
                return@launch
            }
            val currentLikeCount = state.value.post?.likeCount ?: 0
            when(parentType) {
                ParentType.Post.type -> {
                    val post = state.value.post
                    _state.value = state.value.copy(
                        post = state.value.post?.copy(
                            isLiked = !isLiked,
                            likeCount = if (isLiked) {
                                post?.likeCount?.minus(1) ?: 0
                            } else post?.likeCount?.plus(1) ?: 0
                        )
                    )
                }
                ParentType.Comment.type -> {
                    _state.value = state.value.copy(
                        comments = state.value.comments.map { comment ->
                            if(comment.id == parentId) {
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
//            val result = postUseCases.toggleLikeForParent(
//                parentId = parentId,
//                parentType = parentType,
//                isLiked = isLiked
//            )
//            when(result) {
//                is Resource.Success -> Unit
//                is Resource.Error -> {
//                    when(parentType) {
//                        ParentType.Post.type -> {
//                            val post = state.value.post
//                            _state.value = state.value.copy(
//                                post = state.value.post?.copy(
//                                    isLiked = isLiked,
//                                    likeCount = currentLikeCount
//                                )
//                            )
//                        }
//                        ParentType.Comment.type -> {
//                            _state.value = state.value.copy(
//                                comments = state.value.comments.map { comment ->
//                                    if(comment.id == parentId) {
//                                        comment.copy(
//                                            isLiked = isLiked,
//                                            likeCount = if(comment.isLiked) {
//                                                comment.likeCount - 1
//                                            } else comment.likeCount + 1
//                                        )
//                                    } else comment
//                                }
//                            )
//                        }
//                    }
//                }
//            }
        }
    }

    private fun createComment(postId: String, comment: String) {
        viewModelScope.launch {
            isUserLoggedIn = authenticate() is Resource.Success
            if(!isUserLoggedIn) {
                _eventFlow.emit(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_not_logged_in)))
                return@launch
            }

            _commentState.value = commentState.value.copy(
                isLoading = true
            )
//            val result = postUseCases.createComment(
//                postId = postId,
//                comment = comment
//            )
//            when(result) {
//                is Resource.Success -> {
//                    _eventFlow.emit(UiEvent.ShowSnackbar(
//                        uiText = UiText.StringResource(R.string.comment_posted)
//                    ))
//                    _commentState.value = commentState.value.copy(
//                        isLoading = false
//                    )
//                    _commentTextFieldState.value = commentTextFieldState.value.copy(
//                        text = "",
//                        error = CommentError.FieldEmpty
//                    )
//                    loadCommentsForPost(postId)
//                }
//                is Resource.Error -> {
//                    _eventFlow.emit(UiEvent.ShowSnackbar(
//                        uiText = result.uiText ?: UiText.unknownError()
//                    ))
//                    _commentState.value = commentState.value.copy(
//                        isLoading = false
//                    )
//                }
//            }
        }
    }

    private fun loadPostDetails(postId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingPost = true
            )
            var lorem =
                "Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum "

            _state.value = state.value.copy(
                post = danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post(
                    2,
                    "2",
                    "Danyil",
                    "https://images.unsplash.com/photo-1630518615523-0d82e3985c06?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                    "https://images.unsplash.com/photo-1630370939214-4c4041b5efc4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                    lorem,
                    555,
                    555,
                    true,
                    false
                ),
                isLoadingPost = false
            )
//            val result = postUseCases.getPostDetails(postId)
//            when(result) {
//                is Resource.Success -> {
//                    _state.value = state.value.copy(
//                        post = result.data,
//                        isLoadingPost = false
//                    )
//                }
//                is Resource.Error -> {
//                    _state.value = state.value.copy(
//                        isLoadingPost = false
//                    )
//                    _eventFlow.emit(
//                        UiEvent.ShowSnackbar(
//                            result.uiText ?: UiText.unknownError()
//                        )
//                    )
//                }
//            }
        }
    }

    private fun loadCommentsForPost(postId: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoadingComments = true
            )
            val comments = mutableListOf<Comment>()
            for (i in 0..20){
                comments.add(Comment(id = 0,
                    username = "Danyil",
                    profilePictureUrl = "https://images.unsplash.com/photo-1630370939214-4c4041b5efc4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                    formattedTime = "08.08.2002",
                    comment ="Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum Lorem Ipsum ",
                    isLiked = true,
                    likeCount = 555))
            }
            _state.value = state.value.copy(
                comments = comments,
                isLoadingComments = false
            )
//            val result = postUseCases.getCommentsForPost(postId)
//            when(result) {
//                is Resource.Success -> {
//                    _state.value = state.value.copy(
//                        comments = result.data ?: emptyList(),
//                        isLoadingComments = false
//                    )
//                }
//                is Resource.Error -> {
//                    _state.value = state.value.copy(
//                        isLoadingComments = false
//                    )
//                }
//            }
        }
    }
}
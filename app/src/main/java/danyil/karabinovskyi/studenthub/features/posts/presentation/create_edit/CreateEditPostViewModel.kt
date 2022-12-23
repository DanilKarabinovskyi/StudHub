package danyil.karabinovskyi.studenthub.features.posts.presentation.create_edit

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.extensions.createFormValues
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.core.data.AppData
import danyil.karabinovskyi.studenthub.core.domain.model.FormFilter
import danyil.karabinovskyi.studenthub.core.model.states.StandardTextFieldState
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.CreatePostRequest
import danyil.karabinovskyi.studenthub.features.posts.domain.use_case.PostUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEditPostViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var postId: Int? = null

    private val _titleState = mutableStateOf(StandardTextFieldState())
    val titleState: State<StandardTextFieldState> = _titleState

    private val _descriptionState = mutableStateOf(StandardTextFieldState())
    val descriptionState: State<StandardTextFieldState> = _descriptionState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _chosenImageUri = mutableStateOf<Uri?>(null)
    val chosenImageUri: State<Uri?> = _chosenImageUri

    private val _oldImageUrl = mutableStateOf<String?>(null)
    val oldImageUrl: State<String?> = _oldImageUrl

    private val _chosenFilters = mutableStateOf<List<String>>(mutableListOf<String>())
    val chosenFilters: State<List<String>> = _chosenFilters

    private val _filters = mutableStateListOf<FormFilter>(
        *AppData.postTags.createFormValues().toTypedArray()
    )
    val formFilters: SnapshotStateList<FormFilter> = _filters

    init {
        savedStateHandle.get<Int>("postId")?.let { postId ->
            if (postId > 0) {
                this.postId = postId
                loadPostDetails(postId)
            }
        }

    }

    fun onEvent(eventEdit: CreateEditPostEvent) {
        when (eventEdit) {
            is CreateEditPostEvent.EnterTitle -> {
                _titleState.value = titleState.value.copy(
                    text = eventEdit.value
                )
            }
            is CreateEditPostEvent.EnterDescription -> {
                _descriptionState.value = descriptionState.value.copy(
                    text = eventEdit.value
                )
            }
            is CreateEditPostEvent.PickImage -> {
                _chosenImageUri.value = eventEdit.uri
            }
            is CreateEditPostEvent.CropImage -> {
                _chosenImageUri.value = eventEdit.uri
            }
            is CreateEditPostEvent.PostImage -> {
                viewModelScope.launch {
                    _isLoading.value = true
                    val result = postUseCases.createPostUseCase.execute(
                        CreatePostRequest(
                            postId,
                            title = titleState.value.text,
                            description = descriptionState.value.text,
                            tags = chosenFilters.value.toList(),
                            chosenImageUri.value,
                        ),
                    )
                    when (result) {
                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    uiText = UiText.StringResource(R.string.post_created)
                                )
                            )
                            _eventFlow.emit(UiEvent.NavigateUp)
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    result.uiText ?: UiText.unknownError()
                                )
                            )
                        }
                    }
                    _isLoading.value = false
                }
            }
        }
    }

    private fun loadPostDetails(postId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = postUseCases.getPostUseCase.execute(postId)) {
                is Resource.Success -> {
                    _isLoading.value = false
                    result.data?.let { post ->
                        _descriptionState.value.text = post.description
                        _titleState.value.text = post.title
                        _oldImageUrl.value = post.imageUrl
                        _chosenFilters.value = post.tags
                        setFiltersSelected(post.tags)
                    }
                }
                is Resource.Error -> {
                    _isLoading.value = false
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            result.uiText ?: UiText.unknownError()
                        )
                    )
                }
            }
        }
    }

    private fun setFiltersSelected(filters: List<String>) {
        filters.forEach { selectedFilter ->
            _filters.forEach { eachFilter ->
                if (selectedFilter == eachFilter.name) {
                    eachFilter.enabled.value = true
                }
            }
        }
    }

    fun setChosenFilters(filters: List<String>) {
        _chosenFilters.value = filters
    }
}
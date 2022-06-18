package danyil.karabinovskyi.studenthub.features.posts.presentation.create_edit

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.core.model.states.StandardTextFieldState
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.CreatePostRequest
import danyil.karabinovskyi.studenthub.features.posts.domain.use_case.PostUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEditPostViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel() {

    private val _descriptionState = mutableStateOf(StandardTextFieldState())
    val descriptionState: State<StandardTextFieldState> = _descriptionState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _chosenImageUri = mutableStateOf<Uri?>(null)
    val chosenImageUri: State<Uri?> = _chosenImageUri

    fun onEvent(eventEdit: CreateEditPostEvent) {
        when (eventEdit) {
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
                            title = descriptionState.value.text,
                            description = descriptionState.value.text,
                            tags = listOf(descriptionState.value.text),
                            chosenImageUri.value
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
}
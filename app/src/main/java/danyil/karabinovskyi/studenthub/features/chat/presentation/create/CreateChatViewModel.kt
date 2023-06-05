package danyil.karabinovskyi.studenthub.features.chat.presentation.create

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.utils.Paginate
import danyil.karabinovskyi.studenthub.core.data.ChatQuery
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.core.model.states.StandardTextFieldState
import danyil.karabinovskyi.studenthub.core.presentation.PagingState
import danyil.karabinovskyi.studenthub.features.chat.data.model.CreateChatRequest
import danyil.karabinovskyi.studenthub.features.chat.domain.use_case.AddUserToChatUseCase
import danyil.karabinovskyi.studenthub.features.chat.domain.use_case.CreateChatUseCase
import danyil.karabinovskyi.studenthub.features.chat.domain.use_case.GetUsersUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateChatViewModel @Inject constructor(
    private val createChatUseCase: CreateChatUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val addUserToChatUseCase: AddUserToChatUseCase
) : ViewModel() {

    var loading = mutableStateOf(false)
    private var query = ChatQuery()

    private val _pagingState = mutableStateOf<PagingState<User>>(PagingState())
    val pagingState: State<PagingState<User>> = _pagingState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _channelNameState = mutableStateOf(StandardTextFieldState())
    val channelNameState: State<StandardTextFieldState> = _channelNameState

    private val _pictureUri = mutableStateOf<Uri?>(null)
    val pictureUri: State<Uri?> = _pictureUri

    private val _userIds = mutableStateOf<Set<Int>>(setOf())
    val userIds: State<Set<Int>> = _userIds

    private val paginator = Paginate.Base(
        onLoadUpdated = { isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = { page ->
            query = query.copy(
                take = query.take * page,
                skip = query.take * (page - 1)
            )
            getUsersUseCase.execute(
                query
            )
        },
        onSuccess = { data ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + data,
                endReached = data.isEmpty(),
                isLoading = false
            )
        },
        onError = { uiText ->
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
        }
    )

    init {
        getUsers()
    }

    fun onEvent(event: CreateChatEvent) {
        when (event) {
            is CreateChatEvent.CreateChat -> {
                loading.value = true
                viewModelScope.launch {
                    kotlin.runCatching {
                        val result = createChatUseCase.execute(
                            createChatRequest = CreateChatRequest(
                                title = _channelNameState.value.text,
                                fileUri = _pictureUri.value,
                                userIds = _userIds.value.toList()
                            )
                        )
                        when (result) {
                            is Resource.Success -> {
                                _eventFlow.emit(UiEvent.NavigateUp)
                                loading.value = false
                            }
                            is Resource.Error -> {
                                loading.value = false
                            }
                        }
                    }.onFailure {

                    }


                }

            }
            is CreateChatEvent.OnEnterChatName -> {
                _channelNameState.value = _channelNameState.value.copy(text = event.value)
            }
            is CreateChatEvent.AddChatImage -> {
                _pictureUri.value = event.uri
            }
        }
    }

    fun addOrDeleteId(id: Int) {
        _userIds.value = if (_userIds.value.contains(id))
            _userIds.value.minus(id)
        else
            _userIds.value.plus(id)

    }

    fun loadNextUsers(){

    }

    fun getUsers() {

        viewModelScope.launch {
            kotlin.runCatching {
                paginator.loadNextItems()

            }.onFailure {

            }
        }
    }
}

sealed class CreateChatEvent {
    data class OnEnterChatName(val value: String) : CreateChatEvent()
    data class AddChatImage(val uri: Uri?) : CreateChatEvent()
    object CreateChat : CreateChatEvent()
}

package danyil.karabinovskyi.studenthub.features.chat.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.utils.Constants
import danyil.karabinovskyi.studenthub.common.utils.SharedPrefs
import danyil.karabinovskyi.studenthub.core.model.states.StandardTextFieldState
import danyil.karabinovskyi.studenthub.core.sockets.Event
import danyil.karabinovskyi.studenthub.core.sockets.OkHttpWebSocket
import danyil.karabinovskyi.studenthub.core.sockets.WebSocketEventObserver
import danyil.karabinovskyi.studenthub.features.chat.data.model.ChatDefaultModel
import danyil.karabinovskyi.studenthub.features.chat.data.model.ChatModel
import danyil.karabinovskyi.studenthub.features.chat.data.model.UpdatedChat
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs
) : ViewModel(), DefaultLifecycleObserver {

    private var ws: WebSocket? = null
    private val client by lazy { OkHttpClient() }
    private val eventsObserver = WebSocketEventObserver()

    private val _chats = mutableStateOf(ChatsScreenState())
    val chats: State<ChatsScreenState> = _chats

    private val _filteredChats = mutableStateOf(ChatsScreenState())
    val filteredChats: State<ChatsScreenState> = _filteredChats

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _searchState = mutableStateOf(StandardTextFieldState())
    val searchState: State<StandardTextFieldState> = _searchState


    private fun open() {
        val request: Request = Request.Builder()
            .url(Constants.CHATS_URL)
            .addHeader("Authorization","Bearer " + sharedPrefs.getToken())
            .build()
        client.newWebSocket(request = request, eventsObserver)
        OkHttpWebSocket(eventsObserver).open()
            .onEach { handleEvent(it) }.launchIn(viewModelScope)
    }

    private fun handleEvent(event: Event.WebSocket) {
        if (event is Event.WebSocket.OnMessageReceived) {
            val message = Gson().fromJson(event.message, ChatDefaultModel::class.java)
            when(message.type){
                INITIAL_CHATS -> {
                    val initialMessage = Gson().fromJson(event.message, ChatModel::class.java)
                    val chatsList = initialMessage.data.map { it.mapToChannel() }
                    _chats.value = _chats.value.copy(chats = chatsList)
                    _filteredChats.value = _chats.value.copy(chats = chatsList)
                }
                CHAT_UPADATE -> {
                    val initialMessage = Gson().fromJson(event.message, UpdatedChat::class.java)
                    val updatedChat = initialMessage.data.mapToChannel()
                }
                CHAT_ADDED -> {
                }
            }
        }
    }

    fun onEvent(eventEdit: ChatsViewModelEvent) {
        when (eventEdit) {
            is ChatsViewModelEvent.EnterSearchField -> {
                _searchState.value = searchState.value.copy(
                    text = eventEdit.value
                )
                _filteredChats.value = _chats.value.copy(
                    chats = _chats.value.chats.filter {
                        it.name.toLowerCase(Locale.current).contains(searchState.value.text.toLowerCase(Locale.current)) })
            }
        }
    }

    fun clearSearch(){
        _searchState.value = _searchState.value.copy(text = "")
        _filteredChats.value = _chats.value.copy(chats = _chats.value.chats)
    }

    private fun stop() {
        ws?.close(1000, "On Stop")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        open()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        client.dispatcher.executorService.shutdown()
    }

    companion object {
        val INITIAL_CHATS = "INITIAL_CHATS"
        val CHAT_UPADATE = "CHAT_UPADATE"
        val CHAT_ADDED = "CHAT_ADDED"

        sealed class ChatsViewModelEvent {
            data class EnterSearchField(val value: String): ChatsViewModelEvent()
        }
    }
}
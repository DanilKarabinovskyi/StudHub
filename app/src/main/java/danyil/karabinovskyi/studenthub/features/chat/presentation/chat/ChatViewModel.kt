package danyil.karabinovskyi.studenthub.features.chat.presentation.chat

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.core.net.toFile
import androidx.lifecycle.*
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import danyil.karabinovskyi.studenthub.common.utils.Constants.CHAT_URL
import danyil.karabinovskyi.studenthub.common.utils.SharedPrefs
import danyil.karabinovskyi.studenthub.core.data.entity.Message
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.core.sockets.Event
import danyil.karabinovskyi.studenthub.core.sockets.OkHttpWebSocket
import danyil.karabinovskyi.studenthub.core.sockets.WebSocketEventObserver
import danyil.karabinovskyi.studenthub.features.chat.data.model.AddMessageModel
import danyil.karabinovskyi.studenthub.features.chat.data.model.MessagesDefaultModel
import danyil.karabinovskyi.studenthub.features.chat.data.model.MessagesModel
import danyil.karabinovskyi.studenthub.features.profile.domain.usecase.GetUserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sharedPrefs: SharedPrefs,
    private val savedStateHandle: SavedStateHandle,
    private val getUserUseCase: GetUserUseCase
) : ViewModel(), DefaultLifecycleObserver {

    private var ws: WebSocket? = null
    private val client by lazy { OkHttpClient() }
    private val eventsObserver = WebSocketEventObserver()

    private val _user = mutableStateOf(User())
    val user: State<User> = _user

    private val _messages = mutableStateListOf<Message>()
    val messages: SnapshotStateList<Message> = _messages

    private val _eventFlow = MutableSharedFlow<ChatScreenUiState>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _chatName = mutableStateOf(savedStateHandle.get<String>("chatName").toString())
    val chatName: State<String> = _chatName

    private val _messageImageUri = mutableStateOf<Uri?>(null)
    val messageImageUri: State<Uri?> = _messageImageUri

    val lastReadPosition = mutableStateOf(0)

    init {
        viewModelScope.launch {
            val user = getUserUseCase.execute()
            _user.value = user
        }
    }

    private fun open() {
        val request: Request = Request.Builder()
            .url("${CHAT_URL}${savedStateHandle.get<String>("chatIdToOpen").toString()}")
            .addHeader("Authorization", "Bearer " + sharedPrefs.getToken())
            .build()
        ws = client.newWebSocket(request = request, eventsObserver)
        OkHttpWebSocket(eventsObserver).open()
            .onEach { handleEvent(it) }.launchIn(viewModelScope)
    }

    private fun handleEvent(event: Event.WebSocket) {
        if (event is Event.WebSocket.OnMessageReceived) {
            val message = Gson().fromJson(event.message, MessagesDefaultModel::class.java)
            when (message.type) {
                INITIAL_MESSAGES -> {
                    val initialMessage = Gson().fromJson(event.message, MessagesModel::class.java)
                    val messages = initialMessage.data.map { it.toMessage() }.toMutableList()
                    if (_messages.isEmpty() || messages.first().id != _messages.first().id){
                        _messages.addAll(messages)
                    }
                    viewModelScope.launch {
                        findLastReadMessage()
                    }
                }
                NEW_MESSAGE -> {
                    val initialMessage = Gson().fromJson(event.message, AddMessageModel::class.java)
                    if (_messages.isEmpty() || initialMessage.data.message.id != _messages.first().id){
                        _messages.add(0, initialMessage.data.message.toMessage())

                    }
                }
            }
        }
    }

    fun sendMessage(text: String) {
        val message = MessageRequest(data = DataObject(text, if(_messageImageUri.value!= null ) Base64.getEncoder().encodeToString(_messageImageUri.value?.toFile()?.readBytes()).replace("\u003d","=") else ""))
        ws?.send(Gson().toJson(message, MessageRequest::class.java))
    }

    fun sendReadIds(from: Int, to: Int) {
        val message = ReadMessageRequest(data = ReadIds(startMessageId = from, endMessageId = to))
        ws?.send(Gson().toJson(message, ReadMessageRequest::class.java))
    }

    private fun findLastReadMessage() {
        val message = _messages.find { it.isMessageRead == true }
        val prevMessage = _messages.getOrNull(_messages.indexOf(message) - 1)
        lastReadPosition.value = if(_messages.indexOf(prevMessage) == -1) 0 else _messages.indexOf(prevMessage)
        if(_messages.isNotEmpty()){
            sendReadIds((prevMessage?:_messages.last()).id,_messages.first().id)
        }
    }

    fun onEvent(event: ChatViewModelEvent) {
        when(event){
            is ChatViewModelEvent.PickImage ->{
                viewModelScope.launch {
                    // todo investigate why image doesent recompose without delay
                    _messageImageUri.value = null
                    delay(100)
                    _messageImageUri.value = event.uri
                }

            }
        }

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
        val INITIAL_MESSAGES = "INITIAL_MESSAGES"
        val NEW_MESSAGE = "NEW_MESSAGE"
        val CHAT_ADDED = "CHAT_ADDED"

        sealed class ChatViewModelEvent {
            data class EnterSearchField(val value: String) : ChatViewModelEvent()
            data class PickImage(val uri: Uri?): ChatViewModelEvent()
        }
    }

    inner class MessageRequest(
        val message: String = "sendMessage",
        val data: DataObject = DataObject()
    )

    inner class ReadMessageRequest(
        val message: String = "setReadMessages",
        val data: ReadIds = ReadIds()
    )

    inner class ReadIds(
        val startMessageId: Int = 0,
        val endMessageId: Int = 0
    )

    inner class DataObject(
        val message: String = "",
        val image: String = ""
    )

}


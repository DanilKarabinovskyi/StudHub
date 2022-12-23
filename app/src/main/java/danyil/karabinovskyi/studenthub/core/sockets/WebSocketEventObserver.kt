package danyil.karabinovskyi.studenthub.core.sockets

import kotlinx.coroutines.flow.MutableSharedFlow
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

private const val EVENTS_BUFFER_SIZE = 100

class WebSocketEventObserver : WebSocketListener() {
    private val _eventsFlow = MutableSharedFlow<Event.WebSocket>(extraBufferCapacity = EVENTS_BUFFER_SIZE)

    val eventsFlow = _eventsFlow

    fun terminate() = _eventsFlow.tryEmit(Event.WebSocket.Terminate)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        _eventsFlow.tryEmit(Event.WebSocket.OnConnectionOpened(webSocket))
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        // no-op
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        _eventsFlow.tryEmit(Event.WebSocket.OnMessageReceived(text))
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        _eventsFlow.tryEmit(Event.WebSocket.OnConnectionClosing(ShutdownReason(code, reason)))
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        _eventsFlow.tryEmit(Event.WebSocket.OnConnectionClosed(ShutdownReason(code, reason)))
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        _eventsFlow.tryEmit(Event.WebSocket.OnConnectionFailed(t))
    }
}

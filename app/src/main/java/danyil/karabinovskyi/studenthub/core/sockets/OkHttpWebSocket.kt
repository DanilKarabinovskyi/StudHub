package danyil.karabinovskyi.studenthub.core.sockets

import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import okhttp3.WebSocket
import okio.ByteString

internal class OkHttpWebSocket constructor(
    private val eventsObserver: WebSocketEventObserver,
) {
    private var webSocket: WebSocket? = null

    fun open(): Flow<Event.WebSocket> {
        return eventsObserver.eventsFlow.onEach(::handleWebSocketEvent)
    }

    fun send(message: String): Boolean = webSocket?.send(message) ?: false

    fun send(bytes: ByteString) = webSocket?.send(bytes) ?: false

    fun close(shutdownReason: ShutdownReason): Boolean {
        val (code, reasonText) = shutdownReason
        return webSocket?.close(code, reasonText) ?: false
    }

    private fun handleConnectionShutdown() {
        webSocket = null
        eventsObserver.terminate()
    }

    fun cancel() = webSocket?.cancel() ?: Unit

    private fun handleWebSocketEvent(event: Event.WebSocket) {
        when (event) {
            is Event.WebSocket.OnConnectionOpened<*> -> webSocket = event.webSocket as WebSocket
            is Event.WebSocket.OnConnectionClosing -> close(ShutdownReason.GRACEFUL)
            is Event.WebSocket.OnConnectionClosed, is Event.WebSocket.OnConnectionFailed -> handleConnectionShutdown()
            else -> Unit
        }
    }
}
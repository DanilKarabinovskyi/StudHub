package danyil.karabinovskyi.studenthub.core.sockets

sealed class Event {

    sealed class Lifecycle : Event() {

        object Started : Lifecycle() {
            override fun toString(): String {
                return "Event.Lifecycle.Started"
            }
        }

        sealed class Stopped(val disconnectCause: DisconnectCause) : Lifecycle() {
            data class WithReason(
                val cause: DisconnectCause,
                val shutdownReason: ShutdownReason = ShutdownReason.GRACEFUL,
            ) : Stopped(disconnectCause = cause)

            data class AndAborted(val cause: DisconnectCause) : Stopped(disconnectCause = cause)
        }

        object Terminate : Lifecycle() {
            override fun toString(): String {
                return "Event.Lifecycle.Terminate"
            }
        }
    }

    sealed class WebSocket : Event() {
        object Terminate : WebSocket() {
            override fun toString(): String {
                return "Event.WebSocket.Terminate"
            }
        }

        data class OnConnectionOpened<out WEB_SOCKET : Any>(val webSocket: WEB_SOCKET) : WebSocket()

        data class OnMessageReceived(val message: String) : WebSocket()

        data class OnConnectionClosing(val shutdownReason: ShutdownReason) : WebSocket()

        data class OnConnectionClosed(val shutdownReason: ShutdownReason) : WebSocket()

        data class OnConnectionFailed(val throwable: Throwable) : WebSocket()
    }
}

internal fun Event.Lifecycle.isStopped(): Boolean = this is Event.Lifecycle.Stopped

internal fun Event.Lifecycle.isStoppedAndAborted(): Boolean = this is Event.Lifecycle.Stopped.AndAborted

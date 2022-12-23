package danyil.karabinovskyi.studenthub.common

import androidx.annotation.StringRes
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SnackbarMessage(val id: Long, @StringRes val messageId: Int)

object SnackbarManager {

    private val _messages: MutableStateFlow<List<SnackbarMessage>> = MutableStateFlow(emptyList())
    val messages: StateFlow<List<SnackbarMessage>> get() = _messages.asStateFlow()

    fun showMessage(@StringRes messageTextId: Int) {
        _messages.update { currentMessages ->
            currentMessages + SnackbarMessage(
                id = UUID.randomUUID().mostSignificantBits,
                messageId = messageTextId
            )
        }
    }

    fun setMessageShown(messageId: Long) {
        _messages.update { currentMessages ->
            currentMessages.filterNot { it.id == messageId }
        }
    }
}

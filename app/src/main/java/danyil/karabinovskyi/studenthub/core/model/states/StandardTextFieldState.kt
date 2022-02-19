package danyil.karabinovskyi.studenthub.core.model.states

import danyil.karabinovskyi.studenthub.common.model.Error

data class StandardTextFieldState(
    val text: String = "",
    val error: Error? = null
)

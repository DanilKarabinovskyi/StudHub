package danyil.karabinovskyi.studenthub.features.auth.domain.screen_result

import danyil.karabinovskyi.studenthub.common.model.AuthError
import danyil.karabinovskyi.studenthub.common.model.SimpleResource

data class LoginResult(
    val codeError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: SimpleResource? = null
)

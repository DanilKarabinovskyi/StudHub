package danyil.karabinovskyi.studenthub.features.auth.presentation.login

import androidx.annotation.StringRes

data class LoginState(
    val code: String = "",
    val password: String = "",
    val successLogin: Boolean = false,
    val displayProgressBar: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

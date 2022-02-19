package danyil.karabinovskyi.studenthub.features.auth.presentation.login

import androidx.annotation.StringRes

data class LoginState(
    val successLogin: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

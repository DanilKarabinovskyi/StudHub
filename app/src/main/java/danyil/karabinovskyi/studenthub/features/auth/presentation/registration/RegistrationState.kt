package danyil.karabinovskyi.studenthub.features.auth.presentation.registration

import androidx.annotation.StringRes

data class RegistrationState(
    val successRegister: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

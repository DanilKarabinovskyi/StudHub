package danyil.karabinovskyi.studenthub.features.auth.presentation.login

sealed class LoginEvent {
    data class EnteredCode(val code: String): LoginEvent()
    data class EnteredPassword(val password: String): LoginEvent()
    object Login: LoginEvent()
}

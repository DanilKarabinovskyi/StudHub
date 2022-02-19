package danyil.karabinovskyi.studenthub.features.auth.presentation.registration

import java.io.File

sealed class RegistrationEvent {
    data class EnteredGroup(val value: String): RegistrationEvent()
    data class EnteredEmail(val value: String): RegistrationEvent()
    data class SavePhoto(val value: File): RegistrationEvent()
    data class EnteredPassword(val value: String): RegistrationEvent()
    data class EnteredConfirmedPassword(val value: String): RegistrationEvent()
    object Register : RegistrationEvent()
}

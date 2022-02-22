package danyil.karabinovskyi.studenthub.features.auth.presentation.splash

import danyil.karabinovskyi.studenthub.common.model.Event
import danyil.karabinovskyi.studenthub.common.model.UiEvent
import danyil.karabinovskyi.studenthub.common.model.UiText

sealed class SplashEvent: Event() {
    object ToLogin : SplashEvent()
    object ToHome: SplashEvent()
}

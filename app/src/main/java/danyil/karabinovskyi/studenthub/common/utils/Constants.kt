package danyil.karabinovskyi.studenthub.common.utils
object Constants {
    const val BASE_URL = "http://10.0.2.2:2000"
//    const val BASE_URL = "https://backend-students-hub.herokuapp.com"
    const val SOCKET_BASE_URL = "ws://10.0.2.2:2000"
    const val CHATS_URL = "$SOCKET_BASE_URL/chat"
    const val CHAT_URL = "$SOCKET_BASE_URL/chat/chats/"

    const val SPLASH_SCREEN_DURATION = 2000L

    const val MAX_POST_DESCRIPTION_LINES = 3

    const val MIN_USERNAME_LENGTH = 3
    const val MIN_PASSWORD_LENGTH = 3

    const val DEFAULT_PAGE_SIZE = 50
    const val DEFAULT_SKIP_SIZE = 0

    const val ASC = "asc"
    const val DESC = "desc"

    const val KEY_JWT_TOKEN = "jwt_token"
    const val KEY_USER_ID = "userId"

    const val SHARED_PREF_NAME = "shared_pref"

    const val RECONNECT_INTERVAL = 5000L
}


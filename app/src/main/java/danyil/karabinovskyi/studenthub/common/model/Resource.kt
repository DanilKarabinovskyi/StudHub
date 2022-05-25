package danyil.karabinovskyi.studenthub.common.model

import androidx.annotation.StringRes
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.PostsResponse

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(val data: T? = null, val uiText: UiText? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(uiText: UiText, data: T? = null): Resource<T>(data, uiText)
}

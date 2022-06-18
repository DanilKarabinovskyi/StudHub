package danyil.karabinovskyi.studenthub.features.posts.presentation.util

import danyil.karabinovskyi.studenthub.common.model.Error

sealed class PostDescriptionError : Error() {
    object FieldEmpty: PostDescriptionError()
}

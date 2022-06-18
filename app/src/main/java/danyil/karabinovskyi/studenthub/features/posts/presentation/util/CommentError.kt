package danyil.karabinovskyi.studenthub.features.posts.presentation.util

sealed class CommentError : Error() {
    object FieldEmpty: CommentError()
}

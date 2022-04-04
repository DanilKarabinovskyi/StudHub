package danyil.karabinovskyi.studenthub.common.model


sealed class CommentError : Error() {
    object FieldEmpty: CommentError()
}

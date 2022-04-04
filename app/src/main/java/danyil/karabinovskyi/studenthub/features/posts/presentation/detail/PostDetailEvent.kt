package danyil.karabinovskyi.studenthub.features.posts.presentation.detail

sealed class PostDetailEvent {
    object LikePost: PostDetailEvent()
    object Comment: PostDetailEvent()
    data class LikeComment(val commentId: String): PostDetailEvent()
    data class EnteredComment(val comment: String): PostDetailEvent()
}

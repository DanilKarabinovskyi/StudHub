package danyil.karabinovskyi.studenthub.features.posts.presentation.detail

sealed class PostDetailEvent {
    object LikePost: PostDetailEvent()
    object DeletePost: PostDetailEvent()
    object Comment: PostDetailEvent()
    data class LikeComment(val commentId: Int): PostDetailEvent()
    data class EnteredComment(val comment: String): PostDetailEvent()
}

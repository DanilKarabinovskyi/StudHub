package danyil.karabinovskyi.studenthub.features.posts.presentation

import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post

sealed class PostsEvents {
    data class LikedPost(val postId: Int): PostsEvents()
    data class DeletePost(val post: Post): PostsEvents()
}

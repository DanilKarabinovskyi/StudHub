package danyil.karabinovskyi.studenthub.features.posts.presentation

import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post

sealed class PostsEvents {
    data class LikedPost(val post: Post): PostsEvents()
    data class DeletePost(val post: Post): PostsEvents()
}

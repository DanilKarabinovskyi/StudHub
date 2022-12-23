package danyil.karabinovskyi.studenthub.features.posts.presentation.util

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post

interface LikePost {

    suspend fun toggleLike(
        posts: List<Post>,
        parentId: Int,
        onRequest: suspend (isLiked: Boolean) -> Resource<Boolean>,
        onStateUpdated: (List<Post>) -> Unit
    )

    class Base() : LikePost {
        override suspend fun toggleLike(
            posts: List<Post>,
            parentId: Int,
            onRequest: suspend (isLiked: Boolean) -> Resource<Boolean>,
            onStateUpdated: (List<Post>) -> Unit
        ) {
            val post = posts.find { it.id == parentId }
            val currentlyLiked = post?.isLiked == true
            val currentLikeCount = post?.likesCount ?: 0
            val newPosts = posts.map { post ->
                if (post.id == parentId) {
                    post.copy(
                        isLiked = !post.isLiked,
                        likesCount = if (currentlyLiked) {
                            post.likesCount - 1
                        } else post.likesCount + 1
                    )
                } else post
            }
            onStateUpdated(newPosts)
            when (onRequest(currentlyLiked)) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    val oldPosts = posts.map { post ->
                        if (post.id == parentId) {
                            post.copy(
                                isLiked = currentlyLiked,
                                likesCount = currentLikeCount
                            )
                        } else post
                    }
                    onStateUpdated(oldPosts)
                }
            }
        }
    }
}
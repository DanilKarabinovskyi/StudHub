package danyil.karabinovskyi.studenthub.features.posts.domain.use_case

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikeCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikePostRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikeUpdateRequest
import danyil.karabinovskyi.studenthub.features.posts.domain.PostsRepository
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post
import danyil.karabinovskyi.studenthub.features.posts.presentation.util.Like

class LikeToggleUseCase(
    private val repository: PostsRepository
) {
    suspend fun execute(
        likeParent: Like,
        isLiked: Boolean,
        parentId: Int
    ): Resource<Boolean> {
        return when (likeParent) {
            Like.Post -> {
                repository.toggleLikePost(LikePostRequest(isLiked,parentId))
            }
            Like.Comment -> {
                repository.toggleLikeComment(LikeCommentRequest(isLiked,parentId))
            }
        }
    }
}
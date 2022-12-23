package danyil.karabinovskyi.studenthub.features.posts.domain

import android.net.Uri
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.core.data.Query
import danyil.karabinovskyi.studenthub.core.domain.model.Comment
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.CreateCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikeCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikePostRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikeUpdateRequest
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.CreatePostRequest
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post
import danyil.karabinovskyi.studenthub.features.posts.presentation.detail.PostDetailEvent

interface PostsRepository {
    suspend fun getPosts(query: Query): Resource<List<Post>>

    suspend fun upsertPost(request: CreatePostRequest):
            Resource<Unit>

    suspend fun getPost(postId: Int):
            Resource<Post>

    suspend fun deletePost(postId: Int):
            Resource<Boolean>

    suspend fun getCommentsForPost(postId: Int):
            Resource<List<Comment>>

    suspend fun createComment(createCommentRequest: CreateCommentRequest):
            SimpleResource

    suspend fun toggleLikePost(request: LikePostRequest):
            Resource<Boolean>

    suspend fun toggleLikeComment(request: LikeCommentRequest):
            Resource<Boolean>

}
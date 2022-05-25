package danyil.karabinovskyi.studenthub.features.posts.domain

import android.net.Uri
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.core.data.Query
import danyil.karabinovskyi.studenthub.core.domain.model.Comment
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.PostsResponse
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post

interface PostsRepository {
    suspend fun getPosts(query: Query): Resource<List<Post>>

    suspend fun createPost(description: String, imageUri: Uri):
            Resource<Post>

    suspend fun getPost(postId: Int):
            Resource<Post>

    suspend fun editPost(description: String, imageUri: Uri):
            Resource<Post>

    suspend fun deletePost(postId: Int):
            Resource<Boolean>

    suspend fun getCommentsForPost(postId: String):
            Resource<List<Comment>>

    suspend fun createComment(postId: String, comment: String):
            SimpleResource

    suspend fun likeParent(parentId: String, parentType: Int):
            SimpleResource

    suspend fun unlikeParent(parentId: String, parentType: Int):
            SimpleResource

    suspend fun getLikesForParent(parentId: String):
            Resource<Int>
}
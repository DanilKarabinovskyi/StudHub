package danyil.karabinovskyi.studenthub.features.posts.domain

import danyil.karabinovskyi.studenthub.common.model.BasicApiResponse
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.PostsResponse
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.CreatePostRequest

interface PostsRepository {
    suspend fun getPosts() :
            BasicApiResponse<List<PostsResponse>>

    suspend fun createPost(request : CreatePostRequest):
            BasicApiResponse<PostsResponse>

    suspend fun getPost(postId: Int) :
            BasicApiResponse<PostsResponse>

    suspend fun editPost(request : CreatePostRequest) :
            BasicApiResponse<PostsResponse>

    suspend fun deletePost(postId: Int) : Boolean
}
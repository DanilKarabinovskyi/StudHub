package danyil.karabinovskyi.studenthub.features.posts.data.remote.api

import danyil.karabinovskyi.studenthub.common.model.BasicApiResponse
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.PostsResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("take") take: Int,
        @Query("skip") skip: Int,
        @Query("order") order: Int,
        @Query("sort") sort: Int,
        @Query("tags") tags: Int,
        @Query("socialTag") socialTag: Int,
        ): BasicApiResponse<List<PostsResponse>>

    @Multipart
    @POST("posts")
    suspend fun createPost(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part
    ): BasicApiResponse<Unit>

    @GET("posts/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String,
    ): BasicApiResponse<PostsResponse>

    @PUT("posts/post")
    suspend fun editPost(
        @Query("postId") postId: String
    ): BasicApiResponse<PostsResponse>

    @DELETE("posts/post")
    suspend fun deletePost(
        @Query("postId") postId: String,
    ): Boolean
}
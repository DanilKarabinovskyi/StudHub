package danyil.karabinovskyi.studenthub.features.posts.data.remote.api

import danyil.karabinovskyi.studenthub.common.model.BasicApiResponse
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.CommentResponse
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.CreateCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.CreatePostRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikeUpdateRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.PostsResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("take") take: Int,
        @Query("skip") skip: Int,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("tags") tags: Int,
        @Query("socialTag") socialTag: List<String>,
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
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part
    ): BasicApiResponse<PostsResponse>

    @DELETE("posts/post")
    suspend fun deletePost(
        @Query("postId") postId: String,
    ): BasicApiResponse<Boolean>

    @GET("comment/get")
    suspend fun getCommentsForPost(
        @Query("postId") postId: String
    ): BasicApiResponse<List<CommentResponse>>

    @POST("comment/create")
    suspend fun createComment(
        @Body request: CreateCommentRequest
    ): BasicApiResponse<Unit>

    @POST("like")
    suspend fun likeParent(
        @Body request: LikeUpdateRequest
    ): BasicApiResponse<Unit>

    @DELETE("unlike")
    suspend fun unlikeParent(
        @Query("parentId") parentId: String,
        @Query("parentType") parentType: Int
    ): BasicApiResponse<Unit>

    @GET("like/parent")
    suspend fun getLikesForParent(
        @Query("parentId") parentId: String
    ): BasicApiResponse<List<Int>>

}
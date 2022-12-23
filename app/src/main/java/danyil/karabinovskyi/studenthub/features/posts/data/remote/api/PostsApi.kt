package danyil.karabinovskyi.studenthub.features.posts.data.remote.api

import danyil.karabinovskyi.studenthub.common.model.BasicApiResponse
import danyil.karabinovskyi.studenthub.core.data.Filter
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.CommentResponse
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.PostResponse
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.CreateCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikeCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikePostRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikeUpdateRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.http.*

interface PostsApi {
    @GET("posts/")
    suspend fun getPosts(
        @Query("take") take: Int,
        @Query("skip") skip: Int,
        @Query("order") order: String,     // ASC, DESC
        @Query("sort") sort: String,       // Any field
        @Query("filter") filter: JSONObject, // "sport" etc
        @Query("socialTag") socialTag: String, // All, My, My University
    ): BasicApiResponse<List<PostResponse>>

    @Multipart
    @POST("posts/upsert")
    suspend fun upsertPost(
        @Part("title") title: RequestBody,
        @Part("id") id: RequestBody,
        @Part("body") description: RequestBody,
        @Part("tags") tags: RequestBody,
        @Part file: MultipartBody.Part?
    ): BasicApiResponse<PostResponse>

    @GET("posts/{postId}")
    suspend fun getPost(
        @Path("postId") postId: Int,
    ): BasicApiResponse<PostResponse>

    @DELETE("posts/post")
    suspend fun deletePost(
        @Query("id") postId: Int,
    ): BasicApiResponse<Unit>

    @GET("posts/post/comments/{postId}")
    suspend fun getCommentsForPost(
        @Path("postId") postId: Int
    ): BasicApiResponse<List<CommentResponse>>

    @POST("posts/post/comment/create")
    suspend fun createComment(
        @Body request: CreateCommentRequest
    ): BasicApiResponse<Unit>

    @POST("posts/post/toggle-like")
    suspend fun toggleLikePost(
        @Body request: LikePostRequest
    ): BasicApiResponse<Boolean>

    @POST("posts/post/comment/toggle-like")
    suspend fun toggleLikeComment(
        @Body request: LikeCommentRequest
    ): BasicApiResponse<Boolean>

}
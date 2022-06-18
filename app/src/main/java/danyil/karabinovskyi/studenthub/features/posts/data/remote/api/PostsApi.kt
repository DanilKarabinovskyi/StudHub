package danyil.karabinovskyi.studenthub.features.posts.data.remote.api

import danyil.karabinovskyi.studenthub.common.model.BasicApiResponse
import danyil.karabinovskyi.studenthub.core.data.Filter
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.CommentResponse
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.PostResponse
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.CreateCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikeUpdateRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface PostsApi {
    @GET("posts/")
    suspend fun getPosts(
        @Query("take") take: Int,
        @Query("skip") skip: Int,
        @Query("order") order: String,     // ASC, DESC
        @Query("sort") sort: String,       // Any field
//        @Query("filter") filter: Filter, // "sport" etc
        @Query("socialTag") socialTag: String, // All, My, My University
    ): BasicApiResponse<List<PostResponse>>

    @Multipart
    @POST("posts")
    suspend fun createPost(
        @Part("title") title: RequestBody,
        @Part("body") description: RequestBody,
        @Part("tags") tags: RequestBody,
        @Part file: MultipartBody.Part
    ): BasicApiResponse<PostResponse>

    @GET("posts/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String,
    ): BasicApiResponse<PostResponse>

    @PUT("posts/post")
    suspend fun editPost(
        @Part("title") title: RequestBody,
        @Part("body") description: RequestBody,
        @Part("tags") tags: List<String>,
        @Part("file") postImage: MultipartBody.Part
    ): BasicApiResponse<PostResponse>

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
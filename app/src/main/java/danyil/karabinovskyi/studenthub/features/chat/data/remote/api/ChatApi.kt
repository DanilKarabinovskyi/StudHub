package danyil.karabinovskyi.studenthub.features.chat.data.remote.api

import danyil.karabinovskyi.studenthub.common.model.BasicApiResponse
import danyil.karabinovskyi.studenthub.features.chat.data.model.AddToChatRequest
import danyil.karabinovskyi.studenthub.features.chat.data.model.ChatData
import danyil.karabinovskyi.studenthub.features.chat.data.remote.entity.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ChatApi {

    @Multipart
    @POST("chat/")
    suspend fun createChat(
        @Part("title") title: RequestBody,
        @Part("userIds") userIds: RequestBody,
        @Part file: MultipartBody.Part?
    ) : BasicApiResponse<ChatData>

    @GET("chat/users")
    suspend fun getUsers(
        @Query("take") take: Int,
        @Query("skip") skip: Int,
        @Query("order") order: String,     // ASC, DESC
        @Query("sort") sort: String,       // Any field
        @Query("search") search: String, // "sport" etc
    ): BasicApiResponse<List<UserResponse>>

    @POST("chat/add-to-chat")
    suspend fun addToChat(
        addToChatRequest: AddToChatRequest
    ) : BasicApiResponse<Boolean>
}
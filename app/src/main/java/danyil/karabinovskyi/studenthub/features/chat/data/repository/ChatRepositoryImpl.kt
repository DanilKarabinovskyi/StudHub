package danyil.karabinovskyi.studenthub.features.chat.data.repository

import androidx.core.net.toFile
import com.google.gson.Gson
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.core.data.ChatQuery
import danyil.karabinovskyi.studenthub.core.data.entity.User
import danyil.karabinovskyi.studenthub.core.data.handleErrors
import danyil.karabinovskyi.studenthub.features.chat.data.model.AddToChatRequest
import danyil.karabinovskyi.studenthub.features.chat.data.model.CreateChatRequest
import danyil.karabinovskyi.studenthub.features.chat.data.remote.api.ChatApi
import danyil.karabinovskyi.studenthub.features.chat.domain.ChatRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val api: ChatApi,
) : ChatRepository {
    override suspend fun createChat(
        request: CreateChatRequest
    ): Resource<Boolean> {
        return handleErrors {
            val file = request.fileUri?.toFile()
            val finalFile = if (file == null) null else {
                MultipartBody.Part
                    .createFormData(
                        name = "file",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
            }

            val response = api.createChat(
                file = finalFile,
                title = request.title.toRequestBody(),
                userIds = Gson().toJson(request.userIds).toRequestBody()
            )
            if (response.successful) {
                Resource.Success(true)
            } else {
                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        }
    }

    override suspend fun getUsers(
        chatQuery: ChatQuery
    ): Resource<List<User>> {
        return handleErrors {
            val response = api.getUsers(
                take = chatQuery.take,
                skip = chatQuery.skip,
                order = chatQuery.order,
                sort = chatQuery.sort,
                search = chatQuery.search
            )
            if (response.successful) {
                if (response.data != null) {
                    Resource.Success(data = response.data.map { user -> user.toUser() })
                } else {
                    Resource.Error(uiText = UiText.StringResource(R.string.oops_something_went_wrong))
                }
            } else {
                response.message?.let { msg: String ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        }
    }

    override suspend fun addToChat(addToChatRequest: AddToChatRequest): Resource<Boolean> {
        return handleErrors {
            val response = api.addToChat(
                addToChatRequest = addToChatRequest
            )
            if (response.successful) {
                Resource.Success(data = true)
            } else {
                response.message?.let { msg: String ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        }
    }
}
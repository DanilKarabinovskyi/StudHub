package danyil.karabinovskyi.studenthub.features.posts.data.repository

import androidx.core.net.toFile
import com.google.gson.Gson
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.core.data.Query
import danyil.karabinovskyi.studenthub.core.data.handleErrors
import danyil.karabinovskyi.studenthub.core.domain.model.Comment
import danyil.karabinovskyi.studenthub.features.posts.data.remote.api.PostsApi
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.CreateCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikeCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikePostRequest
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.LikeUpdateRequest
import danyil.karabinovskyi.studenthub.features.posts.domain.PostsRepository
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.CreatePostRequest
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val api: PostsApi,
    private val gson: Gson
) : PostsRepository {

    override suspend fun getPosts(query: Query): Resource<List<Post>> {
        return handleErrors {
            val response = api.getPosts(
                take = query.take,
                skip = query.skip,
                order = query.order,
                sort = query.sort,
                filter = JSONObject().put("tags", JSONArray(query.filter.tags)),
                socialTag = query.socialTag
            )
            if (response.successful) {
                if (response.data != null) {
                    Resource.Success(data = response.data.map { post -> post.mapToUI() })
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

    override suspend fun upsertPost(
        request: CreatePostRequest
    ): Resource<Unit> {
        val file = request.postImage?.toFile()
        return handleErrors {
            val finalFile = if (request.postImage == null) null else {
                MultipartBody.Part
                    .createFormData(
                        name = "file",
                        filename = file!!.name,
                        body = file.asRequestBody()
                    )
            }
            val response = api.upsertPost(
                id = gson.toJson(request.id).toRequestBody(),
                title = request.title.toRequestBody(),
                description = request.description.toRequestBody(),
                tags = gson.toJson(request.tags).toRequestBody(),
                file = finalFile
            )
            if (response.successful) {
                Resource.Success(Unit)
            } else {
                response.message?.let { msg: String ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        }
    }

    override suspend fun getPost(postId: Int): Resource<Post> {
        return handleErrors {
            val response = api.getPost(
                postId = postId
            )
            if (response.successful) {
                if (response.data != null) {
                    Resource.Success(data = response.data.mapToUI())
                } else {
                    Resource.Error(UiText.StringResource(R.string.error_unknown))
                }
            } else {
                response.message?.let { msg: String ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        }
    }

    override suspend fun deletePost(postId: Int): Resource<Boolean> {
        return handleErrors {
            val response = api.deletePost(
                postId = postId
            )
            if (response.successful) {
                Resource.Success(true)
            } else {
                response.message?.let { msg: String ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
            Resource.Success(data = true)
        }
    }

    override suspend fun getCommentsForPost(postId: Int): Resource<List<Comment>> {
        return handleErrors {
            val response = api.getCommentsForPost(
                postId = postId
            )
            if (response.successful) {
                if (response.data != null) {
                    Resource.Success(data = response.data.map { comment -> comment.mapToUI() })
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


override suspend fun createComment(createCommentRequest: CreateCommentRequest): SimpleResource {
    return handleErrors {
        val response = api.createComment(
            request = createCommentRequest
        )
        if (response.successful) {
            Resource.Success(data = Unit)
        } else {
            response.message?.let { msg: String ->
                Resource.Error(UiText.DynamicString(msg))
            } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
        }
    }
}

    override suspend fun toggleLikePost(request: LikePostRequest): Resource<Boolean> {
        return handleErrors {
            val response = api.toggleLikePost(
                request = request
            )
            if (response.successful) {
                Resource.Success(data = response.data)
            } else {
                response.message?.let { msg: String ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        }
    }

    override suspend fun toggleLikeComment(request: LikeCommentRequest): Resource<Boolean> {
        return handleErrors {
            val response = api.toggleLikeComment(
                request = request
            )
            if (response.successful) {
                Resource.Success(data = response.data)
            } else {
                response.message?.let { msg: String ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
            }
        }
    }

}
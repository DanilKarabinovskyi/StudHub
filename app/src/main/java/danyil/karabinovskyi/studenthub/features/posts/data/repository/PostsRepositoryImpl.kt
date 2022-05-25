package danyil.karabinovskyi.studenthub.features.posts.data.repository

import android.net.Uri
import androidx.core.net.toFile
import com.google.gson.Gson
import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.core.data.Query
import danyil.karabinovskyi.studenthub.core.domain.model.Comment
import danyil.karabinovskyi.studenthub.features.posts.data.remote.api.PostsApi
import danyil.karabinovskyi.studenthub.features.posts.data.remote.entity.PostsResponse
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.CreatePostRequest
import danyil.karabinovskyi.studenthub.features.posts.domain.PostsRepository
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val api: PostsApi,
    private val gson: Gson
) : PostsRepository {
    override suspend fun getPosts(query: Query): Resource<List<Post>> {
        return try {
            val response = api.getPosts(
                take = query.take,
                skip = query.skip,
                order = query.order,
                sort = query.sort,
                tags = query.tags,
                socialTag = query.socialTag
            )

            val posts = mutableListOf<Post>()
            for (i in 0..20) {
                posts.add(
                    Post(
                        "2",
                        "2",
                        "Danyil",
                        "https://images.unsplash.com/photo-1630518615523-0d82e3985c06?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                        "https://images.unsplash.com/photo-1630370939214-4c4041b5efc4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                        "loremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremlorem",
                        555,
                        555,
                        true,
                        false
                    )
                )
            }
//            todo when api will be ready
//            if(response.successful) {
//                Resource.Success(Unit)
//            } else {
//                response.message?.let { msg:String ->
//                    Resource.Error(UiText.DynamicString(msg))
//                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
//            }
            Resource.Success(data = posts)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun createPost(
        description: String,
        imageUri: Uri
    ): Resource<Post> {
        val request = CreatePostRequest(description)
        val file = imageUri.toFile()
        return try {
            val response = api.createPost(
                postData = MultipartBody.Part
                    .createFormData(
                        "post_data",
                        gson.toJson(request)
                    ),
                postImage = MultipartBody.Part
                    .createFormData(
                        name = "post_image",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
            )
            val post = Post(
                "2",
                "2",
                "Danyil",
                "https://images.unsplash.com/photo-1630518615523-0d82e3985c06?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                "https://images.unsplash.com/photo-1630370939214-4c4041b5efc4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                "loremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremlorem",
                555,
                555,
                true,
                false
            )
//            todo when api will be ready
//            if(response.successful) {
//                Resource.Success(Unit)
//            } else {
//                response.message?.let { msg:String ->
//                    Resource.Error(UiText.DynamicString(msg))
//                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
//            }
            Resource.Success(data = post)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun getPost(postId: Int): Resource<Post> {
        return try {
            val response = api.getPost(
                postId = postId.toString()
            )
            val post = Post(
                "2",
                "2",
                "Danyil",
                "https://images.unsplash.com/photo-1630518615523-0d82e3985c06?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                "https://images.unsplash.com/photo-1630370939214-4c4041b5efc4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                "loremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremlorem",
                555,
                555,
                true,
                false
            )
//            todo when api will be ready
//            if(response.successful) {
//                Resource.Success(Unit)
//            } else {
//                response.message?.let { msg:String ->
//                    Resource.Error(UiText.DynamicString(msg))
//                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
//            }
            Resource.Success(data = post)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun editPost(
        description: String,
        imageUri: Uri
    ): Resource<Post> {
        val request = CreatePostRequest(description)
        val file = imageUri.toFile()
        return try {
            val response = api.editPost(
                postData = MultipartBody.Part
                    .createFormData(
                        "post_data",
                        gson.toJson(request)
                    ),
                postImage = MultipartBody.Part
                    .createFormData(
                        name = "post_image",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
            )
            val post = Post(
                "2",
                "2",
                "Danyil",
                "https://images.unsplash.com/photo-1630518615523-0d82e3985c06?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                "https://images.unsplash.com/photo-1630370939214-4c4041b5efc4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80",
                "loremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremloremlorem",
                555,
                555,
                true,
                false
            )
//            todo when api will be ready
//            if(response.successful) {
//                Resource.Success(Unit)
//            } else {
//                response.message?.let { msg:String ->
//                    Resource.Error(UiText.DynamicString(msg))
//                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
//            }
            Resource.Success(data = post)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun deletePost(postId: Int): Resource<Boolean> {
        return try {
            val response = api.deletePost(
                postId = postId.toString()
            )
//            todo when api will be ready
//            if(response.successful) {
//                Resource.Success(Unit)
//            } else {
//                response.message?.let { msg:String ->
//                    Resource.Error(UiText.DynamicString(msg))
//                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))
//            }
            Resource.Success(data = true)
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override suspend fun getCommentsForPost(postId: String): Resource<List<Comment>> {
        TODO("Not yet implemented")
    }

    override suspend fun createComment(postId: String, comment: String): SimpleResource {
        TODO("Not yet implemented")
    }

    override suspend fun likeParent(parentId: String, parentType: Int): SimpleResource {
        TODO("Not yet implemented")
    }

    override suspend fun unlikeParent(parentId: String, parentType: Int): SimpleResource {
        TODO("Not yet implemented")
    }

    override suspend fun getLikesForParent(parentId: String): Resource<Int> {
        TODO("Not yet implemented")
    }

}
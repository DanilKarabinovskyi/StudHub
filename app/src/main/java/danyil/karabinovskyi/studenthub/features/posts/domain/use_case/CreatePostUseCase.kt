package danyil.karabinovskyi.studenthub.features.posts.domain.use_case

import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.features.posts.domain.PostsRepository
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.CreatePostRequest
import javax.inject.Inject

class CreatePostUseCase (
    private val repository: PostsRepository
) {
    suspend fun execute(
        createPostRequest: CreatePostRequest
    ): SimpleResource {
        if (createPostRequest.postImage == null) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_no_image_picked)
            )
        }
        if (createPostRequest.description.isBlank()) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_description_blank)
            )
        }
        return repository.createPost(createPostRequest)
    }
}
package danyil.karabinovskyi.studenthub.features.posts.domain.use_case

import danyil.karabinovskyi.studenthub.R
import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.common.model.UiText
import danyil.karabinovskyi.studenthub.features.posts.domain.PostsRepository
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.CreatePostRequest

class CreatePostUseCase (
    private val repository: PostsRepository
) {
    suspend fun execute(
        createPostRequest: CreatePostRequest
    ): SimpleResource {
        if (createPostRequest.description.isBlank() || createPostRequest.title.isBlank() || createPostRequest.tags.isEmpty()) {
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_fields_blank)
            )
        }
        return repository.upsertPost(createPostRequest)
    }
}
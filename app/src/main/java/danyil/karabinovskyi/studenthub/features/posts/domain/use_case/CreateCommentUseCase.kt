package danyil.karabinovskyi.studenthub.features.posts.domain.use_case

import danyil.karabinovskyi.studenthub.common.model.SimpleResource
import danyil.karabinovskyi.studenthub.features.posts.data.remote.request.CreateCommentRequest
import danyil.karabinovskyi.studenthub.features.posts.domain.PostsRepository

class CreateCommentUseCase (
    private val repository: PostsRepository
) {
    suspend fun execute(
        createCommentRequest: CreateCommentRequest
    ): SimpleResource {
        return repository.createComment(createCommentRequest)
    }
}
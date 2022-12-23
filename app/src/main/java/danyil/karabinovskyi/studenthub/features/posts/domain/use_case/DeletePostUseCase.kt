package danyil.karabinovskyi.studenthub.features.posts.domain.use_case

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.features.posts.domain.PostsRepository

class DeletePostUseCase (
    private val repository: PostsRepository
) {
    suspend fun execute(
        postId:Int
    ): Resource<Boolean> {
        return repository.deletePost(postId = postId)
    }
}
package danyil.karabinovskyi.studenthub.features.posts.domain.use_case

import danyil.karabinovskyi.studenthub.common.model.Resource
import danyil.karabinovskyi.studenthub.core.data.Query
import danyil.karabinovskyi.studenthub.core.domain.model.Comment
import danyil.karabinovskyi.studenthub.features.posts.domain.PostsRepository
import danyil.karabinovskyi.studenthub.features.posts.domain.entity.Post

class GetCommentsForPostUseCase(
    private val repository: PostsRepository
) {
    suspend fun execute(
        postId: Int,
    ): Resource<List<Comment>> {
        return repository.getCommentsForPost(postId = postId)
    }
}
package danyil.karabinovskyi.studenthub.features.posts.domain.use_case

import javax.inject.Inject

data class PostUseCases @Inject constructor(
    val createPostUseCase: CreatePostUseCase,
    val getPostsUseCase: GetPostsUseCase,
)